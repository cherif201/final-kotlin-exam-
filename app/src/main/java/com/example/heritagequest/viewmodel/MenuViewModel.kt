package com.example.heritagequest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heritagequest.data.repository.StatsRepository
import com.example.heritagequest.ui.state.MenuUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val statsRepository: StatsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            statsRepository.stats.collect { stats ->
                _uiState.update { it.copy(isLoading = false, stats = stats) }
            }
        }
    }
}
