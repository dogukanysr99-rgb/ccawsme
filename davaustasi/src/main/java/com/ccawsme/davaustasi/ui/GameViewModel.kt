package com.ccawsme.davaustasi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ccawsme.davaustasi.data.DavaAsamasi
import com.ccawsme.davaustasi.data.DavaSonucu
import com.ccawsme.davaustasi.data.DavaSureci
import com.ccawsme.davaustasi.data.DavaTuru
import com.ccawsme.davaustasi.data.DELIL_SECENEKLERI
import com.ccawsme.davaustasi.data.GameRepository
import com.ccawsme.davaustasi.data.GameState
import com.ccawsme.davaustasi.data.PersonelRolu
import com.ccawsme.davaustasi.data.STRATEJI_SECENEKLERI
import com.ccawsme.davaustasi.data.Secenek
import com.ccawsme.davaustasi.data.delilTepkisiUret
import com.ccawsme.davaustasi.data.karsiIddiaUret
import com.ccawsme.davaustasi.data.stratejiTepkisiUret
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToLong
import kotlin.random.Random

private const val AZAMI_OFFLINE_SANIYE = 8 * 60 * 60L
private const val KAYIT_ARALIGI_TIK = 5

class GameViewModel(private val repository: GameRepository) : ViewModel() {

    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private val _aktifDava = MutableStateFlow<DavaSureci?>(null)
    val aktifDava: StateFlow<DavaSureci?> = _aktifDava.asStateFlow()

    private var yuklendi = false

    init {
        viewModelScope.launch {
            repository.gameState.collect { kaydedilmis ->
                if (!yuklendi) {
                    yuklendi = true
                    _gameState.value = katkiUygula(kaydedilmis)
                }
            }
        }
        oyunDongusunuBaslat()
    }

    private fun katkiUygula(durum: GameState): GameState {
        val simdi = System.currentTimeMillis()
        val gecenSaniye = ((simdi - durum.sonTikZamani) / 1000L).coerceIn(0, AZAMI_OFFLINE_SANIYE)
        val kazanc = (durum.saniyeGeliri() * gecenSaniye).roundToLong()
        return durum.copy(para = durum.para + kazanc, sonTikZamani = simdi)
    }

    private fun oyunDongusunuBaslat() {
        viewModelScope.launch {
            var tikSayaci = 0
            while (true) {
                delay(1000)
                if (!yuklendi) continue
                val guncel = _gameState.value
                val kazanc = guncel.saniyeGeliri().roundToLong()
                _gameState.value = guncel.copy(
                    para = guncel.para + kazanc,
                    sonTikZamani = System.currentTimeMillis()
                )
                tikSayaci++
                if (tikSayaci >= KAYIT_ARALIGI_TIK) {
                    tikSayaci = 0
                    repository.kaydet(_gameState.value)
                }
            }
        }
    }

    fun personelSatinAl(rol: PersonelRolu) {
        val guncel = _gameState.value
        val mevcutSayi = guncel.personelSayisi(rol)
        val maliyet = rol.sonrakiMaliyet(mevcutSayi)
        if (guncel.para < maliyet) return

        val yeniDurum = when (rol) {
            PersonelRolu.STAJYER -> guncel.copy(para = guncel.para - maliyet, stajyerSayisi = guncel.stajyerSayisi + 1)
            PersonelRolu.KATIP -> guncel.copy(para = guncel.para - maliyet, katipSayisi = guncel.katipSayisi + 1)
            PersonelRolu.PARALEGAL -> guncel.copy(para = guncel.para - maliyet, paralegalSayisi = guncel.paralegalSayisi + 1)
            PersonelRolu.ORTAK_AVUKAT -> guncel.copy(para = guncel.para - maliyet, ortakSayisi = guncel.ortakSayisi + 1)
        }
        _gameState.value = yeniDurum
        kaydetSimdi()
    }

    fun davaBaslat(davaTuru: DavaTuru) {
        if (_gameState.value.enYuksekItibar < davaTuru.gerekliItibar) return
        _aktifDava.value = DavaSureci(davaTuru = davaTuru, asama = DavaAsamasi.ACILIS)
    }

    fun stratejiSec(secenek: Secenek) {
        val surec = _aktifDava.value ?: return
        _aktifDava.value = surec.copy(secilenStrateji = secenek, stratejiTepkisi = stratejiTepkisiUret(secenek))
    }

    fun delilSec(secenek: Secenek) {
        val surec = _aktifDava.value ?: return
        _aktifDava.value = surec.copy(secilenDelil = secenek, delilTepkisi = delilTepkisiUret(secenek))
    }

    fun sonrakiAsamayaGec() {
        val surec = _aktifDava.value ?: return
        _aktifDava.value = when (surec.asama) {
            DavaAsamasi.ACILIS -> surec.copy(asama = DavaAsamasi.KARSI_IDDIA, karsiIddiaMetni = karsiIddiaUret())
            DavaAsamasi.KARSI_IDDIA -> surec.copy(asama = DavaAsamasi.STRATEJI)
            DavaAsamasi.STRATEJI -> if (surec.secilenStrateji != null) surec.copy(asama = DavaAsamasi.DELIL) else surec
            DavaAsamasi.DELIL -> if (surec.secilenDelil != null) {
                surec.copy(asama = DavaAsamasi.KARAR, sonuc = sonucHesapla(surec))
            } else surec
            DavaAsamasi.KARAR -> surec
        }
    }

    private fun sonucHesapla(surec: DavaSureci): DavaSonucu {
        val strateji = surec.secilenStrateji ?: STRATEJI_SECENEKLERI[1]
        val delil = surec.secilenDelil ?: DELIL_SECENEKLERI[0]
        val guncelDurum = _gameState.value

        val basariSansi = (
            surec.davaTuru.zorluk +
                strateji.basariEtkisi +
                delil.basariEtkisi +
                guncelDurum.ortakBonusu() +
                guncelDurum.deneyimBonusu()
            ).coerceIn(0.05, 0.95)

        val kazandi = Random.nextDouble() < basariSansi

        return if (kazandi) {
            val carpan = strateji.oduleCarpan * delil.oduleCarpan
            DavaSonucu(
                kazandi = true,
                basariSansi = basariSansi,
                kazanilanPara = (surec.davaTuru.temelOdulPara * carpan).roundToLong(),
                kazanilanItibar = surec.davaTuru.itibarOdulu,
                kazanilanDeneyim = surec.davaTuru.deneyimOdulu
            )
        } else {
            DavaSonucu(
                kazandi = false,
                basariSansi = basariSansi,
                kazanilanPara = 0,
                kazanilanItibar = 0,
                kazanilanDeneyim = surec.davaTuru.deneyimOdulu / 2
            )
        }
    }

    fun davaSonucunuUygula() {
        val sonuc = _aktifDava.value?.sonuc ?: return
        val guncel = _gameState.value
        val yeniItibar = guncel.itibar + sonuc.kazanilanItibar
        _gameState.value = guncel.copy(
            para = guncel.para + sonuc.kazanilanPara,
            itibar = yeniItibar,
            enYuksekItibar = maxOf(guncel.enYuksekItibar, yeniItibar),
            deneyim = guncel.deneyim + sonuc.kazanilanDeneyim
        )
        _aktifDava.value = null
        kaydetSimdi()
    }

    fun davaKapat() {
        _aktifDava.value = null
    }

    fun kariyerIlerlet() {
        val guncel = _gameState.value
        if (!guncel.kariyerIlerletebilirMi()) return
        _gameState.value = GameState(
            unvanIndex = guncel.unvanIndex + 1,
            enYuksekItibar = guncel.enYuksekItibar,
            deneyim = guncel.deneyim
        )
        kaydetSimdi()
    }

    private fun kaydetSimdi() {
        viewModelScope.launch { repository.kaydet(_gameState.value) }
    }

    override fun onCleared() {
        super.onCleared()
        kaydetSimdi()
    }

    class Factory(private val repository: GameRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            GameViewModel(repository) as T
    }
}
