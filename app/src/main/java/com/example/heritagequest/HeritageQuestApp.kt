package com.example.heritagequest

import android.app.Application
import com.example.heritagequest.di.AppContainer

class HeritageQuestApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
