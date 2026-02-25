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
                            val name = previousState.name
                            addOrganizationUseCase(
                                name
                            )
                            CreateOrganizationScreenState.Finished
                        } else {
                            previousState
                        }
                    }
                }

            }
        }
    }
}

sealed interface CreateOrganizationCommand{

    data class InputName(val name: String): CreateOrganizationCommand

    data object Save: CreateOrganizationCommand

    data object Back: CreateOrganizationCommand
}
sealed interface CreateOrganizationScreenState{

    data class Creation(
        val name: String = ""
    ): CreateOrganizationScreenState

    data object Finished: CreateOrganizationScreenState
}