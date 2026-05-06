package com.example.heritagequest.ui.state

import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.domain.model.Difficulty

data class DifficultyUiState(
    val category: Category? = null,
    val difficulties: List<Difficulty> = Difficulty.entries.toList()
)
