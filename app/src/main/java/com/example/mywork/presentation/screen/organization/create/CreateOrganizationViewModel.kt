package com.example.mywork.presentation.screen.organization.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork.domain.organization.AddOrganizationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateOrganizationViewModel @Inject constructor(
    private val addOrganizationUseCase: AddOrganizationUseCase
): ViewModel() {
    private val _state = MutableStateFlow<CreateOrganizationScreenState>(CreateOrganizationScreenState.Creation())

    val state = _state.asStateFlow()

    fun processCommand(command: CreateOrganizationCommand){
        when(command){
            CreateOrganizationCommand.Back ->{
                _state.update { CreateOrganizationScreenState.Finished }
            }
            is CreateOrganizationCommand.InputName -> {
                _state.update { previousState ->
                    if (previousState is CreateOrganizationScreenState.Creation){
                        previousState.copy(name = command.name)
                    } else {
                        previousState
                    }
                }
            }
            CreateOrganizationCommand.Save -> {
                viewModelScope.launch {
                    _state.update {previousState ->
                        if (previousState is CreateOrganizationScreenState.Creation) {
                            with(previousState){
                                addOrganizationUseCase(
                                    name = name,
                                    inn = inn,
                                    phone = phone,
                                    email = email,
                                    address = address,
                                    comments = comments
                                )
                            }

                            CreateOrganizationScreenState.Finished
                        } else {
                            previousState
                        }
                    }
                }

            }

            is CreateOrganizationCommand.InputAddress -> {
                _state.update { previousState ->
                    if (previousState is CreateOrganizationScreenState.Creation){
                        previousState.copy(address = command.address)
                    } else {
                        previousState
                    }
                }
            }
            is CreateOrganizationCommand.InputComments -> {
                _state.update { previousState ->
                    if (previousState is CreateOrganizationScreenState.Creation){
                        previousState.copy(comments = command.comments)
                    } else {
                        previousState
                    }
                }
            }
            is CreateOrganizationCommand.InputEmail -> {
                _state.update { previousState ->
                    if (previousState is CreateOrganizationScreenState.Creation){
                        previousState.copy(email = command.email)
                    } else {
                        previousState
                    }
                }
            }
            is CreateOrganizationCommand.InputPhone -> {
                _state.update { previousState ->
                    if (previousState is CreateOrganizationScreenState.Creation){
                        previousState.copy(phone = command.phone)
                    } else {
                        previousState
                    }
                }
            }
        }
    }
}

sealed interface CreateOrganizationCommand{

    data class InputName(val name: String): CreateOrganizationCommand

    data class InputPhone(val phone: String): CreateOrganizationCommand

    data class InputEmail(val email: String): CreateOrganizationCommand

    data class InputAddress(val address: String): CreateOrganizationCommand

    data class InputComments(val comments: String): CreateOrganizationCommand

    data object Save: CreateOrganizationCommand

    data object Back: CreateOrganizationCommand
}
sealed interface CreateOrganizationScreenState{

    data class Creation(
        val name: String = "",
        val inn: String = "",
        val phone: String = "",
        val email: String = "",
        val address: String = "",
        val comments: String = ""
    ): CreateOrganizationScreenState

    data object Finished: CreateOrganizationScreenState
}