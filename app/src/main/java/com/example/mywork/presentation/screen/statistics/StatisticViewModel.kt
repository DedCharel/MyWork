package com.example.mywork.presentation.screen.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.statistic.GetOrganizationStatisticUseCase
import com.example.mywork.domain.statistic.OrganizationStatisticEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val getOrganizationStatisticUseCase: GetOrganizationStatisticUseCase
): ViewModel() {

    private val _state = MutableStateFlow<StatisticScreenState>(StatisticScreenState.OrganizationStatistics())
    val state = _state.asStateFlow()

    init {
        getOrganizationStatisticUseCase().onEach {
            _state.update { previousState ->
             if (previousState is StatisticScreenState.OrganizationStatistics){
                 previousState.copy(organizationStatistics = it)
             }else{
                 previousState
             }
            }
        }.launchIn(viewModelScope)
    }
}


sealed interface StatisticCommand {

    data object Back: StatisticCommand



}
sealed interface StatisticScreenState{
    data object Finished: StatisticScreenState

    data class OrganizationStatistics(
        val organizationStatistics: List<OrganizationStatisticEntity> = listOf()
    ): StatisticScreenState


}