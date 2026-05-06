package com.example.heritagequest.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.heritagequest.domain.model.PlayerStats

@Entity(tableName = "player_stats")
data class PlayerStatsEntity(
    @PrimaryKey val id: Int = 0,
    val sitesCompleted: Int,
    val masteryPercent: Int,
    val streakDays: Int
)

fun PlayerStatsEntity.toModel(): PlayerStats =
    PlayerStats(
        sitesCompleted = sitesCompleted,
        masteryPercent = masteryPercent,
        streakDays = streakDays
    )

fun PlayerStats.toEntity(): PlayerStatsEntity =
    PlayerStatsEntity(
        id = 0,
        sitesCompleted = sitesCompleted,
        masteryPercent = masteryPercent,
        streakDays = streakDays
    )
