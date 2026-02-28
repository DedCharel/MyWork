package com.example.mywork.presentation.screen.workers.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.worker.AddWorkerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateWorkerViewModel @Inject constructor(
    private val addWorkerUseCase: AddWorkerUseCase
): ViewModel() {
    private val _state  = MutableStateFlow<CreateWorkerScreenState>(CreateWorkerScreenState.Creation())
    val state = _state.asStateFlow()

    fun processCommand(command: CreateWorkerCommand){
        when(command){
            is CreateWorkerCommand.InputName -> {
                _state.update { previousState ->
                    if (previousState is CreateWorkerScreenState.Creation){
                        previousState.copy(name = command.name)
                    } else{
                        previousState
                    }
                }
            }
            is CreateWorkerCommand.InputPhone -> {
                _state.update { previousState ->
                    if (previousState is CreateWorkerScreenState.Creation){
                        previousState.copy(phone = command.phone)
                    } else {
                        previousState
                    }

                }
            }
            CreateWorkerCommand.Save -> {
                viewModelScope.launch {
                    _state.update { previousState ->
                        if (previousState is CreateWorkerScreenState.Creation){
                            with(previousState){
                                addWorkerUseCase(
                                    name = name,
                                    phone = phone
                                )
                            }
                            CreateWorkerScreenState.Finish
                        }
                        previousState
                    }
                }
            }
            CreateWorkerCommand.Back -> {
                CreateWorkerScreenState.Finish
            }
        }
    }

    sealed interface CreateWorkerCommand {
        data class InputName(val name: String): CreateWorkerCommand

        data class InputPhone(val phone: String): CreateWorkerCommand

        data object Save: CreateWorkerCommand

        data object Back: CreateWorkerCommand
    }
}

sealed interface CreateWorkerScreenState {
    data class Creation(
        val name: String = "",
        val phone: String = ""
    ): CreateWorkerScreenState

    data object Finish: CreateWorkerScreenState
}