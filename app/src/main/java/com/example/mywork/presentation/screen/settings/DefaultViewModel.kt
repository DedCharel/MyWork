package com.example.mywork.presentation.screen.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DefaultViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow<DefaultScreenState>(DefaultScreenState.Display)
    val state = _state.asStateFlow()

    fun processCommand(command: DefaultScreenCommand){
        when(command){
            DefaultScreenCommand.Back -> {
                _state.update {
                    DefaultScreenState.Finished
                }
            }
        }
    }
}

sealed interface DefaultScreenCommand{

    data object Back: DefaultScreenCommand
}
sealed interface DefaultScreenState{

    data object Finished: DefaultScreenState

    data object Display: DefaultScreenState
}

