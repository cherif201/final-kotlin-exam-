package com.example.heritagequest.data.stats

import com.example.heritagequest.data.local.db.PlayerStatsDao
import com.example.heritagequest.data.local.db.toEntity
import com.example.heritagequest.data.local.db.toModel
import com.example.heritagequest.data.questions.QuestionBank
import com.example.heritagequest.data.repository.StatsRepository
import com.example.heritagequest.domain.model.PlayerStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomStatsRepository(
    private val dao: PlayerStatsDao
) : StatsRepository {
    override val stats: Flow<PlayerStats> = dao.observeStats().map { entity ->
        entity?.toModel() ?: PlayerStats(sitesCompleted = 0, masteryPercent = 0, streakDays = 0)
    }

    override suspend fun updateAfterQuiz(correct: Int, total: Int) {
        withContext(Dispatchers.IO) {
            val current = dao.observeStats().first()?.toModel()
            val totalQuestions = QuestionBank.all().size

            val newSitesCompleted = (current?.sitesCompleted ?: 0) + correct
            val masteryPercent = if (totalQuestions == 0) {
                0
            } else {
                (newSitesCompleted * 100 / totalQuestions).coerceIn(0, 100)
            }
            val streakDays = current?.streakDays ?: 0

            dao.upsert(
                PlayerStats(
                    sitesCompleted = newSitesCompleted,
                    masteryPercent = masteryPercent,
                    streakDays = streakDays
                ).toEntity()
            )
        }
    }
}
