package com.example.heritagequest.domain.model

enum class Difficulty(
    val secondsPerQuestion: Int,
    val showHints: Boolean
) {
    EASY(15, true),
    MEDIUM(20, true),
    HARD(15, false);
}
