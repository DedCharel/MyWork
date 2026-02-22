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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrganizationViewModel @Inject constructor(
    private val getAllOrganizationUseCase: GetAllOrganizationUseCase,
    private val addOrganizationUseCase: AddOrganizationUseCase
): ViewModel(){

    private val _state = MutableStateFlow(OrganizationScreenState())
    val state = _state.asStateFlow()

    init {
//        viewModelScope.launch {
//            repeat(10){
//                addOrganizationUseCase(Organization(it.toLong(), "Organization$it"))
//            }
//
//        }
        getAllOrganizationUseCase().onEach {previousState ->
            _state.update { it.copy(organizations = previousState)
            }
        }.launchIn(viewModelScope)
    }

    fun processCommand(command: OrganizationCommand){
        when(command){
            OrganizationCommand.Back -> {

            }
            is OrganizationCommand.SelectedOrganization -> {

            }
        }
    }

}

sealed interface OrganizationCommand{

    data class SelectedOrganization(val organization: Organization): OrganizationCommand

    data object Back: OrganizationCommand
}

data class OrganizationScreenState(
    val organizations:List<Organization> = listOf()
)
