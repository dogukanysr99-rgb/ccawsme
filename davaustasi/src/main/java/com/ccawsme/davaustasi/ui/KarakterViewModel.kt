package com.ccawsme.davaustasi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ccawsme.davaustasi.data.Aktivite
import com.ccawsme.davaustasi.data.AktiviteId
import com.ccawsme.davaustasi.data.DavaSonucu
import com.ccawsme.davaustasi.data.Karakter
import com.ccawsme.davaustasi.data.KarakterRepository
import com.ccawsme.davaustasi.data.Konum
import com.ccawsme.davaustasi.data.ZamanDilimi
import com.ccawsme.davaustasi.data.davaSonucuUret
import com.ccawsme.davaustasi.data.yuzdeyeSinirla
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class KarakterViewModel(private val repository: KarakterRepository) : ViewModel() {

    private val _karakter = MutableStateFlow(Karakter())
    val karakter: StateFlow<Karakter> = _karakter.asStateFlow()

    private val _aktifDavaSonucu = MutableStateFlow<DavaSonucu?>(null)
    val aktifDavaSonucu: StateFlow<DavaSonucu?> = _aktifDavaSonucu.asStateFlow()

    private val _guncelKonum = MutableStateFlow(Konum.EV)
    val guncelKonum: StateFlow<Konum> = _guncelKonum.asStateFlow()

    private var yuklendi = false

    init {
        viewModelScope.launch {
            repository.karakter.collect { kaydedilmis ->
                if (!yuklendi) {
                    yuklendi = true
                    _karakter.value = kaydedilmis
                }
            }
        }
    }

    fun karakterOlustur(ad: String, cildIndex: Int, sacIndex: Int, kiyafetIndex: Int) {
        _karakter.value = Karakter(
            ad = ad,
            olusturuldu = true,
            cildTonuIndex = cildIndex,
            sacRengiIndex = sacIndex,
            kiyafetRengiIndex = kiyafetIndex
        )
        kaydetSimdi()
    }

    fun aktiviteUygula(aktivite: Aktivite) {
        val guncel = _karakter.value
        _guncelKonum.value = aktivite.konum

        if (aktivite.id == AktiviteId.UYU) {
            _karakter.value = guncel.copy(enerji = 100, gun = guncel.gun + 1, zamanDilimiIndex = 0)
            kaydetSimdi()
            return
        }

        if (aktivite.id == AktiviteId.DAVA_AL) {
            _aktifDavaSonucu.value = davaSonucuUret(guncel)
            val sonrasi = guncel.copy(
                enerji = (guncel.enerji - aktivite.enerjiMaliyeti).yuzdeyeSinirla(),
                mutluluk = (guncel.mutluluk + aktivite.mutlulukEtkisi).yuzdeyeSinirla(),
                bilgi = (guncel.bilgi + aktivite.bilgiEtkisi).yuzdeyeSinirla()
            )
            _karakter.value = ilerletZamanDilimi(sonrasi)
            return
        }

        val paraDegisimi = if (aktivite.id == AktiviteId.CALIS) {
            guncel.mevcutUnvan().calismaGeliri
        } else {
            aktivite.paraEtkisi
        }

        var yeni = guncel.copy(
            enerji = (guncel.enerji - aktivite.enerjiMaliyeti).yuzdeyeSinirla(),
            mutluluk = (guncel.mutluluk + aktivite.mutlulukEtkisi).yuzdeyeSinirla(),
            bilgi = (guncel.bilgi + aktivite.bilgiEtkisi).yuzdeyeSinirla(),
            itibar = (guncel.itibar + aktivite.itibarEtkisi).coerceAtLeast(0),
            para = (guncel.para + paraDegisimi).coerceAtLeast(0),
            kariyerPuani = guncel.kariyerPuani + aktivite.kariyerPuaniEtkisi
        )
        yeni = terfiUygula(yeni)
        yeni = ilerletZamanDilimi(yeni)

        _karakter.value = yeni
        kaydetSimdi()
    }

    fun davaSonucunuKapat() {
        val sonuc = _aktifDavaSonucu.value ?: return
        val guncel = _karakter.value
        var yeni = guncel.copy(
            para = (guncel.para + sonuc.kazanilanPara).coerceAtLeast(0),
            itibar = (guncel.itibar + sonuc.itibarDegisimi).coerceAtLeast(0),
            kariyerPuani = guncel.kariyerPuani + sonuc.kazanilanKariyerPuani
        )
        yeni = terfiUygula(yeni)
        _karakter.value = yeni
        _aktifDavaSonucu.value = null
        kaydetSimdi()
    }

    private fun ilerletZamanDilimi(karakter: Karakter): Karakter {
        val sonSlotIndex = ZamanDilimi.entries.size - 1
        val sonrakiIndex = (karakter.zamanDilimiIndex + 1).coerceAtMost(sonSlotIndex)
        return karakter.copy(zamanDilimiIndex = sonrakiIndex)
    }

    private fun terfiUygula(karakter: Karakter): Karakter {
        var k = karakter
        while (k.terfiEdebilirMi()) {
            k = k.copy(unvanIndex = k.unvanIndex + 1)
        }
        return k
    }

    private fun kaydetSimdi() {
        viewModelScope.launch { repository.kaydet(_karakter.value) }
    }

    override fun onCleared() {
        super.onCleared()
        kaydetSimdi()
    }

    class Factory(private val repository: KarakterRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            KarakterViewModel(repository) as T
    }
}
