package com.example.heritagequest.ui.state

import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.domain.model.Difficulty

data class ResultsUiState(
    val score: Int = 0,
    val total: Int = 0,
    val correct: Int = 0,
    val category: Category? = null,
    val difficulty: Difficulty? = null
) {
    val percentage: Int
        get() = if (total == 0) 0 else (correct * 100) / total

    val performanceMessage: String
        get() = when {
            percentage >= 90 -> "Heritage Master!"
            percentage >= 70 -> "Well done."
            percentage >= 50 -> "Not bad - keep exploring."
            else -> "Plenty of monuments left to discover."
        }
}
