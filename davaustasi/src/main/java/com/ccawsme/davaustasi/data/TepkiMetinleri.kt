package com.ccawsme.davaustasi.data

import kotlin.random.Random

private val KARSI_IDDIA_METINLERI = listOf(
    "Karşı taraf avukatı, müvekkilinin hiçbir kusuru bulunmadığını öne sürerek davanın reddini talep etti.",
    "İddia makamı, sunulan belgelerin yetersiz olduğunu iddia ederek güçlü bir itiraz yöneltti.",
    "Karşı taraf, olayların tamamen farklı geliştiğini savunarak sizi zor durumda bırakmaya çalıştı.",
    "Rakip avukat, emsal kararlara atıfta bulunarak davanın kendi lehlerine olduğunu iddia etti."
)

private val STRATEJI_TEPKI_POZITIF = listOf(
    "Hakim dikkatle not aldı, yaklaşımınız mantıklı bulundu.",
    "Karşı taraf bu noktada duraksadı, savunmanız etkili oldu.",
    "Salonda küçük bir onay mırıltısı duyuldu."
)

private val STRATEJI_TEPKI_NOTR = listOf(
    "Hakim itirazsız kaydı düştü, duruşma normal seyrinde devam etti.",
    "Beklenen bir yaklaşım oldu, kayda geçirildi."
)

private val STRATEJI_TEPKI_NEGATIF = listOf(
    "Karşı taraf hemen itiraz etti, hakim sizi biraz daha temkinli olmaya çağırdı.",
    "Riskli bir hamle oldu, salonda gerginlik hissedildi."
)

private val DELIL_TEPKI_GUCLU = listOf(
    "Sunduğunuz delil salonda etki yarattı, hakim dikkatle inceledi.",
    "Karşı taraf bu delile karşı hazırlıksız yakalandı."
)

private val DELIL_TEPKI_NORMAL = listOf(
    "Delil kayıtlara geçti, duruşma bir sonraki aşamaya geçti.",
    "Hakim delili değerlendirmeye aldı."
)

fun karsiIddiaUret(): String = KARSI_IDDIA_METINLERI[Random.nextInt(KARSI_IDDIA_METINLERI.size)]

fun stratejiTepkisiUret(secenek: Secenek): String {
    val havuz = when {
        secenek.basariEtkisi > 0 -> STRATEJI_TEPKI_POZITIF
        secenek.basariEtkisi < 0 -> STRATEJI_TEPKI_NEGATIF
        else -> STRATEJI_TEPKI_NOTR
    }
    return havuz[Random.nextInt(havuz.size)]
}

fun delilTepkisiUret(secenek: Secenek): String {
    val havuz = if (secenek.basariEtkisi >= 0.1) DELIL_TEPKI_GUCLU else DELIL_TEPKI_NORMAL
    return havuz[Random.nextInt(havuz.size)]
}
