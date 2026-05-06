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
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.heritagequest.ui.components.HeritageHeaderPanel
import com.example.heritagequest.ui.components.HeritageMetricCard
import com.example.heritagequest.ui.components.HeritagePrimaryButton
import com.example.heritagequest.ui.components.HeritageScreenBackground
import com.example.heritagequest.ui.components.HeroBanner
import com.example.heritagequest.ui.components.statIcon
import com.example.heritagequest.ui.state.MenuUiState
import com.example.heritagequest.ui.theme.CoralAccent
import com.example.heritagequest.ui.theme.HeritageGold
import com.example.heritagequest.ui.theme.MediterraneanBlue

@Composable
fun MenuScreen(
    uiState: MenuUiState,
    onStartClick: () -> Unit
) {
    HeritageScreenBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeritageHeaderPanel(
                title = "Tunisia\nHeritage Quest",
                subtitle = "Mediterranean stories, monuments, and cities"
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val statColors = listOf(MediterraneanBlue, HeritageGold, CoralAccent)
                val statData = if (uiState.isLoading) {
                    listOf(
                        Triple("Sites", "--", "Loading"),
                        Triple("Mastery", "--", "Loading"),
                        Triple("Streak", "--", "Loading")
                    )
                } else {
                    listOf(
                        Triple("Sites", uiState.stats.sitesCompleted.toString(), "Discovered"),
                        Triple("Mastery", "${uiState.stats.masteryPercent}%", "In Progress"),
                        Triple("Streak", uiState.stats.streakDays.toString(), "Days")
                    )
                }

                statData.forEachIndexed { index, (title, value, caption) ->
                    HeritageMetricCard(
                        title = title,
                        value = value,
                        caption = caption,
                        icon = statIcon(index),
                        iconTint = statColors[index],
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            HeroBanner(
                title = "Explore Tunisia's timeless heritage",
                subtitle = "Test your knowledge, discover hidden stories, and build your own mastery path.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            HeritagePrimaryButton(
                text = "START QUIZ",
                trailingIcon = Icons.Outlined.ArrowForward,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = onStartClick
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = null,
                    tint = MediterraneanBlue
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Settings live inside the difficulty screen",
                    color = MediterraneanBlue,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

        }
    }
}
