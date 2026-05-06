package com.example.heritagequest.viewmodel

import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.domain.model.Difficulty

sealed class QuizEvent {
    data class NavigateToResults(
        val category: Category,
        val difficulty: Difficulty,
        val score: Int,
        val total: Int,
        val correct: Int
    ) : QuizEvent()
}
