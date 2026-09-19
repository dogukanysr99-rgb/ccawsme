package com.ccawsme.davaustasi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ccawsme.davaustasi.data.ImparatorlukRepository
import com.ccawsme.davaustasi.ui.ImparatorlukViewModel
import com.ccawsme.davaustasi.ui.screens.AnaEkran
import com.ccawsme.davaustasi.ui.screens.EtkinlikDialog
import com.ccawsme.davaustasi.ui.screens.KarakterOlusturmaEkrani
import com.ccawsme.davaustasi.ui.theme.DavaUstasiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = ImparatorlukRepository(applicationContext)

        setContent {
            DavaUstasiTheme {
                val viewModel: ImparatorlukViewModel = viewModel(factory = ImparatorlukViewModel.Factory(repository))
                val durum by viewModel.imparatorluk.collectAsState()
                val aktifEtkinlik by viewModel.aktifEtkinlik.collectAsState()

                Surface(modifier = Modifier.fillMaxSize()) {
                    if (!durum.olusturuldu) {
                        KarakterOlusturmaEkrani(onOlustur = viewModel::karakterOlustur)
                    } else {
                        AnaEkran(
                            durum = durum,
                            onIseAl = viewModel::kadroIseAl,
                            onFethet = viewModel::bolgeFethet,
                            onGunlukOdul = viewModel::gunlukOduluAl,
                            onSonrakiGun = viewModel::sonrakiGun
                        )

                        aktifEtkinlik?.let { etkinlik ->
                            EtkinlikDialog(etkinlik = etkinlik, onSecim = viewModel::etkinlikSecimiUygula)
                        }
                    }
                }
            }
        }
    }
}
