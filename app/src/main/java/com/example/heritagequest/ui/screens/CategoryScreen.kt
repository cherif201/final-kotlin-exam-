package com.example.heritagequest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.heritagequest.data.questions.QuestionBank
import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.ui.components.HeritageCategoryProgress
import com.example.heritagequest.ui.components.HeritageHeaderPanel
import com.example.heritagequest.ui.components.HeritageScreenBackground
import com.example.heritagequest.ui.components.categoryAccent
import com.example.heritagequest.ui.components.categoryIcon
import com.example.heritagequest.ui.state.CategoryUiState
import com.example.heritagequest.ui.theme.MutedText

@Composable
fun CategoryScreen(
    uiState: CategoryUiState,
    onCategorySelected: (Category) -> Unit,
    onBack: () -> Unit
) {
    HeritageScreenBackground(topBlueFraction = 0.22f) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeritageHeaderPanel(
                title = "Choose Category",
                subtitle = "Select a heritage path to begin",
                onBack = onBack
            )

            LazyVerticalGrid(
                columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 16.dp, bottom = 56.dp)
            ) {
                items(uiState.categories) { category ->
                    val count = QuestionBank.forCategory(category).size
                    val accent = categoryAccent(category)
                    val progress = (count / 13f).coerceIn(0.18f, 1f)

                    Card(
                        onClick = { onCategorySelected(category) },
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            androidx.compose.material3.Surface(
                                color = accent.copy(alpha = 0.12f),
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Icon(
                                    imageVector = categoryIcon(category),
                                    contentDescription = null,
                                    tint = accent,
                                    modifier = Modifier.padding(18.dp)
                                )
                            }
                            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(top = 10.dp))
                            Text(
                                text = category.displayName,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF13345B)
                            )
                            Text(
                                text = "$count questions",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MutedText
                            )
                            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(top = 12.dp))
                            HeritageCategoryProgress(
                                progress = progress,
                                accent = accent,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
