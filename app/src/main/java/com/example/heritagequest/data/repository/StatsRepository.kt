package com.example.heritagequest.data.repository

import com.example.heritagequest.domain.model.PlayerStats
import kotlinx.coroutines.flow.Flow

interface StatsRepository {
    val stats: Flow<PlayerStats>

    suspend fun updateAfterQuiz(correct: Int, total: Int)
}
