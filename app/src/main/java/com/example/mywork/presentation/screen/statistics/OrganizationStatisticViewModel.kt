package com.example.mywork.presentation.screen.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.organization.GetOrganizationUseCase
import com.example.mywork.domain.statistic.GetOrganizationStatisticUseCase
import com.example.mywork.domain.work.Work
import com.example.mywork.presentation.utils.DateUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel(assistedFactory = OrganizationStatisticViewModel.Factory::class)
class OrganizationStatisticViewModel @AssistedInject constructor(
    private val getOrganizationStatisticUseCase: GetOrganizationStatisticUseCase,
    private val getOrganizationUseCase: GetOrganizationUseCase,
    @Assisted("organizationId")val organizationId: Long
): ViewModel(){
    private val rangeState = MutableStateFlow(Pair(DateUtils.getStartOfCurrentMonth(),
        System.currentTimeMillis()))

    private val _state = MutableStateFlow<OrganizationStatisticScreenState>(
        OrganizationStatisticScreenState.DisplayOrganizationStatistic())

    val state = _state.asStateFlow()

    init {
        rangeState.onEach { input ->
            _state.update { previousState ->
                if (previousState is OrganizationStatisticScreenState.DisplayOrganizationStatistic)
                    previousState.copy(currentRange = input)
                else{
                    previousState
                }
            }
        }.flatMapLatest {
            getOrganizationStatisticUseCase(organizationId, it)
        }.onEach { works ->
            _state.update { previousState ->
                if (previousState is OrganizationStatisticScreenState.DisplayOrganizationStatistic){
                    previousState.copy(works = works)
                } else {
                    previousState
                }
            }
        }.launchIn(viewModelScope)
    }

    fun processCommand(command: OrganizationStatisticCommand){
        when(command){
            OrganizationStatisticCommand.Back -> {
                _state.update {
                    OrganizationStatisticScreenState.Finished
                }
            }
            OrganizationStatisticCommand.SendEmail -> {

            }
            is OrganizationStatisticCommand.SetStatisticRange -> {
                rangeState.update { command.range }
            }
        }
    }

    @AssistedFactory
    interface Factory{
        fun create(@Assisted("organizationId") organizationId: Long): OrganizationStatisticViewModel
    }
}

sealed interface OrganizationStatisticCommand{

    data object Back: OrganizationStatisticCommand

    data object SendEmail: OrganizationStatisticCommand

    data class SetStatisticRange(val range: Pair<Long,Long>): OrganizationStatisticCommand
}
sealed interface OrganizationStatisticScreenState{

    data object Finished: OrganizationStatisticScreenState

    data class DisplayOrganizationStatistic(
        val works: List<Work> = listOf(),
        val currentRange: Pair<Long, Long> = Pair(0,0)
    ): OrganizationStatisticScreenState
}

