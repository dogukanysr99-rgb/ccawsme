package com.ccawsme.davaustasi.data

import kotlin.random.Random

data class EtkinlikSecenegi(
    val etiket: String,
    val paraEtkisi: Long,
    val itibarEtkisi: Int
)

data class Etkinlik(
    val id: String,
    val karakterAdi: String,
    val avatarSeed: String,
    val mesaj: String,
    val secenekA: EtkinlikSecenegi,
    val secenekB: EtkinlikSecenegi
)

val ETKINLIKLER = listOf(
    Etkinlik(
        "ucretsiz_danismanlik", "Ayşe Kaya", "etkinlik-ayse",
        "Ayşe Kaya, ücretsiz danışmanlık istiyor.",
        EtkinlikSecenegi("Kabul Et", 0, 6),
        EtkinlikSecenegi("Nazikçe Reddet", 40, -3)
    ),
    Etkinlik(
        "stajyer_zam", "Deniz Aydın", "ortak-deniz",
        "Ekibinizden biri zam istiyor.",
        EtkinlikSecenegi("Zam Ver", -150, 5),
        EtkinlikSecenegi("Reddet", 0, -6)
    ),
    Etkinlik(
        "roportaj", "Bir Gazeteci", "etkinlik-gazeteci",
        "Bir gazeteci röportaj teklif ediyor.",
        EtkinlikSecenegi("Röportaj Ver", -20, 10),
        EtkinlikSecenegi("Reddet", 0, 0)
    ),
    Etkinlik(
        "tesekkur_hediyesi", "Eski Müvekkiliniz", "etkinlik-hediye",
        "Eski bir müvekkiliniz teşekkür hediyesi gönderdi.",
        EtkinlikSecenegi("Kabul Et", 80, 2),
        EtkinlikSecenegi("Nazikçe İade Et", 0, 4)
    ),
    Etkinlik(
        "haksiz_rekabet", "Rakip Büro", "etkinlik-rakip",
        "Rakip bir büro sizi haksız rekabetle suçluyor.",
        EtkinlikSecenegi("Uzlaş", -250, 0),
        EtkinlikSecenegi("Görmezden Gel", 0, -8)
    ),
    Etkinlik(
        "baro_bagisi", "Yerel Baro", "etkinlik-baro",
        "Yerel barodan bağış isteniyor.",
        EtkinlikSecenegi("Bağışla", -100, 7),
        EtkinlikSecenegi("Reddet", 0, -4)
    )
)

fun rastgeleEtkinlik(): Etkinlik = ETKINLIKLER[Random.nextInt(ETKINLIKLER.size)]
