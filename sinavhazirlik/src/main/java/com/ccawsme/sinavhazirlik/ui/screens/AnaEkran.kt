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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ccawsme.sinavhazirlik.data.Ilerleme
import com.ccawsme.sinavhazirlik.data.KONULAR
import com.ccawsme.sinavhazirlik.data.Konu
import com.ccawsme.sinavhazirlik.ui.theme.Turuncu40
import com.ccawsme.sinavhazirlik.ui.theme.Yesil40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnaEkran(
    ilerleme: Ilerleme,
    onKonuSec: (String) -> Unit,
    onTumKartlar: () -> Unit,
    onTumQuiz: () -> Unit,
    onTumDogruYanlis: () -> Unit
) {
    Scaffold(topBar = { TopAppBar(title = { Text("Adli Yargı Hazırlık", fontWeight = FontWeight.Bold) }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            StreakKarti(ilerleme.streakGunSayisi)
            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                IstatistikKarti("Quiz Doğruluk", "%${ilerleme.quizDogrulukYuzdesi()}", Modifier.weight(1f))
                IstatistikKarti("D/Y Doğruluk", "%${ilerleme.dySDogrulukYuzdesi()}", Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text("Hızlı Başla (tüm konular)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                ModButonu("Bilgi\nKartları", Icons.Filled.School, MaterialTheme.colorScheme.primary, onTumKartlar, Modifier.weight(1f))
                ModButonu("Mini\nTest", Icons.Filled.CheckCircle, Yesil40, onTumQuiz, Modifier.weight(1f))
                ModButonu("Doğru /\nYanlış", Icons.Filled.ThumbUp, Turuncu40, onTumDogruYanlis, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text("Konular", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                KONULAR.forEach { konu ->
                    KonuKarti(konu = konu, ilerleme = ilerleme, onClick = { onKonuSec(konu.id) })
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun StreakKarti(streak: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.FlashOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("$streak günlük seri", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(
                    if (streak > 0) "Harika gidiyorsun, devam et!" else "Bugün çalışarak seriye başla!",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun IstatistikKarti(etiket: String, deger: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(deger, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text(etiket, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun ModButonu(etiket: String, ikon: ImageVector, renk: Color, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(onClick = onClick, modifier = modifier) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(renk.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(ikon, contentDescription = null, tint = renk)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(etiket, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun KonuKarti(konu: Konu, ilerleme: Ilerleme, onClick: () -> Unit) {
    val (ogrenilen, toplam) = ilerleme.konuKartIlerlemesi(konu.id)
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(konu.renk.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(konu.ikon, contentDescription = null, tint = konu.renk)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(konu.ad, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { if (toplam == 0) 0f else ogrenilen.toFloat() / toplam.toFloat() },
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(4.dp)),
                    color = konu.renk
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text("$ogrenilen/$toplam kart öğrenildi", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
