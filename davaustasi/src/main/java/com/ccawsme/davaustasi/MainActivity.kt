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
import com.ccawsme.davaustasi.data.KarakterRepository
import com.ccawsme.davaustasi.ui.KarakterViewModel
import com.ccawsme.davaustasi.ui.screens.AnaEkran
import com.ccawsme.davaustasi.ui.screens.DavaSonucuDialog
import com.ccawsme.davaustasi.ui.screens.KarakterOlusturmaEkrani
import com.ccawsme.davaustasi.ui.theme.DavaUstasiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = KarakterRepository(applicationContext)

        setContent {
            DavaUstasiTheme {
                val viewModel: KarakterViewModel = viewModel(factory = KarakterViewModel.Factory(repository))
                val karakter by viewModel.karakter.collectAsState()
                val guncelKonum by viewModel.guncelKonum.collectAsState()
                val aktifDavaSonucu by viewModel.aktifDavaSonucu.collectAsState()

                Surface(modifier = Modifier.fillMaxSize()) {
                    if (!karakter.olusturuldu) {
                        KarakterOlusturmaEkrani(onOlustur = viewModel::karakterOlustur)
                    } else {
                        AnaEkran(
                            karakter = karakter,
                            guncelKonum = guncelKonum,
                            onAktiviteSec = viewModel::aktiviteUygula
                        )

                        aktifDavaSonucu?.let { sonuc ->
                            DavaSonucuDialog(sonuc = sonuc, onKapat = viewModel::davaSonucunuKapat)
                        }
                    }
                }
            }
        }
    }
}
