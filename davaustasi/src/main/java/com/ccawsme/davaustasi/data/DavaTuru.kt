package com.ccawsme.davaustasi.data

data class DavaTuru(
    val ad: String,
    val gerekliItibar: Long,
    val temelOdulPara: Long,
    val itibarOdulu: Long,
    val deneyimOdulu: Long,
    val zorluk: Double
)

val DAVA_TURLERI = listOf(
    DavaTuru("Sulh Hukuk Davası", gerekliItibar = 0, temelOdulPara = 50, itibarOdulu = 5, deneyimOdulu = 10, zorluk = 0.65),
    DavaTuru("İş Hukuku Davası", gerekliItibar = 100, temelOdulPara = 150, itibarOdulu = 15, deneyimOdulu = 25, zorluk = 0.60),
    DavaTuru("Ticari Dava", gerekliItibar = 500, temelOdulPara = 400, itibarOdulu = 35, deneyimOdulu = 50, zorluk = 0.55),
    DavaTuru("Ağır Ceza Davası", gerekliItibar = 2000, temelOdulPara = 1000, itibarOdulu = 80, deneyimOdulu = 100, zorluk = 0.50),
    DavaTuru("Yargıtay Temyiz Başvurusu", gerekliItibar = 8000, temelOdulPara = 3000, itibarOdulu = 200, deneyimOdulu = 250, zorluk = 0.45)
)

data class Secenek(
    val etiket: String,
    val aciklama: String,
    val basariEtkisi: Double,
    val oduleCarpan: Double
)

val STRATEJI_SECENEKLERI = listOf(
    Secenek("Agresif Savunma", "Riskli ama kazanırsan ödül yüksek", basariEtkisi = -0.05, oduleCarpan = 1.3),
    Secenek("Dengeli Yaklaşım", "Standart, öngörülebilir bir strateji", basariEtkisi = 0.0, oduleCarpan = 1.0),
    Secenek("Usul İtirazı", "Temkinli, başarı şansı yüksek ama ödül düşük", basariEtkisi = 0.10, oduleCarpan = 0.8)
)

val DELIL_SECENEKLERI = listOf(
    Secenek("Tanık İfadesi", "Orta düzey güvenilirlik", basariEtkisi = 0.05, oduleCarpan = 1.0),
    Secenek("Belge Delili", "Güçlü ve ikna edici", basariEtkisi = 0.08, oduleCarpan = 1.05),
    Secenek("Bilirkişi Raporu", "En güçlü delil ama biraz zaman kaybettirir", basariEtkisi = 0.12, oduleCarpan = 0.95)
)

data class Unvan(val ad: String, val gerekliEnYuksekItibar: Long)

val UNVANLAR = listOf(
    Unvan("Stajyer Avukat", 0),
    Unvan("Avukat", 300),
    Unvan("Kıdemli Avukat", 1500),
    Unvan("Ortak Avukat", 6000),
    Unvan("Baro Başkanı", 20000)
)
