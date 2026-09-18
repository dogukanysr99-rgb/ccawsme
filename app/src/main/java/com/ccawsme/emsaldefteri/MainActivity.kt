package com.ccawsme.emsaldefteri

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ccawsme.emsaldefteri.data.AppDatabase
import com.ccawsme.emsaldefteri.data.KararRepository
import com.ccawsme.emsaldefteri.navigation.Rotalar
import com.ccawsme.emsaldefteri.ui.KararViewModel
import com.ccawsme.emsaldefteri.ui.screens.DetayEkrani
import com.ccawsme.emsaldefteri.ui.screens.DuzenleEkrani
import com.ccawsme.emsaldefteri.ui.screens.ListeEkrani
import com.ccawsme.emsaldefteri.ui.theme.EmsalDefteriTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getInstance(applicationContext)
        val repository = KararRepository(database.kararDao())

        setContent {
            EmsalDefteriTheme {
                val viewModel: KararViewModel = viewModel(factory = KararViewModel.Factory(repository))
                val navController = rememberNavController()

                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(navController = navController, startDestination = Rotalar.LISTE) {
                        composable(Rotalar.LISTE) {
                            ListeEkrani(
                                viewModel = viewModel,
                                onKararTikla = { id -> navController.navigate(Rotalar.detay(id)) },
                                onYeniEkle = { navController.navigate(Rotalar.YENI) }
                            )
                        }
                        composable(Rotalar.YENI) {
                            DuzenleEkrani(
                                kararId = null,
                                viewModel = viewModel,
                                onGeri = { navController.popBackStack() }
                            )
                        }
                        composable(
                            route = Rotalar.DETAY,
                            arguments = listOf(navArgument("kararId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getLong("kararId") ?: 0L
                            DetayEkrani(
                                kararId = id,
                                viewModel = viewModel,
                                onGeri = { navController.popBackStack() },
                                onDuzenle = { navController.navigate(Rotalar.duzenle(id)) }
                            )
                        }
                        composable(
                            route = Rotalar.DUZENLE,
                            arguments = listOf(navArgument("kararId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getLong("kararId") ?: 0L
                            DuzenleEkrani(
                                kararId = id,
                                viewModel = viewModel,
                                onGeri = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
