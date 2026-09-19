package com.ccawsme.emsaldefteri.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kararlar")
data class Karar(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val baslik: String,
    val mahkeme: String = "",
    val kararNo: String = "",
    val kararTarihi: String = "",
    val ozet: String = "",
    val metin: String = "",
    val notlar: String = "",
    val etiketler: String = "",
    val favori: Boolean = false,
    val eklenmeTarihi: Long = System.currentTimeMillis()
)
