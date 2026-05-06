package com.example.heritagequest.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

private val LightColorScheme = lightColorScheme(
    primary = MediterraneanBlue,
    onPrimary = WarmSurface,
    primaryContainer = MediterraneanBlueLight,
    onPrimaryContainer = WarmSurface,
    secondary = HeritageGold,
    onSecondary = MediterraneanBlueDark,
    tertiary = CoralAccent,
    onTertiary = WarmSurface,
    background = WarmCream,
    onBackground = MediterraneanBlueDark,
    surface = WarmSurface,
    onSurface = MediterraneanBlueDark,
    surfaceVariant = WarmSurfaceVariant,
    onSurfaceVariant = MutedText,
    outline = DividerTint,
    error = ErrorTerracotta,
    onError = WarmSurface
)

private val DarkColorScheme = darkColorScheme(
    primary = SeaGlass,
    onPrimary = MediterraneanBlueDark,
    primaryContainer = MediterraneanBlue,
    onPrimaryContainer = WarmSurface,
    secondary = HeritageGold,
    onSecondary = MediterraneanBlueDark,
    tertiary = CoralAccent,
    onTertiary = WarmSurface,
    background = MediterraneanBlueDark,
    onBackground = WarmSurface,
    surface = ColorTokens.darkSurface,
    onSurface = WarmSurface,
    surfaceVariant = ColorTokens.darkSurfaceVariant,
    onSurfaceVariant = SeaGlass,
    outline = MediterraneanBlueLight,
    error = ErrorTerracotta,
    onError = WarmSurface
)

private object ColorTokens {
    val darkSurface = androidx.compose.ui.graphics.Color(0xFF14283C)
    val darkSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF1D3854)
}

private val HeritageShapes = androidx.compose.material3.Shapes(
    extraSmall = RoundedCornerShape(12),
    small = RoundedCornerShape(18),
    medium = RoundedCornerShape(24),
    large = RoundedCornerShape(32),
    extraLarge = RoundedCornerShape(40)
)

@Immutable
data class HeritageExtraColors(
    val gold: androidx.compose.ui.graphics.Color,
    val cream: androidx.compose.ui.graphics.Color,
    val blueDark: androidx.compose.ui.graphics.Color,
    val coral: androidx.compose.ui.graphics.Color,
    val olive: androidx.compose.ui.graphics.Color,
    val success: androidx.compose.ui.graphics.Color,
    val mutedText: androidx.compose.ui.graphics.Color
)

val LocalHeritageExtraColors = androidx.compose.runtime.staticCompositionLocalOf {
    HeritageExtraColors(
        gold = HeritageGold,
        cream = WarmCream,
        blueDark = MediterraneanBlueDark,
        coral = CoralAccent,
        olive = OliveAccent,
        success = SuccessGreen,
        mutedText = MutedText
    )
}

@Composable
fun HeritageQuestTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme && dynamicColor.not()) DarkColorScheme else LightColorScheme

    androidx.compose.runtime.CompositionLocalProvider(
        LocalHeritageExtraColors provides HeritageExtraColors(
            gold = HeritageGold,
            cream = WarmCream,
            blueDark = MediterraneanBlueDark,
            coral = CoralAccent,
            olive = OliveAccent,
            success = SuccessGreen,
            mutedText = MutedText
        )
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = HeritageShapes,
            content = content
        )
    }
}

object HeritageTheme {
    val extraColors: HeritageExtraColors
        @Composable get() = LocalHeritageExtraColors.current
}
