package com.ccawsme.sinavhazirlik.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Konu(
    val id: String,
    val ad: String,
    val renk: Color,
    val ikon: ImageVector
)

val KONULAR = listOf(
    Konu("anayasa", "Anayasa Hukuku", Color(0xFF4361EE), Icons.Filled.Gavel),
    Konu("medeni", "Medeni Hukuk", Color(0xFF3A86FF), Icons.Filled.Group),
    Konu("ceza", "Ceza Hukuku", Color(0xFFE63946), Icons.Filled.Lock)
)

fun konu(id: String): Konu? = KONULAR.firstOrNull { it.id == id }
