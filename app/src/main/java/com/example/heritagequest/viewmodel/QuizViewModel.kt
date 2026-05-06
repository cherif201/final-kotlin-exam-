package com.example.heritagequest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heritagequest.data.repository.ImageRepository
import com.example.heritagequest.data.repository.QuestionRepository
import com.example.heritagequest.data.repository.SettingsRepository
import com.example.heritagequest.domain.model.AnswerOutcome
import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.domain.model.Difficulty
import com.example.heritagequest.ui.state.QuizQuestion
import com.example.heritagequest.ui.state.QuizUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class QuizViewModel(
    private val questionRepository: QuestionRepository,
    private val settingsRepository: SettingsRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<QuizEvent>()
    val events: SharedFlow<QuizEvent> = _events.asSharedFlow()

    private var timerJob: Job? = null
    private var feedbackJob: Job? = null

    fun start(category: Category, difficulty: Difficulty) {
        val currentState = _uiState.value
        if (
            !currentState.isLoading &&
            currentState.category == category &&
            currentState.difficulty == difficulty &&
            currentState.questions.isNotEmpty()
        ) {
            return
        }

        viewModelScope.launch {
            val settings = settingsRepository.settings.first()
            val questions = questionRepository.getQuestions(category, difficulty)
                .shuffled()
                .take(8)
                .map { question ->
                    val options = question.options.shuffled()
                    val correctOption = question.options[question.correctIndex]
                    val correctIndex = options.indexOf(correctOption)
                    QuizQuestion(question, options, correctIndex)
                }

            if (questions.isEmpty()) {
                _uiState.value = QuizUiState(
                    isLoading = false,
                    category = category,
                    difficulty = difficulty,
                    errorMessage = "No questions available for this selection.",
                    timerEnabled = settings.timerEnabled,
                    soundEnabled = settings.soundEnabled,
                    hapticsEnabled = settings.hapticsEnabled
                )
                return@launch
            }

            _uiState.value = QuizUiState(
                isLoading = false,
                category = category,
                difficulty = difficulty,
                questions = questions,
                secondsRemaining = initialSeconds(settings.timerEnabled, difficulty),
                timerEnabled = settings.timerEnabled,
                soundEnabled = settings.soundEnabled,
                hapticsEnabled = settings.hapticsEnabled,
                isImageLoading = true
            )
            preloadImages(questions)
            restartTimer()
        }
    }

    fun onOptionSelected(index: Int) {
        _uiState.update { state ->
            if (state.isFeedbackVisible) state else state.copy(selectedIndex = index)
        }
    }

    fun submitAnswer() {
        val state = _uiState.value
        if (state.isFeedbackVisible) {
            return
        }

        val question = state.currentQuestion ?: return
        val selectedIndex = state.selectedIndex ?: return

        val isCorrect = selectedIndex == question.correctIndex
        val outcome = AnswerOutcome(
            wasCorrect = isCorrect,
            selectedAnswer = question.options[selectedIndex],
            correctAnswer = question.options[question.correctIndex],
            funFact = question.question.funFact
        )
        showAnswerOutcome(outcome)
    }

    fun continueAfterFeedback() {
        val state = _uiState.value
        if (!state.isFeedbackVisible || state.lastOutcome?.wasCorrect == true) {
            return
        }

        viewModelScope.launch {
            moveNextOrFinish()
        }
    }

    private fun showTimedOutAnswer() {
        val state = _uiState.value
        if (state.isFeedbackVisible) {
            return
        }

        val question = state.currentQuestion ?: return
        val outcome = AnswerOutcome(
            wasCorrect = false,
            selectedAnswer = "Time ran out",
            correctAnswer = question.options[question.correctIndex],
            funFact = question.question.funFact
        )
        showAnswerOutcome(outcome, timedOut = true)
    }

    private fun showAnswerOutcome(
        outcome: AnswerOutcome,
        timedOut: Boolean = false
    ) {
        timerJob?.cancel()
        feedbackJob?.cancel()
        val scoreDelta = if (outcome.wasCorrect) 10 else 0

        _uiState.update {
            it.copy(
                score = it.score + scoreDelta,
                isFeedbackVisible = true,
                lastOutcome = outcome,
                feedbackTitle = when {
                    outcome.wasCorrect -> "Correct! +10 points"
                    timedOut -> "Time's up!"
                    else -> "Incorrect!"
                },
                feedbackBody = when {
                    outcome.wasCorrect -> "Nice catch. Moving to the next question..."
                    timedOut -> "The right answer was ${outcome.correctAnswer}."
                    else -> "The right answer was ${outcome.correctAnswer}."
                },
                answers = it.answers + outcome
            )
        }

        if (outcome.wasCorrect) {
            feedbackJob = viewModelScope.launch {
                delay(1500)
                moveNextOrFinish()
            }
        }
    }

    fun clearSelection() {
        _uiState.update { state ->
            if (state.isFeedbackVisible) state else state.copy(selectedIndex = null)
        }
    }

    private suspend fun moveNextOrFinish() {
        val state = _uiState.value
        val category = state.category
        val difficulty = state.difficulty
        if (state.isLastQuestion && category != null && difficulty != null) {
            val total = state.questions.size
            val correct = state.answers.count { it.wasCorrect }
            _events.emit(
                QuizEvent.NavigateToResults(
                    category = category,
                    difficulty = difficulty,
                    score = state.score,
                    total = total,
                    correct = correct
                )
            )
            return
        }

        _uiState.update {
            it.copy(
                currentIndex = it.currentIndex + 1,
                selectedIndex = null,
                isFeedbackVisible = false,
                lastOutcome = null,
                feedbackTitle = null,
                feedbackBody = null,
                secondsRemaining = initialSeconds(it.timerEnabled, it.difficulty)
            )
        }
        restartTimer()
    }

    private fun preloadImages(questions: List<QuizQuestion>) {
        viewModelScope.launch {
            val resolvedImages = questions.associate { quizQuestion ->
                quizQuestion.question.id to imageRepository.resolveImageUrl(
                    questionId = quizQuestion.question.id,
                    wikipediaTitle = quizQuestion.question.wikipediaTitle
                )
            }
            _uiState.update {
                it.copy(
                    imageUrls = resolvedImages,
                    isImageLoading = false
                )
            }
        }
    }

    private fun restartTimer() {
        timerJob?.cancel()
        val state = _uiState.value
        if (!state.timerEnabled || state.isFeedbackVisible) {
            return
        }

        timerJob = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                val current = _uiState.value
                val remaining = current.secondsRemaining ?: break
                if (remaining <= 1) {
                    _uiState.update { it.copy(secondsRemaining = 0) }
                    showTimedOutAnswer()
                    break
                }
                _uiState.update { it.copy(secondsRemaining = remaining - 1) }
            }
        }
    }

    private fun initialSeconds(
        timerEnabled: Boolean,
        difficulty: Difficulty?
    ): Int? {
        if (!timerEnabled || difficulty == null) {
            return null
        }
        return difficulty.secondsPerQuestion
    }

    override fun onCleared() {
        timerJob?.cancel()
        feedbackJob?.cancel()
        super.onCleared()
    }
}
