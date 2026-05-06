package com.example.heritagequest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.heritagequest.ui.components.HeritageHeaderPanel
import com.example.heritagequest.ui.components.HeritageInfoRow
import com.example.heritagequest.ui.components.HeritagePrimaryButton
import com.example.heritagequest.ui.components.HeritageScreenBackground
import com.example.heritagequest.ui.components.HeritageSectionCard
import com.example.heritagequest.ui.state.ResultsUiState
import com.example.heritagequest.ui.theme.CoralAccent
import com.example.heritagequest.ui.theme.HeritageGold
import com.example.heritagequest.ui.theme.MediterraneanBlue
import com.example.heritagequest.ui.theme.SuccessGreen

@Composable
fun ResultsScreen(
    uiState: ResultsUiState,
    onPlayAgain: () -> Unit,
    onBackToMenu: () -> Unit
) {
    HeritageScreenBackground(topBlueFraction = 0.24f) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 52.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeritageHeaderPanel(
                title = "Quiz Results",
                subtitle = "Your heritage journey summary"
            )

            Spacer(modifier = Modifier.height(16.dp))

            HeritageSectionCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Your score",
                    style = MaterialTheme.typography.titleLarge,
                    color = MediterraneanBlue
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${uiState.score} / ${uiState.total * 10}",
                    style = MaterialTheme.typography.displayLarge,
                    color = MediterraneanBlue
                )
                Text(
                    text = "${uiState.percentage}% correct",
                    style = MaterialTheme.typography.headlineSmall,
                    color = HeritageGold
                )
                Spacer(modifier = Modifier.height(14.dp))
                androidx.compose.material3.Surface(
                    color = MediterraneanBlue,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = uiState.performanceMessage,
                            color = HeritageGold,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "You're building real heritage knowledge.",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            HeritageSectionCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Breakdown",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MediterraneanBlue
                )
                Spacer(modifier = Modifier.height(10.dp))
                HeritageInfoRow(
                    icon = Icons.Outlined.CheckCircle,
                    label = "Correct Answers",
                    value = "${uiState.correct} / ${uiState.total}",
                    valueColor = SuccessGreen
                )
                HeritageInfoRow(
                    icon = Icons.Outlined.AccountBalance,
                    label = "Category",
                    value = uiState.category?.displayName ?: "Heritage"
                )
                HeritageInfoRow(
                    icon = Icons.Outlined.AutoAwesome,
                    label = "Difficulty",
                    value = uiState.difficulty?.name?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "-"
                )
                HeritageInfoRow(
                    icon = Icons.Outlined.Timer,
                    label = "Accuracy",
                    value = "${uiState.percentage}%"
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            HeritagePrimaryButton(
                text = "PLAY AGAIN",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = onPlayAgain
            )
            Spacer(modifier = Modifier.height(12.dp))
            HeritagePrimaryButton(
                text = "BACK TO MENU",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = onBackToMenu
            )
        }
    }
}
