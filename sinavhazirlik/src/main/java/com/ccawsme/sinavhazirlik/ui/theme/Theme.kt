package com.ccawsme.sinavhazirlik.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AcikRenkSemasi = lightColorScheme(
    primary = Indigo40,
    primaryContainer = IndigoContainer,
    secondary = Mavi40,
    error = Kirmizi40,
    background = ArkaPlanAcik,
    surface = YuzeyAcik,
    onBackground = MetinAcik,
    onSurface = MetinAcik,
    onSurfaceVariant = MetinSoluk
)

@Composable
fun SinavHazirlikTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AcikRenkSemasi,
        typography = Typography,
        content = content
    )
}
