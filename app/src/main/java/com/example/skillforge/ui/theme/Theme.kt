package com.example.skillforge.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = TealPrimary,
    secondary = TealLight,
    tertiary = Pink40,
    background = CreamBackground,
    surface = CardBackground,
    onPrimary = Color.White,
    onSecondary = TealPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
)

@Composable
fun SkillforgeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme // Force Light Theme colors as per requirements

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}