package com.example.heritagequest.ui.screens

import android.view.HapticFeedbackConstants
import android.view.SoundEffectConstants
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CrisisAlert
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.heritagequest.ui.components.HeritageHeaderPanel
import com.example.heritagequest.ui.components.HeritageOptionCard
import com.example.heritagequest.ui.components.HeritagePrimaryButton
import com.example.heritagequest.ui.components.HeritageScreenBackground
import com.example.heritagequest.ui.components.HeritageSectionCard
import com.example.heritagequest.ui.components.HeritageTag
import com.example.heritagequest.ui.components.categoryAccent
import com.example.heritagequest.ui.state.QuizUiState
import com.example.heritagequest.ui.theme.HeritageGold
import com.example.heritagequest.ui.theme.MediterraneanBlue
import com.example.heritagequest.ui.theme.MediterraneanBlueDark

@Composable
fun QuizScreen(
    uiState: QuizUiState,
    onOptionSelected: (Int) -> Unit,
    onSubmit: () -> Unit,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    val view = LocalView.current

    LaunchedEffect(uiState.isFeedbackVisible, uiState.lastOutcome) {
        val outcome = uiState.lastOutcome ?: return@LaunchedEffect
        if (!uiState.isFeedbackVisible) {
            return@LaunchedEffect
        }

        if (uiState.hapticsEnabled) {
            view.performHapticFeedback(
                if (outcome.wasCorrect) {
                    HapticFeedbackConstants.CONFIRM
                } else {
                    HapticFeedbackConstants.REJECT
                }
            )
        }

        if (uiState.soundEnabled) {
            view.playSoundEffect(
                if (outcome.wasCorrect) SoundEffectConstants.CLICK
                else SoundEffectConstants.NAVIGATION_DOWN
            )
        }
    }

    HeritageScreenBackground(topBlueFraction = 0.2f) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 52.dp)
        ) {
            HeritageHeaderPanel(
                title = "Question ${uiState.questionNumber} of ${uiState.totalQuestions}",
                subtitle = uiState.category?.displayName ?: "Heritage quiz",
                onBack = onBack
            )

            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                uiState.secondsRemaining?.let { seconds ->
                    HeritageTag(
                        text = "00:${seconds.toString().padStart(2, '0')}",
                        icon = Icons.Outlined.Timer,
                        containerColor = categoryAccent(uiState.category ?: com.example.heritagequest.domain.model.Category.ROMAN),
                        modifier = Modifier.align(Alignment.End)
                    )
                }

                HeritageSectionCard {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            uiState.currentImageUrl != null -> {
                                AsyncImage(
                                    model = uiState.currentImageUrl,
                                    contentDescription = uiState.currentQuestion?.question?.prompt,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            uiState.isImageLoading -> {
                                CircularProgressIndicator(color = MediterraneanBlue)
                            }

                            else -> {
                                Text(
                                    text = "Image unavailable",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MediterraneanBlueDark
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Source: ${uiState.currentQuestion?.question?.imageSource ?: "Wikipedia"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF6E7A88)
                    )
                }

                Text(
                    text = uiState.currentQuestion?.question?.prompt ?: "Loading question...",
                    style = MaterialTheme.typography.displayMedium,
                    color = MediterraneanBlueDark
                )

                uiState.currentQuestion?.options?.forEachIndexed { index, option ->
                    HeritageOptionCard(
                        letter = ('A' + index).toString(),
                        text = option,
                        selected = uiState.selectedIndex == index,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { if (!uiState.isFeedbackVisible) onOptionSelected(index) }
                    )
                }

                if (uiState.difficulty?.showHints == true) {
                    OutlinedButton(
                        onClick = {},
                        enabled = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 34.dp)
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Outlined.CrisisAlert,
                            contentDescription = null,
                            tint = HeritageGold
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                        Text(text = "Hint coming later")
                    }
                }

                HeritagePrimaryButton(
                    text = "SUBMIT ANSWER",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSubmit
                )

                if (uiState.isFeedbackVisible && uiState.lastOutcome != null) {
                    HeritageSectionCard {
                        Text(
                            text = uiState.feedbackTitle.orEmpty(),
                            style = MaterialTheme.typography.headlineSmall,
                            color = if (uiState.lastOutcome?.wasCorrect == true) MediterraneanBlue else categoryAccent(uiState.category ?: com.example.heritagequest.domain.model.Category.PUNIC)
                        )
                        uiState.feedbackBody?.let {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = it, style = MaterialTheme.typography.bodyLarge)
                        }
                        if (uiState.lastOutcome?.wasCorrect == false) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = uiState.lastOutcome?.funFact.orEmpty(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF6E7A88)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            HeritagePrimaryButton(
                                text = "CONTINUE",
                                modifier = Modifier.fillMaxWidth(),
                                onClick = onContinue
                            )
                        }
                    }
                }
            }
        }
    }
}
