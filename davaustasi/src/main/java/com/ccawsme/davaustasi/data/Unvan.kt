package com.ccawsme.davaustasi.data

data class Unvan(
    val ad: String,
    val gerekliKariyerPuani: Long,
    val calismaGeliri: Long
)

val UNVANLAR = listOf(
    Unvan(ad = "Hukuk Stajyeri", gerekliKariyerPuani = 0, calismaGeliri = 40),
    Unvan(ad = "Avukat Yardımcısı", gerekliKariyerPuani = 500, calismaGeliri = 90),
    Unvan(ad = "Avukat", gerekliKariyerPuani = 2000, calismaGeliri = 180),
    Unvan(ad = "Kıdemli Avukat", gerekliKariyerPuani = 6000, calismaGeliri = 350),
    Unvan(ad = "Ortak Avukat", gerekliKariyerPuani = 15000, calismaGeliri = 650),
    Unvan(ad = "Baro Başkanı", gerekliKariyerPuani = 40000, calismaGeliri = 1200)
)
