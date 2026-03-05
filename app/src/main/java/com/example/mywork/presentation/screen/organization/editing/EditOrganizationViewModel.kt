package com.example.mywork.presentation.screen.organization.editing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.organization.DeleteOrganizationUseCase
import com.example.mywork.domain.organization.EditOrganizationUseCase
import com.example.mywork.domain.organization.GetOrganizationUseCase
import com.example.mywork.domain.organization.Organization
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = EditOrganizationViewModel.Factory::class)
class EditOrganizationViewModel @AssistedInject constructor(
    private val getOrganizationUseCase: GetOrganizationUseCase,
    private val editOrganizationUseCase: EditOrganizationUseCase,
    private val deleteOrganizationUseCase: DeleteOrganizationUseCase,
    @Assisted("organizationId") val organizationId: Long
): ViewModel() {

    private val _state = MutableStateFlow<EditOrganizationState>(EditOrganizationState.Initial)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                val organization = getOrganizationUseCase(organizationId)
                EditOrganizationState.Editing(organization)
            }
        }
    }

    fun processCommand(command: EditOrganizationCommand){
        when(command){
            EditOrganizationCommand.Back -> {
                _state.update {
                    EditOrganizationState.Finished
                }
            }
            is EditOrganizationCommand.InputAddress -> {
                _state.update { previousState ->
                    if (previousState is EditOrganizationState.Editing){
                        val organization = previousState.organization.copy(address = command.address)
                        previousState.copy(organization = organization)
                    } else {
                        previousState
                    }
                }
            }
            is EditOrganizationCommand.InputComments -> {
                _state.update { previousState ->
                    if (previousState is EditOrganizationState.Editing){
                        val organization = previousState.organization.copy(comments = command.comments)
                        previousState.copy(organization = organization)
                    } else {
                        previousState
                    }
                }
            }
            is EditOrganizationCommand.InputEmail -> {
                _state.update { previousState ->
                    if (previousState is EditOrganizationState.Editing){
                        val organization = previousState.organization.copy(email = command.email)
                        previousState.copy(organization = organization)
                    } else {
                        previousState
                    }
                }
            }
            is EditOrganizationCommand.InputInn -> {
                _state.update { previousState ->
                    if (previousState is EditOrganizationState.Editing){
                        val organization = previousState.organization.copy(inn = command.Inn)
                        previousState.copy(organization = organization)
                    } else {
                        previousState
                    }
                }
            }
            is EditOrganizationCommand.InputName -> {
                _state.update { previousState ->
                    if (previousState is EditOrganizationState.Editing){
                        val organization = previousState.organization.copy(name = command.name)
                        previousState.copy(organization = organization)
                    } else {
                        previousState
                    }
                }
            }
            is EditOrganizationCommand.InputPhone -> {
                _state.update { previousState ->
                    if (previousState is EditOrganizationState.Editing){
                        val organization = previousState.organization.copy(phone = command.phone)
                        previousState.copy(organization = organization)
                    } else {
                        previousState
                    }
                }
            }
            EditOrganizationCommand.Save -> {
                viewModelScope.launch {
                    _state.update { previousState ->
                        if (previousState is EditOrganizationState.Editing){
                            editOrganizationUseCase(previousState.organization)
                            EditOrganizationState.Finished
                        } else {
                            previousState
                        }

                    }
                }
            }
            is EditOrganizationCommand.DeleteOrganization -> {
                viewModelScope.launch {
                    _state.update {
                        deleteOrganizationUseCase(command.organizationId)
                        EditOrganizationState.Finished
                    }
                }
            }
        }
    }

    @AssistedFactory
    interface Factory{
        fun create(
            @Assisted("organizationId") organizationId: Long): EditOrganizationViewModel
    }
}


sealed interface EditOrganizationCommand{

    data object Back: EditOrganizationCommand

    data object Save: EditOrganizationCommand

    data class InputName(val name: String): EditOrganizationCommand

    data class InputInn(val Inn: String): EditOrganizationCommand

    data class InputPhone(val phone: String): EditOrganizationCommand

    data class InputEmail(val email: String): EditOrganizationCommand

    data class InputAddress(val address: String): EditOrganizationCommand

    data class InputComments(val comments: String): EditOrganizationCommand

    data class DeleteOrganization(val organizationId: Long): EditOrganizationCommand
}
sealed interface EditOrganizationState{
    data object Finished: EditOrganizationState

    data object Initial: EditOrganizationState

    data class Editing(val organization: Organization): EditOrganizationState
}