package com.ccawsme.sinavhazirlik.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ccawsme.sinavhazirlik.data.DogruYanlisSorusu
import com.ccawsme.sinavhazirlik.data.Ilerleme
import com.ccawsme.sinavhazirlik.data.IlerlemeRepository
import com.ccawsme.sinavhazirlik.data.QuizSorusu
import com.ccawsme.sinavhazirlik.data.dogruYanlisSorulari
import com.ccawsme.sinavhazirlik.data.quizSorulari
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

data class QuizOturumu(
    val sorular: List<QuizSorusu>,
    val index: Int = 0,
    val secilenIndex: Int? = null,
    val dogruSayisi: Int = 0,
    val bitti: Boolean = false
)

data class DysOturumu(
    val sorular: List<DogruYanlisSorusu>,
    val index: Int = 0,
    val cevaplandi: Boolean = false,
    val sonCevapDogruMu: Boolean? = null,
    val dogruSayisi: Int = 0,
    val bitti: Boolean = false
)

class SinavViewModel(private val repository: IlerlemeRepository) : ViewModel() {

    private val _ilerleme = MutableStateFlow(Ilerleme())
    val ilerleme: StateFlow<Ilerleme> = _ilerleme.asStateFlow()

    private val _quizOturumu = MutableStateFlow<QuizOturumu?>(null)
    val quizOturumu: StateFlow<QuizOturumu?> = _quizOturumu.asStateFlow()

    private val _dysOturumu = MutableStateFlow<DysOturumu?>(null)
    val dysOturumu: StateFlow<DysOturumu?> = _dysOturumu.asStateFlow()

    private var yuklendi = false

    init {
        viewModelScope.launch {
            repository.ilerleme.collect { kaydedilmis ->
                if (!yuklendi) {
                    yuklendi = true
                    _ilerleme.value = kaydedilmis
                }
            }
        }
    }

    fun kartOgrenildiIsaretle(kartId: String) {
        val guncel = _ilerleme.value
        _ilerleme.value = guncel.copy(ogrenilenKartIdleri = guncel.ogrenilenKartIdleri + kartId)
        streakGuncelle()
        kaydetSimdi()
    }

    fun kartTekrarEtIsaretle(kartId: String) {
        val guncel = _ilerleme.value
        _ilerleme.value = guncel.copy(ogrenilenKartIdleri = guncel.ogrenilenKartIdleri - kartId)
        kaydetSimdi()
    }

    fun quizBaslat(konuId: String?) {
        _quizOturumu.value = QuizOturumu(sorular = quizSorulari(konuId).shuffled())
    }

    fun quizCevaplaSec(secilenIndex: Int) {
        val o = _quizOturumu.value ?: return
        if (o.secilenIndex != null) return
        val soru = o.sorular[o.index]
        val dogruMu = secilenIndex == soru.dogruIndex
        _quizOturumu.value = o.copy(
            secilenIndex = secilenIndex,
            dogruSayisi = o.dogruSayisi + if (dogruMu) 1 else 0
        )
    }

    fun quizSonrakiSoru() {
        val o = _quizOturumu.value ?: return
        if (o.index + 1 >= o.sorular.size) {
            val guncel = _ilerleme.value
            _ilerleme.value = guncel.copy(
                quizDogruSayisi = guncel.quizDogruSayisi + o.dogruSayisi,
                quizToplamSayisi = guncel.quizToplamSayisi + o.sorular.size
            )
            _quizOturumu.value = o.copy(bitti = true)
            streakGuncelle()
            kaydetSimdi()
        } else {
            _quizOturumu.value = o.copy(index = o.index + 1, secilenIndex = null)
        }
    }

    fun quizKapat() {
        _quizOturumu.value = null
    }

    fun dysBaslat(konuId: String?) {
        _dysOturumu.value = DysOturumu(sorular = dogruYanlisSorulari(konuId).shuffled())
    }

    fun dysCevaplaSec(cevapDogru: Boolean) {
        val o = _dysOturumu.value ?: return
        if (o.cevaplandi) return
        val soru = o.sorular[o.index]
        val dogruMu = cevapDogru == soru.dogruMu
        _dysOturumu.value = o.copy(
            cevaplandi = true,
            sonCevapDogruMu = dogruMu,
            dogruSayisi = o.dogruSayisi + if (dogruMu) 1 else 0
        )
    }

    fun dysSonrakiSoru() {
        val o = _dysOturumu.value ?: return
        if (o.index + 1 >= o.sorular.size) {
            val guncel = _ilerleme.value
            _ilerleme.value = guncel.copy(
                dySDogruSayisi = guncel.dySDogruSayisi + o.dogruSayisi,
                dySToplamSayisi = guncel.dySToplamSayisi + o.sorular.size
            )
            _dysOturumu.value = o.copy(bitti = true)
            streakGuncelle()
            kaydetSimdi()
        } else {
            _dysOturumu.value = o.copy(index = o.index + 1, cevaplandi = false, sonCevapDogruMu = null)
        }
    }

    fun dysKapat() {
        _dysOturumu.value = null
    }

    private fun streakGuncelle() {
        val bugun = LocalDate.now().toString()
        val guncel = _ilerleme.value
        if (guncel.sonCalismaGunu == bugun) return
        val dun = LocalDate.now().minusDays(1).toString()
        val yeniStreak = if (guncel.sonCalismaGunu == dun) guncel.streakGunSayisi + 1 else 1
        _ilerleme.value = guncel.copy(streakGunSayisi = yeniStreak, sonCalismaGunu = bugun)
    }

    private fun kaydetSimdi() {
        viewModelScope.launch { repository.kaydet(_ilerleme.value) }
    }

    override fun onCleared() {
        super.onCleared()
        kaydetSimdi()
    }

    class Factory(private val repository: IlerlemeRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SinavViewModel(repository) as T
    }
}
