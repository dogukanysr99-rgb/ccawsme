package com.ccawsme.davaustasi.data

data class Bolge(
    val id: String,
    val ad: String,
    val muvekkilPotansiyeli: Long,
    val maliyet: Long,
    val gerekliItibar: Int,
    val gelirBonusu: Long,
    val konumX: Float,
    val konumY: Float
)

val BOLGELER = listOf(
    Bolge("kadikoy", "Kadıköy", 1200, 800, 0, 6, 0.20f, 0.18f),
    Bolge("besiktas", "Beşiktaş", 2200, 1600, 15, 10, 0.68f, 0.14f),
    Bolge("sisli", "Şişli", 1800, 1300, 10, 8, 0.45f, 0.32f),
    Bolge("uskudar", "Üsküdar", 2600, 2000, 25, 12, 0.15f, 0.52f),
    Bolge("bakirkoy", "Bakırköy", 3200, 2600, 35, 16, 0.72f, 0.55f),
    Bolge("maltepe", "Maltepe", 4200, 3400, 45, 20, 0.40f, 0.75f)
)

fun bolge(id: String): Bolge? = BOLGELER.firstOrNull { it.id == id }
