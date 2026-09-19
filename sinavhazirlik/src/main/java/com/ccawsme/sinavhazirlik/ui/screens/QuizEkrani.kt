package com.ccawsme.sinavhazirlik.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import com.ccawsme.sinavhazirlik.ui.QuizOturumu
import com.ccawsme.sinavhazirlik.ui.theme.Kirmizi40
import com.ccawsme.sinavhazirlik.ui.theme.Yesil40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizEkrani(
    oturum: QuizOturumu,
    onCevapSec: (Int) -> Unit,
    onSonraki: () -> Unit,
    onTekrarBaslat: () -> Unit,
    onGeri: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mini Test", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onGeri) { Icon(Icons.Filled.ArrowBack, contentDescription = "Geri") }
                }
            )
        }
    ) { padding ->
        if (oturum.bitti) {
            TamamlandiEkrani(
                ikon = Icons.Filled.CheckCircle,
                baslik = "Test Tamamlandı!",
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
            Spacer(modifier = Modifier.height(20.dp))

            if (oturum.cikmisMi && soru.yil != null) {
                Text(
                    "${soru.yil} Sınavı" + (soru.kaynak?.let { " · $it" } ?: ""),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            Text(soru.soru, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                soru.secenekler.forEachIndexed { i, secenek ->
                    val secildi = oturum.secilenIndex == i
                    val cevaplandi = oturum.secilenIndex != null
                    val dogruMu = i == soru.dogruIndex

                    val renk = when {
                        !cevaplandi -> null
                        dogruMu -> Yesil40
                        secildi -> Kirmizi40
                        else -> null
                    }

                    Card(
                        onClick = { if (!cevaplandi) onCevapSec(i) },
                        colors = CardDefaults.cardColors(
                            containerColor = renk?.copy(alpha = 0.12f) ?: MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Box(modifier = Modifier.padding(14.dp).fillMaxWidth()) {
                            Text(secenek, color = renk ?: MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }

            if (oturum.secilenIndex != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Card {
                    Text(
                        soru.aciklama,
                        modifier = Modifier.padding(14.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (oturum.secilenIndex != null) {
                Button(onClick = onSonraki, modifier = Modifier.fillMaxWidth()) {
                    Text(if (oturum.index + 1 >= oturum.sorular.size) "Sonucu Gör" else "Sonraki Soru")
                }
            }
        }
    }
}
