package com.example.mywork.presentation.screen.creating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.work.AddWorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateWorkViewModel @Inject constructor(
    private val addWorkUseCase: AddWorkUseCase
): ViewModel() {
    private val _state = MutableStateFlow<CreateWorkScreenState>(CreateWorkScreenState.Creation())
    val state = _state.asStateFlow()

    fun processCommand(command: CreateWorkCommand){
        when (command){
            CreateWorkCommand.Back -> {
                _state.update { CreateWorkScreenState.Finished }
            }
            is CreateWorkCommand.InputCounterparty -> {
                _state.update {previousState ->
                    if (previousState is CreateWorkScreenState.Creation){
                        previousState.copy(counterparty = command.counterparty)
                    } else {
                        previousState
                    }
                }
            }
            is CreateWorkCommand.InputDate -> {
                _state.update {previousState ->
                    if (previousState is CreateWorkScreenState.Creation){
                        previousState.copy(date = command.date)
                    } else {
                        previousState
                    }
                }
            }
            is CreateWorkCommand.InputDescription -> {
                _state.update {previousState ->
                    if (previousState is CreateWorkScreenState.Creation){
                        previousState.copy(description = command.description)
                    } else {
                        previousState
                    }
                }
            }
            is CreateWorkCommand.InputTime -> {
                _state.update {previousState ->
                    if (previousState is CreateWorkScreenState.Creation){
                        if (command.time.isEmpty()){
                            previousState.copy(time = 0)
                        } else {
                            previousState.copy(time = command.time.toLong())
                        }

                    } else {
                        previousState
                    }
                }
            }
            is CreateWorkCommand.InputWorker -> {
                _state.update {previousState ->
                    if (previousState is CreateWorkScreenState.Creation){
                        previousState.copy(worker = command.worker)
                    } else {
                        previousState
                    }
                }
            }
            CreateWorkCommand.Save -> {
                viewModelScope.launch {
                    _state.update { previousState ->
                        if (previousState is CreateWorkScreenState.Creation){
                            val date = previousState.date
                            val counterparty = previousState.counterparty
                            val worker = previousState.worker
                            val description = previousState.description
                            val time = previousState.time
                            addWorkUseCase(
                                date = date,
                                counterparty = counterparty,
                                worker = worker,
                                description = description,
                                time = time
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

sealed interface CreateWorkCommand{

    data class InputDate(val date: Long): CreateWorkCommand

    data class InputCounterparty(val counterparty: String): CreateWorkCommand

    data class InputWorker(val worker: String): CreateWorkCommand

    data class InputDescription(val description: String): CreateWorkCommand

    data class InputTime(val time: String): CreateWorkCommand

    data object Save: CreateWorkCommand

    data object Back: CreateWorkCommand
}


sealed interface CreateWorkScreenState {

    data class Creation(
        val date: Long = System.currentTimeMillis(),
        val counterparty: String = "",
        val worker: String = "",
        val description: String = "",
        val time: Long = 0
    ) : CreateWorkScreenState

    data object Finished : CreateWorkScreenState
}

