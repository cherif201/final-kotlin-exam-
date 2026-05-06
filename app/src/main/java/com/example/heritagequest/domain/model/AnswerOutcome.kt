package com.example.heritagequest.domain.model

data class AnswerOutcome(
    val wasCorrect: Boolean,
    val selectedAnswer: String,
    val correctAnswer: String,
    val funFact: String
)
