package com.example.heritagequest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heritagequest.data.repository.StatsRepository
import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.domain.model.Difficulty
import com.example.heritagequest.ui.state.ResultsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResultsViewModel(
    private val statsRepository: StatsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ResultsUiState())
    val uiState: StateFlow<ResultsUiState> = _uiState.asStateFlow()

    fun setResults(
        category: Category?,
        difficulty: Difficulty?,
        score: Int,
        total: Int,
        correct: Int
    ) {
        _uiState.value = ResultsUiState(
            score = score,
            total = total,
            correct = correct,
            category = category,
            difficulty = difficulty
        )
        viewModelScope.launch {
            statsRepository.updateAfterQuiz(correct = correct, total = total)
        }
    }
}
