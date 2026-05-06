package com.example.heritagequest.viewmodel

import androidx.lifecycle.ViewModel
import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.ui.state.DifficultyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DifficultyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DifficultyUiState())
    val uiState: StateFlow<DifficultyUiState> = _uiState.asStateFlow()

    fun setCategory(category: Category?) {
        _uiState.update { it.copy(category = category) }
    }
}
