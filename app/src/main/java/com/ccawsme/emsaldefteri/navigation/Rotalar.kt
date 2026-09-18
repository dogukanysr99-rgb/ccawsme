package com.ccawsme.emsaldefteri.navigation

object Rotalar {
    const val LISTE = "liste"
    const val YENI = "yeni"
    const val DETAY = "detay/{kararId}"
    const val DUZENLE = "duzenle/{kararId}"

    fun detay(id: Long) = "detay/$id"
    fun duzenle(id: Long) = "duzenle/$id"
}
