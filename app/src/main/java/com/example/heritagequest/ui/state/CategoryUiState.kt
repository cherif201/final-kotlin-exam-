package com.example.heritagequest.ui.state

import com.example.heritagequest.domain.model.Category

data class CategoryUiState(
    val categories: List<Category> = Category.entries.toList()
)
