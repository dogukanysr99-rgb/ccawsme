package com.ccawsme.emsaldefteri.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface KararDao {

    @Query("SELECT * FROM kararlar ORDER BY eklenmeTarihi DESC")
    fun tumKararlar(): Flow<List<Karar>>

    @Query(
        """
        SELECT * FROM kararlar
        WHERE baslik LIKE '%' || :sorgu || '%'
           OR mahkeme LIKE '%' || :sorgu || '%'
           OR ozet LIKE '%' || :sorgu || '%'
           OR metin LIKE '%' || :sorgu || '%'
           OR notlar LIKE '%' || :sorgu || '%'
           OR etiketler LIKE '%' || :sorgu || '%'
        ORDER BY eklenmeTarihi DESC
        """
    )
    fun ara(sorgu: String): Flow<List<Karar>>

    @Query("SELECT * FROM kararlar WHERE id = :id")
    suspend fun getir(id: Long): Karar?

    @Insert
    suspend fun ekle(karar: Karar): Long

    @Update
    suspend fun guncelle(karar: Karar)

    @Delete
    suspend fun sil(karar: Karar)
}
