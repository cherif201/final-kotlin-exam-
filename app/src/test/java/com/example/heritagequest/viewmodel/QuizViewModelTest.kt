package com.example.heritagequest.viewmodel

import app.cash.turbine.test
import com.example.heritagequest.MainDispatcherRule
import com.example.heritagequest.data.repository.ImageRepository
import com.example.heritagequest.data.repository.QuestionRepository
import com.example.heritagequest.data.repository.SettingsRepository
import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.domain.model.Difficulty
import com.example.heritagequest.domain.model.Question
import com.example.heritagequest.domain.model.UserSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `start loads questions and settings`() = runTest {
        val viewModel = QuizViewModel(
            questionRepository = FakeQuestionRepository(),
            settingsRepository = FakeSettingsRepository(
                UserSettings(timerEnabled = false, soundEnabled = true, hapticsEnabled = false)
            ),
            imageRepository = FakeImageRepository()
        )

        viewModel.start(Category.ROMAN, Difficulty.EASY)

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(Category.ROMAN, state.category)
        assertEquals(Difficulty.EASY, state.difficulty)
        assertEquals(1, state.totalQuestions)
        assertEquals(null, state.secondsRemaining)
        assertEquals("https://example.com/image.jpg", state.currentImageUrl)
    }

    @Test
    fun `submitAnswer scores correct answer and emits results event`() = runTest {
        val viewModel = QuizViewModel(
            questionRepository = FakeQuestionRepository(),
            settingsRepository = FakeSettingsRepository(
                UserSettings(timerEnabled = false, soundEnabled = true, hapticsEnabled = true)
            ),
            imageRepository = FakeImageRepository()
        )
        viewModel.start(Category.ROMAN, Difficulty.EASY)

        val correctIndex = viewModel.uiState.value.currentQuestion!!.correctIndex

        viewModel.events.test {
            viewModel.onOptionSelected(correctIndex)
            viewModel.submitAnswer()
            advanceTimeBy(1600)

            val event = awaitItem() as QuizEvent.NavigateToResults
            assertEquals(10, event.score)
            assertEquals(1, event.correct)
            assertEquals(Category.ROMAN, event.category)
            assertEquals(Difficulty.EASY, event.difficulty)
            cancelAndIgnoreRemainingEvents()
        }

        assertEquals(10, viewModel.uiState.value.score)
        assertTrue(viewModel.uiState.value.answers.single().wasCorrect)
    }

    @Test
    fun `timer expiry marks answer incorrect`() = runTest {
        val viewModel = QuizViewModel(
            questionRepository = FakeQuestionRepository(),
            settingsRepository = FakeSettingsRepository(
                UserSettings(timerEnabled = true, soundEnabled = true, hapticsEnabled = true)
            ),
            imageRepository = FakeImageRepository()
        )
        viewModel.start(Category.ROMAN, Difficulty.EASY)

        advanceTimeBy(16_000)

        val state = viewModel.uiState.value
        assertTrue(state.isFeedbackVisible)
        assertEquals("Time ran out", state.lastOutcome?.selectedAnswer)
        assertFalse(state.lastOutcome?.wasCorrect ?: true)
    }
}

private class FakeQuestionRepository : QuestionRepository {
    private val questions = listOf(
        Question(
            id = "roman-1",
            category = Category.ROMAN,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Amphitheatre_of_El_Jem",
            imageSource = "Wikipedia",
            prompt = "What is this monument?",
            options = listOf("El Jem Amphitheatre", "Bardo Museum", "Medina of Tunis", "Dougga"),
            correctIndex = 0,
            funFact = "One of the best preserved Roman amphitheatres."
        )
    )

    override fun getAllQuestions(): List<Question> = questions

    override fun getQuestions(category: Category, difficulty: Difficulty): List<Question> =
        questions.filter { it.category == category && it.difficulty == difficulty }
}

private class FakeSettingsRepository(
    initial: UserSettings
) : SettingsRepository {
    private val state = MutableStateFlow(initial)

    override val settings: Flow<UserSettings> = state

    override suspend fun setTimerEnabled(enabled: Boolean) {
        state.value = state.value.copy(timerEnabled = enabled)
    }

    override suspend fun setSoundEnabled(enabled: Boolean) {
        state.value = state.value.copy(soundEnabled = enabled)
    }

    override suspend fun setHapticsEnabled(enabled: Boolean) {
        state.value = state.value.copy(hapticsEnabled = enabled)
    }
}

private class FakeImageRepository : ImageRepository {
    override suspend fun resolveImageUrl(questionId: String, wikipediaTitle: String): String {
        return "https://example.com/image.jpg"
    }
}
