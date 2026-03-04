package com.example.mywork.presentation.screen.workers

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mywork.domain.worker.AddWorkerUseCase
import com.example.mywork.domain.worker.GetAllWorkersUseCase
import com.example.mywork.domain.worker.Worker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkerViewModel @Inject constructor(
    private val getAllWorkersUseCase: GetAllWorkersUseCase,
    private val addWorkerUseCase: AddWorkerUseCase
): ViewModel() {
    private val _state = MutableStateFlow<WorkerState>(WorkerState.WorkerScreenState())
    val state = _state.asStateFlow()

    init {
        getAllWorkersUseCase().onEach {
            _state.update { previousState ->
                if (previousState is WorkerState.WorkerScreenState){
                    previousState.copy(workers = it)
                } else {
                    previousState
                }
            }
        }.launchIn(viewModelScope)


    }
    fun processCommand(command: WorkerCommand){
        when(command){
            WorkerCommand.Back -> {
                _state.update {
                    WorkerState.Finished
                }
            }
            is WorkerCommand.SelectedWorker -> {

            }
        }
    }
}


sealed interface WorkerCommand{

    data class SelectedWorker(val worker: Worker): WorkerCommand

    data object Back: WorkerCommand
}


sealed interface WorkerState{
    data class WorkerScreenState(
        val workers: List<Worker> = listOf()
    ): WorkerState

    data object Finished: WorkerState
}