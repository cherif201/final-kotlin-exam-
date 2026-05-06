package com.example.heritagequest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.heritagequest.di.AppContainer

class HeritageQuestViewModelFactory(
    private val container: AppContainer
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MenuViewModel::class.java) ->
                MenuViewModel(container.statsRepository) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) ->
                SettingsViewModel(container.settingsRepository) as T
            modelClass.isAssignableFrom(CategoryViewModel::class.java) ->
                CategoryViewModel() as T
            modelClass.isAssignableFrom(DifficultyViewModel::class.java) ->
                DifficultyViewModel() as T
            modelClass.isAssignableFrom(QuizViewModel::class.java) ->
                QuizViewModel(
                    container.questionRepository,
                    container.settingsRepository,
                    container.imageRepository
                ) as T
            modelClass.isAssignableFrom(ResultsViewModel::class.java) ->
                ResultsViewModel(container.statsRepository) as T
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
