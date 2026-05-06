package com.example.heritagequest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.heritagequest.domain.model.Difficulty
import com.example.heritagequest.ui.components.HeritageHeaderPanel
import com.example.heritagequest.ui.components.HeritagePrimaryButton
import com.example.heritagequest.ui.components.HeritageScreenBackground
import com.example.heritagequest.ui.state.DifficultyUiState
import com.example.heritagequest.ui.state.SettingsUiState
import com.example.heritagequest.ui.theme.CoralAccent
import com.example.heritagequest.ui.theme.HeritageGold
import com.example.heritagequest.ui.theme.MediterraneanBlue

@Composable
fun DifficultyScreen(
    uiState: DifficultyUiState,
    settingsUiState: SettingsUiState,
    onDifficultySelected: (Difficulty) -> Unit,
    onTimerEnabledChange: (Boolean) -> Unit,
    onSoundEnabledChange: (Boolean) -> Unit,
    onHapticsEnabledChange: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    HeritageScreenBackground(topBlueFraction = 0.24f) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 48.dp)
        ) {
            HeritageHeaderPanel(
                title = "Select Difficulty",
                subtitle = uiState.category?.displayName ?: "Choose your challenge",
                onBack = onBack
            )

            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                uiState.difficulties.forEach { difficulty ->
                    DifficultyCard(
                        difficulty = difficulty,
                        onClick = { onDifficultySelected(difficulty) }
                    )
                }

                GameOptionsCard(
                    settingsUiState = settingsUiState,
                    onTimerEnabledChange = onTimerEnabledChange,
                    onSoundEnabledChange = onSoundEnabledChange,
                    onHapticsEnabledChange = onHapticsEnabledChange
                )
            }
        }
    }
}

@Composable
private fun DifficultyCard(
    difficulty: Difficulty,
    onClick: () -> Unit
) {
    val accent = when (difficulty) {
        Difficulty.EASY -> MediterraneanBlue
        Difficulty.MEDIUM -> HeritageGold
        Difficulty.HARD -> CoralAccent
    }
    val title = difficulty.name.lowercase().replaceFirstChar { it.uppercase() }
    val description = when (difficulty) {
        Difficulty.EASY -> "Famous landmarks"
        Difficulty.MEDIUM -> "Historical sites"
        Difficulty.HARD -> "Archaeological details"
    }
    val detail = when (difficulty) {
        Difficulty.HARD -> "${difficulty.secondsPerQuestion} seconds · no hints"
        else -> "${difficulty.secondsPerQuestion} seconds per question"
    }
    val icon = when (difficulty) {
        Difficulty.EASY -> Icons.Outlined.AccountBalance
        Difficulty.MEDIUM -> Icons.Outlined.AutoAwesome
        Difficulty.HARD -> Icons.Outlined.Bolt
    }

    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.Surface(
                color = accent.copy(alpha = 0.12f),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.padding(18.dp)
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.headlineMedium, color = MediterraneanBlue)
                Text(text = description, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = detail, style = MaterialTheme.typography.bodyMedium, color = accent)
            }
            HeritagePrimaryButton(
                text = "PLAY",
                modifier = Modifier.width(108.dp),
                onClick = onClick
            )
        }
    }
}

@Composable
private fun GameOptionsCard(
    settingsUiState: SettingsUiState,
    onTimerEnabledChange: (Boolean) -> Unit,
    onSoundEnabledChange: (Boolean) -> Unit,
    onHapticsEnabledChange: (Boolean) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Game Options",
                style = MaterialTheme.typography.headlineSmall,
                color = MediterraneanBlue
            )
            Spacer(modifier = Modifier.height(14.dp))
            ToggleRow(
                icon = Icons.Outlined.Timer,
                label = "Timer Enabled",
                checked = settingsUiState.settings.timerEnabled,
                onCheckedChange = onTimerEnabledChange
            )
            ToggleRow(
                icon = Icons.Outlined.NotificationsActive,
                label = "Sound Effects",
                checked = settingsUiState.settings.soundEnabled,
                onCheckedChange = onSoundEnabledChange
            )
            ToggleRow(
                icon = Icons.Outlined.PhoneAndroid,
                label = "Haptic Feedback",
                checked = settingsUiState.settings.hapticsEnabled,
                onCheckedChange = onHapticsEnabledChange
            )
        }
    }
}

@Composable
private fun ToggleRow(
    icon: ImageVector,
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MediterraneanBlue)
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleMedium
        )
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
