package com.example.mywork.presentation.screen.workers

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val _state = MutableStateFlow(WorkerScreenState())
    val state = _state.asStateFlow()

    init {
//        viewModelScope.launch {
//            repeat(10){
//                addWorkerUseCase(Worker(it.toLong(), "Worker$it"))
//            }
//
//        }
        getAllWorkersUseCase().onEach {previousState ->
            _state.update { it.copy(workers = previousState)
            }
        }.launchIn(viewModelScope)


    }
}

fun processCommand(command: WorkerCommand){
    when(command){
        WorkerCommand.Back -> {

        }
        is WorkerCommand.SelectedWorker -> {

        }
    }
}
sealed interface WorkerCommand{

    data class SelectedWorker(val worker: Worker): WorkerCommand

    data object Back: WorkerCommand
}

data class WorkerScreenState(
    val workers: List<Worker> = listOf()
)