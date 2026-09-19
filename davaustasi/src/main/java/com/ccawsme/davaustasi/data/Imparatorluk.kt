package com.ccawsme.davaustasi.data

private const val BASLANGIC_MUVEKKIL = 50L
private const val GUNLUK_ODUL_PARA = 150L

data class Imparatorluk(
    val ad: String = "",
    val avatarSeed: String = "",
    val olusturuldu: Boolean = false,
    val para: Long = 300,
    val itibarYuzdesi: Int = 20,
    val gun: Int = 1,
    val hireliKadroIdleri: Set<String> = emptySet(),
    val fethedilenBolgeIdleri: Set<String> = emptySet(),
    val sonOdulGunu: Int = 0
) {
    fun toplamGelirSaniye(): Long {
        val kadroGeliri = hireliKadroIdleri.sumOf { id -> kadroUyesi(id)?.gelirSaniye ?: 0 }
        val bolgeGeliri = fethedilenBolgeIdleri.sumOf { id -> bolge(id)?.gelirBonusu ?: 0 }
        return LIDER_KADRO.gelirSaniye + kadroGeliri + bolgeGeliri
    }

    fun toplamMuvekkilSayisi(): Long =
        BASLANGIC_MUVEKKIL + fethedilenBolgeIdleri.sumOf { id -> bolge(id)?.muvekkilPotansiyeli ?: 0 }

    fun kadroKilitliMi(uye: KadroUyesi): Boolean {
        if (uye.id in hireliKadroIdleri) return false
        val ebeveyn = uye.ebeveynId ?: return false
        return ebeveyn != "lider" && ebeveyn !in hireliKadroIdleri
    }

    fun gunlukOdulAlinabilirMi(): Boolean = sonOdulGunu < gun

    fun gunlukOdulMiktari(): Long = GUNLUK_ODUL_PARA
}
