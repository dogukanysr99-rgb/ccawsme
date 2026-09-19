package com.ccawsme.davaustasi.data

data class KadroUyesi(
    val id: String,
    val ad: String,
    val yas: Int,
    val gelirSaniye: Long,
    val maliyet: Long,
    val ebeveynId: String?,
    val avatarSeed: String
)

val LIDER_KADRO = KadroUyesi(
    id = "lider",
    ad = "",
    yas = 0,
    gelirSaniye = 5,
    maliyet = 0,
    ebeveynId = null,
    avatarSeed = "lider"
)

val KADRO_SABLONU = listOf(
    KadroUyesi("ortak_1", "Deniz Aydın", 34, 8, 500, "lider", "ortak-deniz"),
    KadroUyesi("ortak_2", "Cem Korkmaz", 38, 9, 700, "lider", "ortak-cem"),
    KadroUyesi("ortak_3", "Pınar Öz", 31, 7, 650, "lider", "ortak-pinar"),
    KadroUyesi("stajyer_1", "Ece Aydın", 24, 15, 1500, "ortak_1", "stajyer-ece"),
    KadroUyesi("stajyer_2", "Barış Korkmaz", 25, 16, 1800, "ortak_2", "stajyer-baris"),
    KadroUyesi("stajyer_3", "Naz Öz", 23, 14, 1600, "ortak_3", "stajyer-naz")
)

fun kadroUyesi(id: String): KadroUyesi? = KADRO_SABLONU.firstOrNull { it.id == id }
