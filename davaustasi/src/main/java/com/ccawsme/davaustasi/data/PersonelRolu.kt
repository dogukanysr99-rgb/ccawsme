package com.ccawsme.davaustasi.data

import kotlin.math.pow
import kotlin.math.roundToLong

enum class PersonelRolu(
    val etiket: String,
    val aciklama: String,
    val temelMaliyet: Long,
    val maliyetCarpani: Double,
    val saniyeGeliri: Double
) {
    STAJYER(
        etiket = "Stajyer",
        aciklama = "Evrak işleriyle uğraşır, az ama düzenli gelir getirir",
        temelMaliyet = 10,
        maliyetCarpani = 1.15,
        saniyeGeliri = 0.1
    ),
    KATIP(
        etiket = "Kâtip",
        aciklama = "Dosyaları takip eder, idari işleri yürütür",
        temelMaliyet = 60,
        maliyetCarpani = 1.16,
        saniyeGeliri = 0.6
    ),
    PARALEGAL(
        etiket = "Paralegal",
        aciklama = "Dava hazırlığına destek olur, iyi gelir getirir",
        temelMaliyet = 320,
        maliyetCarpani = 1.18,
        saniyeGeliri = 3.0
    ),
    ORTAK_AVUKAT(
        etiket = "Ortak Avukat",
        aciklama = "Büyük davalara girer, hem gelir hem dava başarı şansı katar",
        temelMaliyet = 1800,
        maliyetCarpani = 1.21,
        saniyeGeliri = 16.0
    );

    fun sonrakiMaliyet(mevcutSayi: Int): Long =
        (temelMaliyet * maliyetCarpani.pow(mevcutSayi)).roundToLong().coerceAtLeast(1)
}
