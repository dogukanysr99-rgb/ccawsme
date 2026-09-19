package com.ccawsme.davaustasi.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "dava_ustasi_imparatorluk")

private object Anahtar {
    val AD = stringPreferencesKey("ad")
    val AVATAR_SEED = stringPreferencesKey("avatar_seed")
    val OLUSTURULDU = booleanPreferencesKey("olusturuldu")
    val PARA = longPreferencesKey("para")
    val ITIBAR = intPreferencesKey("itibar_yuzdesi")
    val GUN = intPreferencesKey("gun")
    val HIRELI_KADRO = stringSetPreferencesKey("hireli_kadro")
    val FETHEDILEN_BOLGE = stringSetPreferencesKey("fethedilen_bolge")
    val SON_ODUL_GUNU = intPreferencesKey("son_odul_gunu")
}

class ImparatorlukRepository(private val context: Context) {

    val imparatorluk: Flow<Imparatorluk> = context.dataStore.data.map { prefs ->
        Imparatorluk(
            ad = prefs[Anahtar.AD] ?: "",
            avatarSeed = prefs[Anahtar.AVATAR_SEED] ?: "",
            olusturuldu = prefs[Anahtar.OLUSTURULDU] ?: false,
            para = prefs[Anahtar.PARA] ?: 300,
            itibarYuzdesi = prefs[Anahtar.ITIBAR] ?: 20,
            gun = prefs[Anahtar.GUN] ?: 1,
            hireliKadroIdleri = prefs[Anahtar.HIRELI_KADRO] ?: emptySet(),
            fethedilenBolgeIdleri = prefs[Anahtar.FETHEDILEN_BOLGE] ?: emptySet(),
            sonOdulGunu = prefs[Anahtar.SON_ODUL_GUNU] ?: 0
        )
    }

    suspend fun kaydet(durum: Imparatorluk) {
        context.dataStore.edit { prefs ->
            prefs[Anahtar.AD] = durum.ad
            prefs[Anahtar.AVATAR_SEED] = durum.avatarSeed
            prefs[Anahtar.OLUSTURULDU] = durum.olusturuldu
            prefs[Anahtar.PARA] = durum.para
            prefs[Anahtar.ITIBAR] = durum.itibarYuzdesi
            prefs[Anahtar.GUN] = durum.gun
            prefs[Anahtar.HIRELI_KADRO] = durum.hireliKadroIdleri
            prefs[Anahtar.FETHEDILEN_BOLGE] = durum.fethedilenBolgeIdleri
            prefs[Anahtar.SON_ODUL_GUNU] = durum.sonOdulGunu
        }
    }
}
