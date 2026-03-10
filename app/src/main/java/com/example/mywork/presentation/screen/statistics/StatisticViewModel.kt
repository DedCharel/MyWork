package com.example.mywork.presentation.screen.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.statistic.GetOrganizationStatisticUseCase
import com.example.mywork.domain.statistic.OrganizationStatisticEntity
import com.example.mywork.presentation.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val getOrganizationStatisticUseCase: GetOrganizationStatisticUseCase
): ViewModel() {

    private val rangeState = MutableStateFlow(Pair(DateUtils.getStartOfCurrentMonth(),System.currentTimeMillis()))
    private val _state = MutableStateFlow<StatisticScreenState>(StatisticScreenState.OrganizationStatistics())
    val state = _state.asStateFlow()

    init {
        rangeState.onEach { input ->
            _state.update { previousState ->
                if (previousState is StatisticScreenState.OrganizationStatistics)
                    previousState.copy(currentRange = input)
                else{
                    previousState
                }
            }
        }.flatMapLatest {
            getOrganizationStatisticUseCase(it)
        }.onEach { statistic ->
            _state.update { previousState ->
                if (previousState is StatisticScreenState.OrganizationStatistics){
                    previousState.copy(organizationStatistics = statistic)
                } else {
                    previousState
                }
            }
        }.launchIn(viewModelScope)
    }

    fun processCommand(command: StatisticCommand){
        when(command){
            StatisticCommand.Back -> {
                _state.update {
                    StatisticScreenState.Finished
                }
            }
            is StatisticCommand.StatisticRange -> {
                viewModelScope.launch {
                    val currentRange = command.range
                    rangeState.update {
                        currentRange
                    }
                }

            }
        }
    }
}


sealed interface StatisticCommand {

    data object Back: StatisticCommand

    data class StatisticRange(val range: Pair<Long,Long>): StatisticCommand


}
sealed interface StatisticScreenState{

    data object Finished: StatisticScreenState

    data class OrganizationStatistics(
        val organizationStatistics: List<OrganizationStatisticEntity> = listOf(),
        val currentRange: Pair<Long, Long> = Pair(0,0)
    ): StatisticScreenState


}