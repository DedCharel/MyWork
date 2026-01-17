package com.example.mywork.presentation.screen.works

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.AddWorkUseCase
import com.example.mywork.domain.DeleteWorkUseCase
import com.example.mywork.domain.EditWorkUseCase
import com.example.mywork.domain.GetAllWorksUseCase
import com.example.mywork.domain.Work
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorksViewModel @Inject constructor(
    private val getAllWorksUseCase: GetAllWorksUseCase,
    private val addWorkUseCase: AddWorkUseCase,
    private val editWorkUseCase: EditWorkUseCase,
    private val deleteWorkUseCase: DeleteWorkUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(WorksScreenState())
    val state = _state.asStateFlow()

    init {

        getAllWorksUseCase().onEach {  previousState ->
            _state.update { it.copy(works = previousState)
            }
        }.launchIn(viewModelScope)

    }


    fun processCommand(command: WorkCommand){
        viewModelScope.launch {
            when(command){
                is WorkCommand.AddWork -> {
                    addWorkUseCase(
                        date = System.currentTimeMillis(),
                        counterparty = "Counterparty new",
                        worker = "Worker new",
                        description = "Большой текст для проверки читаемости и прочей ерунды на несколько строк",
                        time = 1
                    )
                }
                is WorkCommand.DeleteWork -> {
                    deleteWorkUseCase(command.workId)
                }
                is WorkCommand.EditWork -> {
                    val counterparty = command.work.counterparty
                    editWorkUseCase(command.work.copy(counterparty = "$counterparty EDIT" ))
                }
            }
        }

    }
}



sealed interface WorkCommand{

    data class DeleteWork(val workId: Int):  WorkCommand

    data class EditWork(val work: Work): WorkCommand

    data object AddWork: WorkCommand
}

data class WorksScreenState(
    val works: List<Work> = listOf()
)