package com.example.heritagequest.ui.state

import com.example.heritagequest.domain.model.AnswerOutcome
import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.domain.model.Difficulty


data class QuizUiState(
    val isLoading: Boolean = true,
    val category: Category? = null,
    val difficulty: Difficulty? = null,
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedIndex: Int? = null,
    val score: Int = 0,
    val secondsRemaining: Int? = null,
    val timerEnabled: Boolean = true,
    val soundEnabled: Boolean = true,
    val hapticsEnabled: Boolean = true,
    val answers: List<AnswerOutcome> = emptyList(),
    val isFeedbackVisible: Boolean = false,
    val lastOutcome: AnswerOutcome? = null,
    val feedbackTitle: String? = null,
    val feedbackBody: String? = null,
    val imageUrls: Map<String, String?> = emptyMap(),
    val isImageLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isLastQuestion: Boolean
        get() = currentIndex >= questions.lastIndex

    val currentQuestion: QuizQuestion?
        get() = questions.getOrNull(currentIndex)

    val totalQuestions: Int
        get() = questions.size

    val questionNumber: Int
        get() = if (questions.isEmpty()) 0 else currentIndex + 1

    val currentImageUrl: String?
        get() = currentQuestion?.question?.id?.let(imageUrls::get)
}
