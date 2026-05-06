package com.example.heritagequest.domain.model

data class Question(
    val id: String,
    val category: Category,
    val difficulty: Difficulty,
    val wikipediaTitle: String,
    val imageSource: String,
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val funFact: String
)
