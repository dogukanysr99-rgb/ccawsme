package com.ccawsme.sinavhazirlik.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ccawsme.sinavhazirlik.data.BILGI_KARTLARI
import com.ccawsme.sinavhazirlik.data.DOGRU_YANLIS_SORULARI
import com.ccawsme.sinavhazirlik.data.Ilerleme
import com.ccawsme.sinavhazirlik.data.QUIZ_SORULARI
import com.ccawsme.sinavhazirlik.data.bilgiKartlari
import com.ccawsme.sinavhazirlik.data.dogruYanlisSorulari
import com.ccawsme.sinavhazirlik.data.konu
import com.ccawsme.sinavhazirlik.data.quizSorulari
import com.ccawsme.sinavhazirlik.ui.theme.Turuncu40
import com.ccawsme.sinavhazirlik.ui.theme.Yesil40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KonuDetayEkrani(
    konuId: String,
    ilerleme: Ilerleme,
    onGeri: () -> Unit,
    onKartlar: () -> Unit,
    onQuiz: () -> Unit,
    onDogruYanlis: () -> Unit
) {
    val tumu = konuId == "tumu"
    val konu = if (tumu) null else konu(konuId)
    val baslik = konu?.ad ?: "Tüm Konular"

    val kartSayisi = if (tumu) BILGI_KARTLARI.size else bilgiKartlari(konuId).size
    val quizSayisi = if (tumu) QUIZ_SORULARI.size else quizSorulari(konuId).size
    val dySayisi = if (tumu) DOGRU_YANLIS_SORULARI.size else dogruYanlisSorulari(konuId).size

    val ogrenilenKart = if (tumu) {
        ilerleme.ogrenilenKartIdleri.size
    } else {
        ilerleme.konuKartIlerlemesi(konuId).first
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(baslik, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onGeri) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            LinearProgressIndicator(
                progress = { if (kartSayisi == 0) 0f else ogrenilenKart.toFloat() / kartSayisi.toFloat() },
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(4.dp)),
                color = konu?.renk ?: MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "$ogrenilenKart/$kartSayisi kart öğrenildi",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))

            ModeSatiri("Bilgi Kartları", "$kartSayisi kart", Icons.Filled.School, MaterialTheme.colorScheme.primary, onKartlar)
            Spacer(modifier = Modifier.height(10.dp))
            ModeSatiri("Mini Test", "$quizSayisi soru, çoktan seçmeli", Icons.Filled.CheckCircle, Yesil40, onQuiz)
            Spacer(modifier = Modifier.height(10.dp))
            ModeSatiri("Doğru / Yanlış", "$dySayisi ifade", Icons.Filled.ThumbUp, Turuncu40, onDogruYanlis)
        }
    }
}

@Composable
private fun ModeSatiri(baslik: String, altBaslik: String, ikon: ImageVector, renk: Color, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(renk.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(ikon, contentDescription = null, tint = renk)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(baslik, fontWeight = FontWeight.Bold)
                Text(altBaslik, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
