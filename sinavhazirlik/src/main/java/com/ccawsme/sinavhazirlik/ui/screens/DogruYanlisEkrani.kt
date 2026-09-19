package com.ccawsme.sinavhazirlik.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ccawsme.sinavhazirlik.ui.DysOturumu
import com.ccawsme.sinavhazirlik.ui.theme.Kirmizi40
import com.ccawsme.sinavhazirlik.ui.theme.Yesil40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogruYanlisEkrani(
    oturum: DysOturumu,
    onCevapSec: (Boolean) -> Unit,
    onSonraki: () -> Unit,
    onTekrarBaslat: () -> Unit,
    onGeri: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Doğru / Yanlış", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onGeri) { Icon(Icons.Filled.ArrowBack, contentDescription = "Geri") }
                }
            )
        }
    ) { padding ->
        if (oturum.bitti) {
            TamamlandiEkrani(
                ikon = Icons.Filled.ThumbUp,
                baslik = "Tamamlandı!",
                ekBilgi = "${oturum.dogruSayisi} / ${oturum.sorular.size} doğru",
                onTekrar = onTekrarBaslat,
                onGeri = onGeri,
                modifier = Modifier.padding(padding)
            )
            return@Scaffold
        }

        val soru = oturum.sorular[oturum.index]

        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(20.dp)) {
            LinearProgressIndicator(
                progress = { oturum.index.toFloat() / oturum.sorular.size.toFloat() },
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "${oturum.index + 1} / ${oturum.sorular.size}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(28.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    soru.ifade,
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            if (oturum.cevaplandi) {
                Spacer(modifier = Modifier.height(16.dp))
                val dogruMu = oturum.sonCevapDogruMu == true
                Text(
                    if (dogruMu) "Doğru!" else "Yanlış",
                    color = if (dogruMu) Yesil40 else Kirmizi40,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Card {
                    Text(soru.aciklama, modifier = Modifier.padding(14.dp), style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (!oturum.cevaplandi) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { onCevapSec(false) },
                        colors = ButtonDefaults.buttonColors(containerColor = Kirmizi40),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = null)
                        Text(" Yanlış")
                    }
                    Button(
                        onClick = { onCevapSec(true) },
                        colors = ButtonDefaults.buttonColors(containerColor = Yesil40),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Filled.ThumbUp, contentDescription = null)
                        Text(" Doğru")
                    }
                }
            } else {
                Button(onClick = onSonraki, modifier = Modifier.fillMaxWidth()) {
                    Text(if (oturum.index + 1 >= oturum.sorular.size) "Sonucu Gör" else "Sonraki")
                }
            }
        }
    }
}
