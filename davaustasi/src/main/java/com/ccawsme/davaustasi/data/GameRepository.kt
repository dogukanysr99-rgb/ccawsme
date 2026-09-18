package com.ccawsme.davaustasi.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "dava_ustasi_oyun")

private object Anahtar {
    val PARA = longPreferencesKey("para")
    val ITIBAR = longPreferencesKey("itibar")
    val EN_YUKSEK_ITIBAR = longPreferencesKey("en_yuksek_itibar")
    val DENEYIM = longPreferencesKey("deneyim")
    val UNVAN_INDEX = intPreferencesKey("unvan_index")
    val STAJYER = intPreferencesKey("stajyer_sayisi")
    val KATIP = intPreferencesKey("katip_sayisi")
    val PARALEGAL = intPreferencesKey("paralegal_sayisi")
    val ORTAK = intPreferencesKey("ortak_sayisi")
    val SON_TIK = longPreferencesKey("son_tik_zamani")
}

class GameRepository(private val context: Context) {

    val gameState: Flow<GameState> = context.dataStore.data.map { prefs ->
        GameState(
            para = prefs[Anahtar.PARA] ?: 0,
            itibar = prefs[Anahtar.ITIBAR] ?: 0,
            enYuksekItibar = prefs[Anahtar.EN_YUKSEK_ITIBAR] ?: 0,
            deneyim = prefs[Anahtar.DENEYIM] ?: 0,
            unvanIndex = prefs[Anahtar.UNVAN_INDEX] ?: 0,
            stajyerSayisi = prefs[Anahtar.STAJYER] ?: 0,
            katipSayisi = prefs[Anahtar.KATIP] ?: 0,
            paralegalSayisi = prefs[Anahtar.PARALEGAL] ?: 0,
            ortakSayisi = prefs[Anahtar.ORTAK] ?: 0,
            sonTikZamani = prefs[Anahtar.SON_TIK] ?: System.currentTimeMillis()
        )
    }

    suspend fun kaydet(durum: GameState) {
        context.dataStore.edit { prefs ->
            prefs[Anahtar.PARA] = durum.para
            prefs[Anahtar.ITIBAR] = durum.itibar
            prefs[Anahtar.EN_YUKSEK_ITIBAR] = durum.enYuksekItibar
            prefs[Anahtar.DENEYIM] = durum.deneyim
            prefs[Anahtar.UNVAN_INDEX] = durum.unvanIndex
            prefs[Anahtar.STAJYER] = durum.stajyerSayisi
            prefs[Anahtar.KATIP] = durum.katipSayisi
            prefs[Anahtar.PARALEGAL] = durum.paralegalSayisi
            prefs[Anahtar.ORTAK] = durum.ortakSayisi
            prefs[Anahtar.SON_TIK] = durum.sonTikZamani
        }
    }
}
