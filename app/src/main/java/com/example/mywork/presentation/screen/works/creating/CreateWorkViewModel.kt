package com.example.mywork.presentation.screen.works.creating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.organization.GetOrganizationUseCase
import com.example.mywork.domain.organization.Organization
import com.example.mywork.domain.work.AddWorkUseCase
import com.example.mywork.domain.worker.GetWorkerUseCase
import com.example.mywork.domain.worker.Worker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateWorkViewModel @Inject constructor(
    private val addWorkUseCase: AddWorkUseCase,
    private val getWorkerUseCase: GetWorkerUseCase,
    private val getOrganizationUseCase: GetOrganizationUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<CreateWorkScreenState>(CreateWorkScreenState.Creation())
    val state = _state.asStateFlow()

    fun processCommand(command: CreateWorkCommand) {
        when (command) {
            CreateWorkCommand.Back -> {
                _state.update { CreateWorkScreenState.Finished }
            }

            is CreateWorkCommand.InputOrganization -> {
                viewModelScope.launch {
                    val organization = getOrganizationUseCase(command.organizationId)
                    _state.update { previousState ->
                        if (previousState is CreateWorkScreenState.Creation) {
                            previousState.copy(organization = organization)
                        } else {
                            previousState
                        }
                    }
                }
            }

            is CreateWorkCommand.InputDate -> {
                _state.update { previousState ->
                    if (previousState is CreateWorkScreenState.Creation) {
                        previousState.copy(date = command.date)
                    } else {
                        previousState
                    }
                }
            }

            is CreateWorkCommand.InputDescription -> {
                _state.update { previousState ->
                    if (previousState is CreateWorkScreenState.Creation) {
                        previousState.copy(description = command.description)
                    } else {
                        previousState
                    }
                }
            }

            is CreateWorkCommand.InputTimeHour -> {
                _state.update { previousState ->
                    if (previousState is CreateWorkScreenState.Creation) {
                        if (command.timeHour.isEmpty())
                            previousState.copy(timeHour = 0)
                        else
                            previousState.copy(timeHour = command.timeHour.toInt())

                    } else {
                        previousState
                    }
                }
            }

            is CreateWorkCommand.InputTimeMinute -> {
                _state.update { previousState ->
                    if (previousState is CreateWorkScreenState.Creation) {
                        if (command.timeMinute.isEmpty())
                            previousState.copy(timeMinute = 0)
                        else
                            previousState.copy(timeMinute = command.timeMinute.toInt())

                    } else {
                        previousState
                    }
                }
            }

            is CreateWorkCommand.InputWorker -> {
                viewModelScope.launch {
                    val worker = getWorkerUseCase(command.workerId)
                    _state.update { previousState ->
                        if (previousState is CreateWorkScreenState.Creation) {
                            previousState.copy(worker = worker)
                        } else {
                            previousState
                        }
                    }
                }

            }

            CreateWorkCommand.Save -> {
                viewModelScope.launch {
                    _state.update { previousState ->
                        if (previousState is CreateWorkScreenState.Creation) {
                            val date = previousState.date
                            val organization = previousState.organization
                            val worker = previousState.worker
                            val description = previousState.description
                            val minInHour = (100 / 60f * previousState.timeMinute).toInt()
                            val time =
                                previousState.timeHour.toString() + "." + minInHour.toString()

                            if (organization !== null && worker !== null)
                                addWorkUseCase(
                                    date = date,
                                    organizationId = organization.id,
                                    workerId = worker.id,
                                    description = description,
                                    time = time.toDouble()
                                )
                            CreateWorkScreenState.Finished
                        } else {
                            previousState
                        }
                    }
                }
            }
        }
    }

}

sealed interface CreateWorkCommand {

    data class InputDate(val date: Long) : CreateWorkCommand

    data class InputOrganization(val organizationId: Long) : CreateWorkCommand

    data class InputWorker(val workerId: Long) : CreateWorkCommand

    data class InputDescription(val description: String) : CreateWorkCommand

    data class InputTimeHour(val timeHour: String) : CreateWorkCommand

    data class InputTimeMinute(val timeMinute: String) : CreateWorkCommand

    data object Save : CreateWorkCommand

    data object Back : CreateWorkCommand
}


sealed interface CreateWorkScreenState {

    data class Creation(
        val date: Long = System.currentTimeMillis(),
        val organization: Organization? = null,
        val worker: Worker? = null,
        val description: String = "",
        val timeHour: Int = 0,
        val timeMinute: Int = 0
    ) : CreateWorkScreenState {
        val isSaveEnabled: Boolean
            get() {
                return !(organization == null || worker == null || description.isEmpty() || (timeHour == 0 && timeMinute == 0))
            }
    }

    data object Finished : CreateWorkScreenState
}

