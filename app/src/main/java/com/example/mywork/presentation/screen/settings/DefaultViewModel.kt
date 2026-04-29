package com.example.mywork.presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.settings.GetSettingsUseCase
import com.example.mywork.domain.settings.Settings
import com.example.mywork.domain.settings.UpdateIntervalDefaultUseCase
import com.example.mywork.domain.settings.UpdateWorkerDefaultUseCase
import com.example.mywork.domain.worker.GetWorkerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefaultViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateWorkerDefaultUseCase: UpdateWorkerDefaultUseCase,
    private val updateIntervalDefaultUseCase: UpdateIntervalDefaultUseCase,
    private val getWorkerUseCase: GetWorkerUseCase
): ViewModel() {

    private val _state = MutableStateFlow<DefaultScreenState>(DefaultScreenState.Display())
    val state = _state.asStateFlow()

    init {
        getSettingsUseCase().onEach {
            _state.update { previousState ->
                if (previousState is DefaultScreenState.Display){
                    if (it.workerId == 0L){
                        previousState.copy(
                            interval = it.interval
                        )
                    } else {
                        previousState.copy(
                            workerId = it.workerId,
                            interval = it.interval,
                            workerName = getWorkerUseCase(it.workerId).name
                        )
                    }

                } else {
                    previousState
                }

            }
        }.launchIn(viewModelScope)
    }

    fun processCommand(command: DefaultScreenCommand){
        when(command){
            DefaultScreenCommand.Back -> {
                _state.update {
                    DefaultScreenState.Finished
                }
            }

            is DefaultScreenCommand.InputWorker -> {
                viewModelScope.launch {
                    val worker = getWorkerUseCase(command.workerId)
                    _state.update { previousState ->
                        if (previousState is DefaultScreenState.Display){
                            previousState.copy(
                                workerName = worker.name,
                                workerId = worker.id
                            )
                        } else {
                            previousState
                        }

                    }
                }

            }

            DefaultScreenCommand.Save -> {
                viewModelScope.launch {
                    _state.update { previousState ->
                        if (previousState is DefaultScreenState.Display){
                            updateWorkerDefaultUseCase(previousState.workerId)
                            DefaultScreenState.Finished
                        } else {
                            previousState
                        }

                    }

                }
            }
        }
    }
}

sealed interface DefaultScreenCommand{

    data object Back: DefaultScreenCommand

    data class InputWorker(val workerId: Long): DefaultScreenCommand

    data object Save: DefaultScreenCommand
}
sealed interface DefaultScreenState{

    data object Finished: DefaultScreenState

    data class Display(
        val workerId: Long = 0,
        val workerName: String = "",
        val interval: Pair<Long, Long> = Pair(0, 0)
    ): DefaultScreenState
}

