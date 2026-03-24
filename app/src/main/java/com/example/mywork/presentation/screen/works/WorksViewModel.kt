package com.example.mywork.presentation.screen.works

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.work.AddWorkUseCase
import com.example.mywork.domain.work.DeleteWorkUseCase
import com.example.mywork.domain.work.EditWorkUseCase
import com.example.mywork.domain.work.GetAllWorksUseCase
import com.example.mywork.domain.work.SearchWorkUseCase
import com.example.mywork.domain.work.Work
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class WorksViewModel @Inject constructor(
    private val getAllWorksUseCase: GetAllWorksUseCase,
    private val addWorkUseCase: AddWorkUseCase,
    private val editWorkUseCase: EditWorkUseCase,
    private val deleteWorkUseCase: DeleteWorkUseCase,
    private val searchWorkUseCase: SearchWorkUseCase
) : ViewModel() {

    private val query = MutableStateFlow("")
    private val _state = MutableStateFlow(WorksScreenState())
    val state = _state.asStateFlow()

    init {
        query
            .onEach { input ->
                _state.update { it.copy(query = input) }
            }
            .flatMapLatest { input ->
                if (input.isBlank()) {
                    getAllWorksUseCase()
                } else {
                    searchWorkUseCase(input)
                }
            }
            .map { works ->
                works.groupBy { work ->
                    val date = Instant.ofEpochMilli(work.date).atZone(ZoneId.systemDefault())
                    date.format(DateTimeFormatter.ofPattern("LLLL yyyy")).replaceFirstChar { it.uppercaseChar() }
                }
            }
            .onEach { groupWorks ->

                _state.update { it.copy(groupWorks = groupWorks) }
            }
            .launchIn(viewModelScope)

    }


    fun processCommand(command: WorkCommand){
        viewModelScope.launch {
            when(command){
                is WorkCommand.AddWork -> {
                    val work = command.work
                    addWorkUseCase(
                        date = work.date,
                        organizationId = work.organization.id ,
                        workerId = work.worker.id,
                        description = work.description,
                        time = work.time
                    )
                }
                is WorkCommand.DeleteWork -> {
                    deleteWorkUseCase(command.workId)
                }
                is WorkCommand.EditWork -> {
                    editWorkUseCase(command.work)
                }

                is WorkCommand.InputSearchQuery -> {
                    query.update { command.query.trim() }
                }
            }
        }

    }
}



sealed interface WorkCommand{

    data class DeleteWork(val workId: Int):  WorkCommand

    data class EditWork(val work: Work): WorkCommand

    data class AddWork(val work: Work): WorkCommand

    data class InputSearchQuery(val query:String): WorkCommand
}

data class WorksScreenState(
    val query:String = "",
    val groupWorks: Map<String, List<Work>> = mapOf("" to listOf())

)