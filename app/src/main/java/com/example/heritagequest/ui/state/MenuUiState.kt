package com.example.heritagequest.ui.state

import com.example.heritagequest.domain.model.PlayerStats

data class MenuUiState(
    val isLoading: Boolean = true,
    val stats: PlayerStats = PlayerStats(
        sitesCompleted = 0,
        masteryPercent = 0,
        streakDays = 0
    )
)
