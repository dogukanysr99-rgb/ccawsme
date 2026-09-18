package com.ccawsme.emsaldefteri.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ccawsme.emsaldefteri.data.Karar
import com.ccawsme.emsaldefteri.data.KararRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class KararViewModel(private val repository: KararRepository) : ViewModel() {

    private val _sorgu = MutableStateFlow("")
    val sorgu: StateFlow<String> = _sorgu.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val kararlar: StateFlow<List<Karar>> = _sorgu
        .debounce(200)
        .flatMapLatest { q -> if (q.isBlank()) repository.tumKararlar() else repository.ara(q) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun aramaGuncelle(yeni: String) {
        _sorgu.value = yeni
    }

    fun kaydet(karar: Karar, tamamlandi: () -> Unit = {}) = viewModelScope.launch {
        if (karar.id == 0L) repository.ekle(karar) else repository.guncelle(karar)
        tamamlandi()
    }

    fun sil(karar: Karar) = viewModelScope.launch {
        repository.sil(karar)
    }

    suspend fun getir(id: Long): Karar? = repository.getir(id)

    class Factory(private val repository: KararRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            KararViewModel(repository) as T
    }
}
