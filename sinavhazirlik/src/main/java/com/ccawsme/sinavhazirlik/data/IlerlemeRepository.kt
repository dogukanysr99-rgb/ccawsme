package com.ccawsme.sinavhazirlik.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "sinav_hazirlik_ilerleme")

private object Anahtar {
    val OGRENILEN_KARTLAR = stringSetPreferencesKey("ogrenilen_kartlar")
    val STREAK = intPreferencesKey("streak_gun_sayisi")
    val SON_CALISMA_GUNU = stringPreferencesKey("son_calisma_gunu")
    val QUIZ_DOGRU = intPreferencesKey("quiz_dogru")
    val QUIZ_TOPLAM = intPreferencesKey("quiz_toplam")
    val DYS_DOGRU = intPreferencesKey("dys_dogru")
    val DYS_TOPLAM = intPreferencesKey("dys_toplam")
}

class IlerlemeRepository(private val context: Context) {

    val ilerleme: Flow<Ilerleme> = context.dataStore.data.map { prefs ->
        Ilerleme(
            ogrenilenKartIdleri = prefs[Anahtar.OGRENILEN_KARTLAR] ?: emptySet(),
            streakGunSayisi = prefs[Anahtar.STREAK] ?: 0,
            sonCalismaGunu = prefs[Anahtar.SON_CALISMA_GUNU] ?: "",
            quizDogruSayisi = prefs[Anahtar.QUIZ_DOGRU] ?: 0,
            quizToplamSayisi = prefs[Anahtar.QUIZ_TOPLAM] ?: 0,
            dySDogruSayisi = prefs[Anahtar.DYS_DOGRU] ?: 0,
            dySToplamSayisi = prefs[Anahtar.DYS_TOPLAM] ?: 0
        )
    }

    suspend fun kaydet(ilerleme: Ilerleme) {
        context.dataStore.edit { prefs ->
            prefs[Anahtar.OGRENILEN_KARTLAR] = ilerleme.ogrenilenKartIdleri
            prefs[Anahtar.STREAK] = ilerleme.streakGunSayisi
            prefs[Anahtar.SON_CALISMA_GUNU] = ilerleme.sonCalismaGunu
            prefs[Anahtar.QUIZ_DOGRU] = ilerleme.quizDogruSayisi
            prefs[Anahtar.QUIZ_TOPLAM] = ilerleme.quizToplamSayisi
            prefs[Anahtar.DYS_DOGRU] = ilerleme.dySDogruSayisi
            prefs[Anahtar.DYS_TOPLAM] = ilerleme.dySToplamSayisi
        }
    }
}
