package com.ccawsme.davaustasi.ui.screens

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ccawsme.davaustasi.data.Aktivite
import com.ccawsme.davaustasi.data.Karakter
import com.ccawsme.davaustasi.data.Konum
import com.ccawsme.davaustasi.data.uygunAktiviteler
import java.text.NumberFormat
import java.util.Locale

private val sayiFormati = NumberFormat.getIntegerInstance(Locale("tr", "TR"))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnaEkran(karakter: Karakter, guncelKonum: Konum, onAktiviteSec: (Aktivite) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(karakter.ad, fontWeight = FontWeight.Bold)
                        Text(karakter.mevcutUnvan().ad, style = MaterialTheme.typography.bodySmall)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                "${karakter.gun}. Gün · ${karakter.zamanDilimi().etiket}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            KonumSahnesi(konum = guncelKonum, karakter = karakter, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

            StatCubugu("Enerji", karakter.enerji, Icons.Filled.FlashOn)
            StatCubugu("Mutluluk", karakter.mutluluk, Icons.Filled.Favorite)
            StatCubugu("Bilgi", karakter.bilgi, Icons.Filled.School)
            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                SayisalKart("Para", "${sayiFormati.format(karakter.para)} ₺", Icons.Filled.AttachMoney, Modifier.weight(1f))
                SayisalKart("İtibar", sayiFormati.format(karakter.itibar), Icons.Filled.Star, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))

            KariyerIlerlemesi(karakter)
            Spacer(modifier = Modifier.height(20.dp))

            Text("Bu zaman diliminde ne yapmak istersiniz?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))

            val aktiviteler = uygunAktiviteler(karakter)
            if (aktiviteler.isEmpty()) {
                Text(
                    "Şu an enerjiniz yetersiz. Dinlenmeyi deneyin.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    aktiviteler.forEach { aktivite ->
                        AktiviteKarti(aktivite = aktivite, onClick = { onAktiviteSec(aktivite) })
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun StatCubugu(etiket: String, deger: Int, ikon: ImageVector) {
    val animasyonluDeger by animateFloatAsState(targetValue = deger / 100f, label = "stat_$etiket")
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(ikon, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(etiket, style = MaterialTheme.typography.bodyMedium)
            }
            Text("$deger", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { animasyonluDeger },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        )
    }
}

@Composable
private fun SayisalKart(etiket: String, deger: String, ikon: ImageVector, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(ikon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(etiket, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(deger, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun KariyerIlerlemesi(karakter: Karakter) {
    val sonraki = karakter.sonrakiUnvan()
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.TrendingUp, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                if (sonraki != null) {
                    "Sonraki unvan: ${sonraki.ad} (${sayiFormati.format(karakter.kariyerPuani)}/${sayiFormati.format(sonraki.gerekliKariyerPuani)})"
                } else {
                    "En üst unvana ulaştınız!"
                },
                style = MaterialTheme.typography.bodySmall
            )
        }
        if (sonraki != null) {
            Spacer(modifier = Modifier.height(4.dp))
            val ilerleme = (karakter.kariyerPuani.toFloat() / sonraki.gerekliKariyerPuani.toFloat()).coerceIn(0f, 1f)
            LinearProgressIndicator(progress = { ilerleme }, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun AktiviteKarti(aktivite: Aktivite, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(aktivite.ikon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(aktivite.etiket, fontWeight = FontWeight.Bold)
                Text(
                    aktivite.aciklama,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
