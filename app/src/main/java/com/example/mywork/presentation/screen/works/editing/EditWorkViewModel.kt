package com.example.mywork.presentation.screen.works.editing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.organization.GetOrganizationUseCase
import com.example.mywork.domain.work.DeleteWorkUseCase
import com.example.mywork.domain.work.EditWorkUseCase
import com.example.mywork.domain.work.GetWorkUseCase
import com.example.mywork.domain.work.Work
import com.example.mywork.domain.worker.GetWorkerUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = EditWorkViewModel.Factory::class)
class EditWorkViewModel @AssistedInject constructor(
    private val getWorkUseCase: GetWorkUseCase,
    private val editWorkUseCase: EditWorkUseCase,
    private val getOrganizationUseCase: GetOrganizationUseCase,
    private val getWorkerUseCase: GetWorkerUseCase,
    private val deleteWorkUseCase: DeleteWorkUseCase,
    @Assisted("workId") val workId: Int
) : ViewModel() {

    private val _state = MutableStateFlow<EditWorkState>(EditWorkState.Initial)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                val work = getWorkUseCase(workId)
                EditWorkState.Editing(work)
            }
        }
    }

    fun processCommand(command: EditWorkCommand) {
        when (command) {
            EditWorkCommand.Back -> {
                _state.update { EditWorkState.Finished }
            }

            is EditWorkCommand.InputDate -> {
                _state.update { previousState ->
                    if (previousState is EditWorkState.Editing) {
                        val newWork = previousState.work.copy(date = command.date)
                        previousState.copy(work = newWork)

                    } else {
                        previousState
                    }
                }
            }

            is EditWorkCommand.InputDescription -> {
                _state.update { previousState ->
                    if (previousState is EditWorkState.Editing) {
                        val newWork = previousState.work.copy(description = command.description)
                        previousState.copy(work = newWork)
                    } else {
                        previousState
                    }
                }
            }

            is EditWorkCommand.InputOrganization -> {
                viewModelScope.launch {
                    _state.update { previousState ->
                        if (previousState is EditWorkState.Editing) {
                            val organization = getOrganizationUseCase(command.organizationId)
                            val newWork = previousState.work.copy(organization = organization)
                            previousState.copy(work = newWork)
                        } else {
                            previousState
                        }
                    }
                }

            }

            is EditWorkCommand.InputTime -> {
                _state.update { previousState ->
                    if (previousState is EditWorkState.Editing) {
                        val newWork = previousState.work.copy(time = command.time.toDouble())
                        previousState.copy(work = newWork)
                    } else {
                        previousState
                    }
                }
            }

            is EditWorkCommand.InputWorker -> {
                viewModelScope.launch {
                    _state.update { previousState ->
                        if (previousState is EditWorkState.Editing) {
                            val worker = getWorkerUseCase(command.workerId)
                            val newWork = previousState.work.copy(worker = worker)
                            previousState.copy(work = newWork)
                        } else {
                            previousState
                        }
                    }
                }

            }

            EditWorkCommand.Save -> {
                viewModelScope.launch {
                    _state.update { previousState ->
                        if (previousState is EditWorkState.Editing){
                            val work = previousState.work
                            editWorkUseCase(work)
                            EditWorkState.Finished
                        } else {
                            previousState
                        }
                    }
                }

            }

            is EditWorkCommand.DeleteWork -> {
                viewModelScope.launch {
                    _state.update {
                        deleteWorkUseCase(command.work.id)
                        EditWorkState.Finished
                    }


                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("workId") workId: Int
        ): EditWorkViewModel
    }
}

sealed interface EditWorkCommand {

    data class InputDate(val date: Long) : EditWorkCommand

    data class InputOrganization(val organizationId: Long) : EditWorkCommand

    data class InputWorker(val workerId: Long) : EditWorkCommand

    data class InputDescription(val description: String) : EditWorkCommand

    data class InputTime(val time: String) : EditWorkCommand

    data class DeleteWork(val work: Work): EditWorkCommand

    data object Save : EditWorkCommand

    data object Back : EditWorkCommand


}

sealed interface EditWorkState {

    data object Initial : EditWorkState

    data object Finished : EditWorkState

    data class Editing(val work: Work) : EditWorkState
}