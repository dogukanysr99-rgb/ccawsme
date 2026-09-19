package com.ccawsme.emsaldefteri.data

import kotlinx.coroutines.flow.Flow

class KararRepository(private val dao: KararDao) {
    fun tumKararlar(): Flow<List<Karar>> = dao.tumKararlar()
    fun ara(sorgu: String): Flow<List<Karar>> = dao.ara(sorgu)
    suspend fun getir(id: Long): Karar? = dao.getir(id)
    suspend fun ekle(karar: Karar): Long = dao.ekle(karar)
    suspend fun guncelle(karar: Karar) = dao.guncelle(karar)
    suspend fun sil(karar: Karar) = dao.sil(karar)
}
