package com.example.heritagequest.domain.model

import androidx.annotation.DrawableRes
import com.example.heritagequest.R

enum class Category(
    val displayName: String,
    @DrawableRes val iconRes: Int
) {
    ROMAN("Roman", R.drawable.ic_launcher_foreground),
    ISLAMIC("Islamic", R.drawable.ic_launcher_foreground),
    PUNIC("Punic", R.drawable.ic_launcher_foreground),
    MODERN("Modern", R.drawable.ic_launcher_foreground),
    NATURE("Nature", R.drawable.ic_launcher_foreground),
    CITIES("Cities", R.drawable.ic_launcher_foreground);
}
