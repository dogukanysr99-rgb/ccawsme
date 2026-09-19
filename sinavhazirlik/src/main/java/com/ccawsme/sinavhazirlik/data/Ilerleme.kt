package com.ccawsme.sinavhazirlik.data

data class Ilerleme(
    val ogrenilenKartIdleri: Set<String> = emptySet(),
    val streakGunSayisi: Int = 0,
    val sonCalismaGunu: String = "",
    val quizDogruSayisi: Int = 0,
    val quizToplamSayisi: Int = 0,
    val dySDogruSayisi: Int = 0,
    val dySToplamSayisi: Int = 0
) {
    fun konuKartIlerlemesi(konuId: String): Pair<Int, Int> {
        val kartlar = bilgiKartlari(konuId)
        val ogrenilen = kartlar.count { it.id in ogrenilenKartIdleri }
        return ogrenilen to kartlar.size
    }

    fun quizDogrulukYuzdesi(): Int =
        if (quizToplamSayisi == 0) 0 else (quizDogruSayisi * 100 / quizToplamSayisi)

    fun dySDogrulukYuzdesi(): Int =
        if (dySToplamSayisi == 0) 0 else (dySDogruSayisi * 100 / dySToplamSayisi)
}
