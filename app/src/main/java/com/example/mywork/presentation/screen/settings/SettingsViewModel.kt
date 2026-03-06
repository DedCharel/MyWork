package com.example.mywork.presentation.screen.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow<SettingsState>(SettingsState.ShowSettings)
    val state = _state.asStateFlow()

    fun processCommand(command: SettingsCommand){
        when(command){
            SettingsCommand.Back -> {
                _state.update {
                    SettingsState.Finished
                }
            }
        }
    }
}

sealed interface SettingsCommand{

    data object Back: SettingsCommand
}

sealed interface SettingsState{

    data object Finished: SettingsState

    data object ShowSettings: SettingsState
}