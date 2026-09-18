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
import com.ccawsme.davaustasi.data.GameRepository
import com.ccawsme.davaustasi.ui.GameViewModel
import com.ccawsme.davaustasi.ui.screens.AnaEkran
import com.ccawsme.davaustasi.ui.screens.DavaEkrani
import com.ccawsme.davaustasi.ui.theme.DavaUstasiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = GameRepository(applicationContext)

        setContent {
            DavaUstasiTheme {
                val viewModel: GameViewModel = viewModel(factory = GameViewModel.Factory(repository))
                val durum by viewModel.gameState.collectAsState()
                val aktifDava by viewModel.aktifDava.collectAsState()

                Surface(modifier = Modifier.fillMaxSize()) {
                    AnaEkran(
                        durum = durum,
                        onPersonelSatinAl = viewModel::personelSatinAl,
                        onDavaBaslat = viewModel::davaBaslat,
                        onKariyerIlerlet = viewModel::kariyerIlerlet
                    )

                    aktifDava?.let { surec ->
                        DavaEkrani(
                            surec = surec,
                            onStratejiSec = viewModel::stratejiSec,
                            onDelilSec = viewModel::delilSec,
                            onTamam = viewModel::davaSonucunuUygula,
                            onKapat = viewModel::davaKapat
                        )
                    }
                }
            }
        }
    }
}
