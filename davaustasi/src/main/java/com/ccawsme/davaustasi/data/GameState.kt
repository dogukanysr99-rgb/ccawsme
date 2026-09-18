package com.ccawsme.davaustasi.data

data class GameState(
    val para: Long = 0,
    val itibar: Long = 0,
    val enYuksekItibar: Long = 0,
    val deneyim: Long = 0,
    val unvanIndex: Int = 0,
    val stajyerSayisi: Int = 0,
    val katipSayisi: Int = 0,
    val paralegalSayisi: Int = 0,
    val ortakSayisi: Int = 0,
    val sonTikZamani: Long = System.currentTimeMillis()
) {
    fun personelSayisi(rol: PersonelRolu): Int = when (rol) {
        PersonelRolu.STAJYER -> stajyerSayisi
        PersonelRolu.KATIP -> katipSayisi
        PersonelRolu.PARALEGAL -> paralegalSayisi
        PersonelRolu.ORTAK_AVUKAT -> ortakSayisi
    }

    fun gelirCarpani(): Double = 1.0 + unvanIndex * 0.5

    fun saniyeGeliri(): Double =
        PersonelRolu.entries.sumOf { rol -> personelSayisi(rol) * rol.saniyeGeliri } * gelirCarpani()

    fun ortakBonusu(): Double = ortakSayisi * 0.01

    fun deneyimBonusu(): Double = (deneyim / 1000.0 * 0.01).coerceAtMost(0.20)

    fun mevcutUnvan(): Unvan = UNVANLAR[unvanIndex]

    fun sonrakiUnvan(): Unvan? = UNVANLAR.getOrNull(unvanIndex + 1)

    fun kariyerIlerletebilirMi(): Boolean {
        val sonraki = sonrakiUnvan() ?: return false
        return enYuksekItibar >= sonraki.gerekliEnYuksekItibar
    }
}
