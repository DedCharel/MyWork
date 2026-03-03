package com.example.mywork.presentation.screen.workers.editing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.worker.EditWorkerUseCase
import com.example.mywork.domain.worker.GetWorkerUseCase
import com.example.mywork.domain.worker.Worker
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = EditWorkerViewModel.Factory::class)
class EditWorkerViewModel @AssistedInject constructor(
    private val editWorkerUseCase: EditWorkerUseCase,
    private val getWorkerUseCase: GetWorkerUseCase,
    @Assisted("workerId") val workerId: Long
) : ViewModel() {

    private val _state = MutableStateFlow<EditWorkerState>(EditWorkerState.Initial)
    val state = _state.asStateFlow()

    init{
        viewModelScope.launch {
            val worker = getWorkerUseCase(workerId)
            _state.update {
                EditWorkerState.Editing(worker)
            }
        }
    }

    fun processCommand(command: EditWorkerCommand){
        when(command){
            EditWorkerCommand.Back -> {
                _state.update {
                    EditWorkerState.Finished
                }
            }
            is EditWorkerCommand.InputName -> {
                _state.update { previousState ->
                    if (previousState is EditWorkerState.Editing){
                        val worker = previousState.worker.copy(name = command.name)
                        previousState.copy(worker = worker)
                    } else{
                        previousState
                    }

                }
            }
            is EditWorkerCommand.InputPhone -> {
                _state.update { previousState ->
                    if (previousState is EditWorkerState.Editing){
                        val worker = previousState.worker.copy(phone = command.phone)
                        previousState.copy(worker = worker)
                    } else{
                        previousState
                    }

                }
            }
            EditWorkerCommand.Save -> {
                viewModelScope.launch {
                    _state.update { previousState ->
                        if (previousState is EditWorkerState.Editing){
                            editWorkerUseCase(previousState.worker)
                            EditWorkerState.Finished
                        } else {
                            previousState
                        }

                    }
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("workerId") workerId: Long
        ): EditWorkerViewModel
    }
}

sealed interface EditWorkerCommand {

    data object Back : EditWorkerCommand

    data object Save : EditWorkerCommand

    data class InputName(val name: String) : EditWorkerCommand

    data class InputPhone(val phone:String): EditWorkerCommand
}

sealed interface EditWorkerState {

    data object Initial : EditWorkerState

    data object Finished : EditWorkerState

    data class Editing(val worker: Worker) : EditWorkerState
}