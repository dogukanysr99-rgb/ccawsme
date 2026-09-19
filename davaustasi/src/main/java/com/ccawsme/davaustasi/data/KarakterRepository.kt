package com.ccawsme.davaustasi.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "dava_ustasi_karakter")

private object Anahtar {
    val AD = stringPreferencesKey("ad")
    val OLUSTURULDU = booleanPreferencesKey("olusturuldu")
    val CILT_TONU = intPreferencesKey("cilt_tonu")
    val SAC_RENGI = intPreferencesKey("sac_rengi")
    val KIYAFET_RENGI = intPreferencesKey("kiyafet_rengi")
    val GUN = intPreferencesKey("gun")
    val ZAMAN_DILIMI = intPreferencesKey("zaman_dilimi")
    val ENERJI = intPreferencesKey("enerji")
    val MUTLULUK = intPreferencesKey("mutluluk")
    val BILGI = intPreferencesKey("bilgi")
    val ITIBAR = intPreferencesKey("itibar")
    val PARA = longPreferencesKey("para")
    val KARIYER_PUANI = longPreferencesKey("kariyer_puani")
    val UNVAN_INDEX = intPreferencesKey("unvan_index")
}

class KarakterRepository(private val context: Context) {

    val karakter: Flow<Karakter> = context.dataStore.data.map { prefs ->
        Karakter(
            ad = prefs[Anahtar.AD] ?: "",
            olusturuldu = prefs[Anahtar.OLUSTURULDU] ?: false,
            cildTonuIndex = prefs[Anahtar.CILT_TONU] ?: 0,
            sacRengiIndex = prefs[Anahtar.SAC_RENGI] ?: 0,
            kiyafetRengiIndex = prefs[Anahtar.KIYAFET_RENGI] ?: 0,
            gun = prefs[Anahtar.GUN] ?: 1,
            zamanDilimiIndex = prefs[Anahtar.ZAMAN_DILIMI] ?: 0,
            enerji = prefs[Anahtar.ENERJI] ?: 100,
            mutluluk = prefs[Anahtar.MUTLULUK] ?: 70,
            bilgi = prefs[Anahtar.BILGI] ?: 10,
            itibar = prefs[Anahtar.ITIBAR] ?: 0,
            para = prefs[Anahtar.PARA] ?: 0,
            kariyerPuani = prefs[Anahtar.KARIYER_PUANI] ?: 0,
            unvanIndex = prefs[Anahtar.UNVAN_INDEX] ?: 0
        )
    }

    suspend fun kaydet(karakter: Karakter) {
        context.dataStore.edit { prefs ->
            prefs[Anahtar.AD] = karakter.ad
            prefs[Anahtar.OLUSTURULDU] = karakter.olusturuldu
            prefs[Anahtar.CILT_TONU] = karakter.cildTonuIndex
            prefs[Anahtar.SAC_RENGI] = karakter.sacRengiIndex
            prefs[Anahtar.KIYAFET_RENGI] = karakter.kiyafetRengiIndex
            prefs[Anahtar.GUN] = karakter.gun
            prefs[Anahtar.ZAMAN_DILIMI] = karakter.zamanDilimiIndex
            prefs[Anahtar.ENERJI] = karakter.enerji
            prefs[Anahtar.MUTLULUK] = karakter.mutluluk
            prefs[Anahtar.BILGI] = karakter.bilgi
            prefs[Anahtar.ITIBAR] = karakter.itibar
            prefs[Anahtar.PARA] = karakter.para
            prefs[Anahtar.KARIYER_PUANI] = karakter.kariyerPuani
            prefs[Anahtar.UNVAN_INDEX] = karakter.unvanIndex
        }
    }
}
