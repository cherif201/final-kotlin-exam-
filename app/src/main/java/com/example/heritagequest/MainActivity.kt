package com.example.heritagequest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.heritagequest.ui.theme.HeritageQuestTheme
import com.example.heritagequest.ui.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HeritageQuestTheme {
                AppNavHost()
            }
        }
    }
}