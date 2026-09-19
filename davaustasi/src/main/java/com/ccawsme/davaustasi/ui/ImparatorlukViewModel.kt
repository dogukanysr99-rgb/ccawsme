package com.ccawsme.davaustasi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ccawsme.davaustasi.data.Bolge
import com.ccawsme.davaustasi.data.Etkinlik
import com.ccawsme.davaustasi.data.EtkinlikSecenegi
import com.ccawsme.davaustasi.data.Imparatorluk
import com.ccawsme.davaustasi.data.ImparatorlukRepository
import com.ccawsme.davaustasi.data.KadroUyesi
import com.ccawsme.davaustasi.data.rastgeleEtkinlik
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val KAYIT_ARALIGI_TIK = 5

class ImparatorlukViewModel(private val repository: ImparatorlukRepository) : ViewModel() {

    private val _imparatorluk = MutableStateFlow(Imparatorluk())
    val imparatorluk: StateFlow<Imparatorluk> = _imparatorluk.asStateFlow()

    private val _aktifEtkinlik = MutableStateFlow<Etkinlik?>(null)
    val aktifEtkinlik: StateFlow<Etkinlik?> = _aktifEtkinlik.asStateFlow()

    private var yuklendi = false

    init {
        viewModelScope.launch {
            repository.imparatorluk.collect { kaydedilmis ->
                if (!yuklendi) {
                    yuklendi = true
                    _imparatorluk.value = kaydedilmis
                }
            }
        }
        gelirDongusunuBaslat()
    }

    private fun gelirDongusunuBaslat() {
        viewModelScope.launch {
            var tikSayaci = 0
            while (true) {
                delay(1000)
                if (!yuklendi) continue
                val guncel = _imparatorluk.value
                if (guncel.olusturuldu) {
                    _imparatorluk.value = guncel.copy(para = guncel.para + guncel.toplamGelirSaniye())
                    tikSayaci++
                    if (tikSayaci >= KAYIT_ARALIGI_TIK) {
                        tikSayaci = 0
                        kaydetSimdi()
                    }
                }
            }
        }
    }

    fun karakterOlustur(ad: String, avatarSeed: String) {
        _imparatorluk.value = Imparatorluk(ad = ad, avatarSeed = avatarSeed, olusturuldu = true)
        kaydetSimdi()
    }

    fun kadroIseAl(uye: KadroUyesi) {
        val guncel = _imparatorluk.value
        if (uye.id in guncel.hireliKadroIdleri) return
        if (guncel.kadroKilitliMi(uye)) return
        if (guncel.para < uye.maliyet) return
        _imparatorluk.value = guncel.copy(
            para = guncel.para - uye.maliyet,
            hireliKadroIdleri = guncel.hireliKadroIdleri + uye.id
        )
        kaydetSimdi()
    }

    fun bolgeFethet(b: Bolge) {
        val guncel = _imparatorluk.value
        if (b.id in guncel.fethedilenBolgeIdleri) return
        if (guncel.itibarYuzdesi < b.gerekliItibar) return
        if (guncel.para < b.maliyet) return
        _imparatorluk.value = guncel.copy(
            para = guncel.para - b.maliyet,
            fethedilenBolgeIdleri = guncel.fethedilenBolgeIdleri + b.id
        )
        kaydetSimdi()
    }

    fun gunlukOduluAl() {
        val guncel = _imparatorluk.value
        if (!guncel.gunlukOdulAlinabilirMi()) return
        _imparatorluk.value = guncel.copy(
            para = guncel.para + guncel.gunlukOdulMiktari(),
            sonOdulGunu = guncel.gun
        )
        kaydetSimdi()
    }

    fun sonrakiGun() {
        val guncel = _imparatorluk.value
        _imparatorluk.value = guncel.copy(gun = guncel.gun + 1)
        if (_aktifEtkinlik.value == null) {
            _aktifEtkinlik.value = rastgeleEtkinlik()
        }
        kaydetSimdi()
    }

    fun etkinlikSecimiUygula(secenek: EtkinlikSecenegi) {
        val guncel = _imparatorluk.value
        _imparatorluk.value = guncel.copy(
            para = (guncel.para + secenek.paraEtkisi).coerceAtLeast(0),
            itibarYuzdesi = (guncel.itibarYuzdesi + secenek.itibarEtkisi).coerceIn(0, 100)
        )
        _aktifEtkinlik.value = null
        kaydetSimdi()
    }

    private fun kaydetSimdi() {
        viewModelScope.launch { repository.kaydet(_imparatorluk.value) }
    }

    override fun onCleared() {
        super.onCleared()
        kaydetSimdi()
    }

    class Factory(private val repository: ImparatorlukRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ImparatorlukViewModel(repository) as T
    }
}
