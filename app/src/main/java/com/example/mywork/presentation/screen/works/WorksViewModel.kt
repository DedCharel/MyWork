package com.example.mywork.presentation.screen.works

import androidx.lifecycle.ViewModel
import com.example.mywork.domain.GetAllWorksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorksViewModel @Inject constructor(
private val getAllWorksUseCase: GetAllWorksUseCase
): ViewModel() {

}