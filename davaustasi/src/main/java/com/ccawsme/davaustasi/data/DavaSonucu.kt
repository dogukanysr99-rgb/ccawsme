package com.ccawsme.davaustasi.data

import kotlin.random.Random

data class DavaSonucu(
    val kazandi: Boolean,
    val basariSansi: Double,
    val kazanilanPara: Long,
    val itibarDegisimi: Int,
    val kazanilanKariyerPuani: Long
)

fun davaSonucuUret(karakter: Karakter): DavaSonucu {
    val basariSansi = (0.4 + karakter.bilgi / 200.0 + karakter.itibar / 500.0).coerceIn(0.1, 0.9)
    val kazandi = Random.nextDouble() < basariSansi

    return if (kazandi) {
        val carpan = 1.0 + karakter.unvanIndex * 0.3
        DavaSonucu(
            kazandi = true,
            basariSansi = basariSansi,
            kazanilanPara = (Random.nextInt(300, 801) * carpan).toLong(),
            itibarDegisimi = Random.nextInt(5, 16),
            kazanilanKariyerPuani = Random.nextInt(200, 401).toLong()
        )
    } else {
        DavaSonucu(
            kazandi = false,
            basariSansi = basariSansi,
            kazanilanPara = 0,
            itibarDegisimi = -2,
            kazanilanKariyerPuani = Random.nextInt(50, 101).toLong()
        )
    }
}
