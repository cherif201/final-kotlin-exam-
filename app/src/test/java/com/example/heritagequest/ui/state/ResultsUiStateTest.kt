package com.example.heritagequest.ui.state

import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.domain.model.Difficulty
import org.junit.Assert.assertEquals
import org.junit.Test

class ResultsUiStateTest {
    @Test
    fun `performanceMessage uses top bucket for 90 percent and above`() {
        val state = ResultsUiState(
            score = 70,
            total = 8,
            correct = 8,
            category = Category.ROMAN,
            difficulty = Difficulty.HARD
        )

        assertEquals(100, state.percentage)
        assertEquals("Heritage Master!", state.performanceMessage)
    }

    @Test
    fun `performanceMessage uses lower bucket under 50 percent`() {
        val state = ResultsUiState(score = 20, total = 8, correct = 2)

        assertEquals(25, state.percentage)
        assertEquals("Plenty of monuments left to discover.", state.performanceMessage)
    }
}
