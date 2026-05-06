package com.example.heritagequest.ui.state

import com.example.heritagequest.domain.model.Question

data class QuizQuestion(
    val question: Question,
    val options: List<String>,
    val correctIndex: Int
)
