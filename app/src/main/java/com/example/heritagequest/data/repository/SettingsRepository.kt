package com.example.heritagequest.data.repository

import com.example.heritagequest.domain.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val settings: Flow<UserSettings>

    suspend fun setTimerEnabled(enabled: Boolean)
    suspend fun setSoundEnabled(enabled: Boolean)
    suspend fun setHapticsEnabled(enabled: Boolean)
}
