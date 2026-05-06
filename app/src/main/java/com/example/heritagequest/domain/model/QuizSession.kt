package com.example.heritagequest.domain.model

data class QuizSession(
    val category: Category,
    val difficulty: Difficulty,
    val questions: List<Question>,
    val answers: List<AnswerOutcome>
)
