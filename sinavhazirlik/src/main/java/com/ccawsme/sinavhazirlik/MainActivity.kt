package com.ccawsme.sinavhazirlik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ccawsme.sinavhazirlik.data.IlerlemeRepository
import com.ccawsme.sinavhazirlik.ui.SinavViewModel
import com.ccawsme.sinavhazirlik.ui.screens.AnaEkran
import com.ccawsme.sinavhazirlik.ui.screens.BilgiKartlariEkrani
import com.ccawsme.sinavhazirlik.ui.screens.CikmisSorularEkrani
import com.ccawsme.sinavhazirlik.ui.screens.DogruYanlisEkrani
import com.ccawsme.sinavhazirlik.ui.screens.KonuDetayEkrani
import com.ccawsme.sinavhazirlik.ui.screens.QuizEkrani
import com.ccawsme.sinavhazirlik.ui.theme.SinavHazirlikTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = IlerlemeRepository(applicationContext)

        setContent {
            SinavHazirlikTheme {
                val viewModel: SinavViewModel = viewModel(factory = SinavViewModel.Factory(repository))
                val ilerleme by viewModel.ilerleme.collectAsState()
                val navController = rememberNavController()

                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(navController = navController, startDestination = "dashboard") {
                        composable("dashboard") {
                            AnaEkran(
                                ilerleme = ilerleme,
                                onKonuSec = { id -> navController.navigate("konu/$id") },
                                onTumKartlar = { navController.navigate("kartlar/tumu") },
                                onTumQuiz = { navController.navigate("quiz/tumu") },
                                onTumDogruYanlis = { navController.navigate("dogruyanlis/tumu") },
                                onCikmisSorular = { navController.navigate("cikmis") }
                            )
                        }
                        composable("cikmis") {
                            CikmisSorularEkrani(
                                onYilSec = { yil ->
                                    navController.navigate("cikmis-quiz/${yil ?: 0}")
                                },
                                onGeri = { navController.popBackStack() }
                            )
                        }
                        composable(
                            "cikmis-quiz/{yil}",
                            arguments = listOf(navArgument("yil") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val yilArg = backStackEntry.arguments?.getInt("yil") ?: 0
                            val yil = if (yilArg == 0) null else yilArg
                            LaunchedEffect(yil) {
                                viewModel.quizBaslatCikmis(yil)
                            }
                            val oturum by viewModel.quizOturumu.collectAsState()
                            oturum?.let {
                                QuizEkrani(
                                    oturum = it,
                                    onCevapSec = viewModel::quizCevaplaSec,
                                    onSonraki = viewModel::quizSonrakiSoru,
                                    onTekrarBaslat = { viewModel.quizBaslatCikmis(yil) },
                                    onGeri = {
                                        viewModel.quizKapat()
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                        composable(
                            "konu/{konuId}",
                            arguments = listOf(navArgument("konuId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val konuId = backStackEntry.arguments?.getString("konuId") ?: "tumu"
                            KonuDetayEkrani(
                                konuId = konuId,
                                ilerleme = ilerleme,
                                onGeri = { navController.popBackStack() },
                                onKartlar = { navController.navigate("kartlar/$konuId") },
                                onQuiz = { navController.navigate("quiz/$konuId") },
                                onDogruYanlis = { navController.navigate("dogruyanlis/$konuId") }
                            )
                        }
                        composable(
                            "kartlar/{konuId}",
                            arguments = listOf(navArgument("konuId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val konuId = backStackEntry.arguments?.getString("konuId") ?: "tumu"
                            BilgiKartlariEkrani(
                                konuId = konuId,
                                onOgrenildi = viewModel::kartOgrenildiIsaretle,
                                onTekrarEt = viewModel::kartTekrarEtIsaretle,
                                onGeri = { navController.popBackStack() }
                            )
                        }
                        composable(
                            "quiz/{konuId}",
                            arguments = listOf(navArgument("konuId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val konuId = backStackEntry.arguments?.getString("konuId") ?: "tumu"
                            LaunchedEffect(konuId) {
                                viewModel.quizBaslat(if (konuId == "tumu") null else konuId)
                            }
                            val oturum by viewModel.quizOturumu.collectAsState()
                            oturum?.let {
                                QuizEkrani(
                                    oturum = it,
                                    onCevapSec = viewModel::quizCevaplaSec,
                                    onSonraki = viewModel::quizSonrakiSoru,
                                    onTekrarBaslat = { viewModel.quizBaslat(if (konuId == "tumu") null else konuId) },
                                    onGeri = {
                                        viewModel.quizKapat()
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                        composable(
                            "dogruyanlis/{konuId}",
                            arguments = listOf(navArgument("konuId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val konuId = backStackEntry.arguments?.getString("konuId") ?: "tumu"
                            LaunchedEffect(konuId) {
                                viewModel.dysBaslat(if (konuId == "tumu") null else konuId)
                            }
                            val oturum by viewModel.dysOturumu.collectAsState()
                            oturum?.let {
                                DogruYanlisEkrani(
                                    oturum = it,
                                    onCevapSec = viewModel::dysCevaplaSec,
                                    onSonraki = viewModel::dysSonrakiSoru,
                                    onTekrarBaslat = { viewModel.dysBaslat(if (konuId == "tumu") null else konuId) },
                                    onGeri = {
                                        viewModel.dysKapat()
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
