package com.example.heritagequest.ui.state

import com.example.heritagequest.domain.model.UserSettings

data class SettingsUiState(
    val isLoading: Boolean = true,
    val settings: UserSettings = UserSettings(
        timerEnabled = true,
        soundEnabled = true,
        hapticsEnabled = true
    )
)
