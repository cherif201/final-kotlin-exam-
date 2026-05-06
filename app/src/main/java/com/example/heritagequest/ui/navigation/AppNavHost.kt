package com.example.heritagequest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.heritagequest.HeritageQuestApp
import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.domain.model.Difficulty
import com.example.heritagequest.ui.screens.CategoryScreen
import com.example.heritagequest.ui.screens.DifficultyScreen
import com.example.heritagequest.ui.screens.MenuScreen
import com.example.heritagequest.ui.screens.QuizScreen
import com.example.heritagequest.ui.screens.ResultsScreen
import com.example.heritagequest.ui.screens.SplashScreen
import com.example.heritagequest.viewmodel.CategoryViewModel
import com.example.heritagequest.viewmodel.DifficultyViewModel
import com.example.heritagequest.viewmodel.HeritageQuestViewModelFactory
import com.example.heritagequest.viewmodel.MenuViewModel
import com.example.heritagequest.viewmodel.QuizEvent
import com.example.heritagequest.viewmodel.QuizViewModel
import com.example.heritagequest.viewmodel.ResultsViewModel
import com.example.heritagequest.viewmodel.SettingsViewModel

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val container = (context.applicationContext as HeritageQuestApp).container
    val factory = remember(container) { HeritageQuestViewModelFactory(container) }

    NavHost(
        navController = navController,
        startDestination = Routes.Splash,
        modifier = modifier
    ) {
        composable(Routes.Splash) {
            SplashScreen(
                onFinished = {
                    navController.navigate(Routes.Menu) {
                        popUpTo(Routes.Splash) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Menu) {
            val viewModel: MenuViewModel = viewModel(factory = factory)
            val uiState by viewModel.uiState.collectAsState()
            MenuScreen(
                uiState = uiState,
                onStartClick = { navController.navigate(Routes.Category) }
            )
        }

        composable(Routes.Category) {
            val viewModel: CategoryViewModel = viewModel(factory = factory)
            val uiState by viewModel.uiState.collectAsState()
            CategoryScreen(
                uiState = uiState,
                onCategorySelected = { category ->
                    navController.navigate(Routes.difficulty(category.name))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.Difficulty,
            arguments = listOf(navArgument(Routes.ArgCategoryId) { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString(Routes.ArgCategoryId).orEmpty()
            val category = runCatching { Category.valueOf(categoryId) }.getOrNull()
            val viewModel: DifficultyViewModel = viewModel(factory = factory)
            val settingsViewModel: SettingsViewModel = viewModel(factory = factory)
            val uiState by viewModel.uiState.collectAsState()
            val settingsUiState by settingsViewModel.uiState.collectAsState()

            LaunchedEffect(category) {
                viewModel.setCategory(category)
            }

            DifficultyScreen(
                uiState = uiState,
                settingsUiState = settingsUiState,
                onDifficultySelected = { difficulty ->
                    navController.navigate(Routes.quiz(categoryId, difficulty.name))
                },
                onTimerEnabledChange = settingsViewModel::onTimerEnabledChange,
                onSoundEnabledChange = settingsViewModel::onSoundEnabledChange,
                onHapticsEnabledChange = settingsViewModel::onHapticsEnabledChange,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.Quiz,
            arguments = listOf(
                navArgument(Routes.ArgCategoryId) { type = NavType.StringType },
                navArgument(Routes.ArgDifficulty) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString(Routes.ArgCategoryId).orEmpty()
            val difficultyId = backStackEntry.arguments?.getString(Routes.ArgDifficulty).orEmpty()
            val viewModel: QuizViewModel = viewModel(factory = factory)
            val uiState by viewModel.uiState.collectAsState()
            val category = runCatching { Category.valueOf(categoryId) }.getOrNull()
            val difficulty = runCatching { Difficulty.valueOf(difficultyId) }.getOrNull()

            LaunchedEffect(category, difficulty) {
                if (category != null && difficulty != null) {
                    viewModel.start(category, difficulty)
                }
            }

            LaunchedEffect(Unit) {
                viewModel.events.collect { event ->
                    when (event) {
                        is QuizEvent.NavigateToResults ->
                            navController.navigate(
                                Routes.results(
                                    categoryId = event.category.name,
                                    difficulty = event.difficulty.name,
                                    score = event.score,
                                    total = event.total,
                                    correct = event.correct
                                )
                            )
                    }
                }
            }

            QuizScreen(
                uiState = uiState,
                onOptionSelected = viewModel::onOptionSelected,
                onSubmit = viewModel::submitAnswer,
                onContinue = viewModel::continueAfterFeedback,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.Results,
            arguments = listOf(
                navArgument(Routes.ArgCategoryId) { type = NavType.StringType },
                navArgument(Routes.ArgDifficulty) { type = NavType.StringType },
                navArgument(Routes.ArgScore) { type = NavType.IntType },
                navArgument(Routes.ArgTotal) { type = NavType.IntType },
                navArgument(Routes.ArgCorrect) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString(Routes.ArgCategoryId).orEmpty()
            val difficultyId = backStackEntry.arguments?.getString(Routes.ArgDifficulty).orEmpty()
            val score = backStackEntry.arguments?.getInt(Routes.ArgScore) ?: 0
            val total = backStackEntry.arguments?.getInt(Routes.ArgTotal) ?: 0
            val correct = backStackEntry.arguments?.getInt(Routes.ArgCorrect) ?: 0
            val category = runCatching { Category.valueOf(categoryId) }.getOrNull()
            val difficulty = runCatching { Difficulty.valueOf(difficultyId) }.getOrNull()
            val viewModel: ResultsViewModel = viewModel(factory = factory)
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(category, difficulty, score, total, correct) {
                viewModel.setResults(
                    category = category,
                    difficulty = difficulty,
                    score = score,
                    total = total,
                    correct = correct
                )
            }

            ResultsScreen(
                uiState = uiState,
                onPlayAgain = {
                    val replayCategory = uiState.category?.name
                    val replayDifficulty = uiState.difficulty?.name
                    if (replayCategory != null && replayDifficulty != null) {
                        navController.navigate(Routes.quiz(replayCategory, replayDifficulty)) {
                            popUpTo(Routes.Menu)
                        }
                    } else {
                        navController.navigate(Routes.Category)
                    }
                },
                onBackToMenu = {
                    navController.navigate(Routes.Menu) {
                        popUpTo(Routes.Menu) { inclusive = true }
                    }
                }
            )
        }
    }
}
