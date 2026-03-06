package com.example.mywork.presentation.screen.organization


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.organization.AddOrganizationUseCase
import com.example.mywork.domain.organization.GetAllOrganizationUseCase
import com.example.mywork.domain.organization.Organization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OrganizationViewModel @Inject constructor(
    private val getAllOrganizationUseCase: GetAllOrganizationUseCase,
    private val addOrganizationUseCase: AddOrganizationUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<OrganizationState>(OrganizationState.OrganizationScreenState())
    val state = _state.asStateFlow()

    init {
        getAllOrganizationUseCase().onEach { organizations ->
            _state.update {
                OrganizationState.OrganizationScreenState(organizations)
            }
        }.launchIn(viewModelScope)
    }

    fun processCommand(command: OrganizationCommand) {
        when (command) {
            OrganizationCommand.Back -> {
                _state.update {
                    OrganizationState.Finished
                }
            }
        }
    }
}

sealed interface OrganizationCommand {
    data object Back : OrganizationCommand
}

sealed interface OrganizationState {
    data object Finished : OrganizationState

    data class OrganizationScreenState(
        val organizations: List<Organization> = listOf()
    ) : OrganizationState
}


