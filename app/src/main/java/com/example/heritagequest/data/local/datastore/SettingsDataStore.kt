package com.example.heritagequest.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.heritagequest.data.repository.SettingsRepository
import com.example.heritagequest.domain.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val SETTINGS_NAME = "heritagequest_settings"
private val Context.settingsDataStore by preferencesDataStore(name = SETTINGS_NAME)

private val TIMER_ENABLED = booleanPreferencesKey("timer_enabled")
private val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
private val HAPTICS_ENABLED = booleanPreferencesKey("haptics_enabled")

class DataStoreSettingsRepository(
    private val context: Context
) : SettingsRepository {
    override val settings: Flow<UserSettings> = context.settingsDataStore.data.map { prefs ->
        UserSettings(
            timerEnabled = prefs[TIMER_ENABLED] ?: true,
            soundEnabled = prefs[SOUND_ENABLED] ?: true,
            hapticsEnabled = prefs[HAPTICS_ENABLED] ?: true
        )
    }

    override suspend fun setTimerEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { prefs ->
            prefs[TIMER_ENABLED] = enabled
        }
    }

    override suspend fun setSoundEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { prefs ->
            prefs[SOUND_ENABLED] = enabled
        }
    }

    override suspend fun setHapticsEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { prefs ->
            prefs[HAPTICS_ENABLED] = enabled
        }
    }
}
