package com.ccawsme.sinavhazirlik.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ccawsme.sinavhazirlik.data.bilgiKartlari
import com.ccawsme.sinavhazirlik.ui.theme.Kirmizi40
import com.ccawsme.sinavhazirlik.ui.theme.Yesil40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BilgiKartlariEkrani(
    konuId: String,
    onOgrenildi: (String) -> Unit,
    onTekrarEt: (String) -> Unit,
    onGeri: () -> Unit
) {
    val kartlar = remember { bilgiKartlari(if (konuId == "tumu") null else konuId) }
    var index by remember { mutableIntStateOf(0) }
    var arkaYuzdeMi by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bilgi Kartları", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onGeri) { Icon(Icons.Filled.ArrowBack, contentDescription = "Geri") }
                }
            )
        }
    ) { padding ->
        if (kartlar.isEmpty()) {
            Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Bu konuda henüz kart yok.")
            }
            return@Scaffold
        }

        if (index >= kartlar.size) {
            TamamlandiEkrani(
                ikon = Icons.Filled.School,
                baslik = "Tüm kartları bitirdiniz!",
                onTekrar = { index = 0; arkaYuzdeMi = false },
                onGeri = onGeri,
                modifier = Modifier.padding(padding)
            )
            return@Scaffold
        }

        val kart = kartlar[index]

        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(20.dp)) {
            LinearProgressIndicator(
                progress = { (index).toFloat() / kartlar.size.toFloat() },
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text("${index + 1} / ${kartlar.size}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(20.dp))

            Card(
                onClick = { arkaYuzdeMi = !arkaYuzdeMi },
                modifier = Modifier.fillMaxWidth().aspectRatio(1.15f)
            ) {
                Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                    Crossfade(targetState = arkaYuzdeMi, label = "kart_yuz") { arkaMi ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                if (arkaMi) "CEVAP" else "SORU",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                if (arkaMi) kart.arkaYuz else kart.onYuz,
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Cevabı görmek için karta dokunun",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = {
                        onTekrarEt(kart.id)
                        index += 1
                        arkaYuzdeMi = false
                    },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Kirmizi40),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Tekrar Et")
                }
                Button(
                    onClick = {
                        onOgrenildi(kart.id)
                        index += 1
                        arkaYuzdeMi = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Yesil40),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Biliyorum")
                }
            }
        }
    }
}

@Composable
fun TamamlandiEkrani(
    ikon: androidx.compose.ui.graphics.vector.ImageVector,
    baslik: String,
    onTekrar: () -> Unit,
    onGeri: () -> Unit,
    modifier: Modifier = Modifier,
    ekBilgi: String? = null
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(50))
                .padding(20.dp)
        ) {
            Icon(ikon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(baslik, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        if (ekBilgi != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(ekBilgi, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(28.dp))
        Button(onClick = onTekrar, modifier = Modifier.fillMaxWidth()) { Text("Tekrar Başla") }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(onClick = onGeri, modifier = Modifier.fillMaxWidth()) { Text("Panele Dön") }
    }
}
