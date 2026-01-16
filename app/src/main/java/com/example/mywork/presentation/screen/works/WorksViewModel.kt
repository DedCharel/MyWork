package com.example.mywork.presentation.screen.works

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.AddWorkUseCase
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
    private val addWorkUseCase: AddWorkUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(WorksScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            addWorkTest()
        }
        getAllWorksUseCase().onEach {  previousState ->
            _state.update { it.copy(works = previousState)
            }
        }.launchIn(viewModelScope)

    }
    suspend  fun addWorkTest(){
        repeat(10){
            addWorkUseCase(
                date = System.currentTimeMillis(),
                counterparty = "Counterparty $it",
                worker = "Worker $it",
                description = "Большой текст для проверки читаемости и прочей ерунды на несколько строк",
                time = it.toLong()
            )
        }
    }
}



data class WorksScreenState(
    val works: List<Work> = listOf()
)