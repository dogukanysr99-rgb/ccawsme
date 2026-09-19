package com.ccawsme.davaustasi.data

data class Karakter(
    val ad: String = "",
    val olusturuldu: Boolean = false,
    val cildTonuIndex: Int = 0,
    val sacRengiIndex: Int = 0,
    val kiyafetRengiIndex: Int = 0,

    val gun: Int = 1,
    val zamanDilimiIndex: Int = 0,

    val enerji: Int = 100,
    val mutluluk: Int = 70,
    val bilgi: Int = 10,
    val itibar: Int = 0,
    val para: Long = 0,

    val kariyerPuani: Long = 0,
    val unvanIndex: Int = 0
) {
    fun zamanDilimi(): ZamanDilimi = ZamanDilimi.entries[zamanDilimiIndex]

    fun mevcutUnvan(): Unvan = UNVANLAR[unvanIndex]

    fun sonrakiUnvan(): Unvan? = UNVANLAR.getOrNull(unvanIndex + 1)

    fun terfiEdebilirMi(): Boolean {
        val sonraki = sonrakiUnvan() ?: return false
        return kariyerPuani >= sonraki.gerekliKariyerPuani
    }
}

fun Int.yuzdeyeSinirla(): Int = coerceIn(0, 100)
