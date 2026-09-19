package com.ccawsme.davaustasi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ccawsme.davaustasi.data.Imparatorluk
import com.ccawsme.davaustasi.data.KADRO_SABLONU
import com.ccawsme.davaustasi.data.KadroUyesi
import com.ccawsme.davaustasi.data.avatarUrl
import com.ccawsme.davaustasi.data.kadroUyesi
import java.text.NumberFormat
import java.util.Locale

private val fmt = NumberFormat.getIntegerInstance(Locale("tr", "TR"))

@Composable
fun KadroAgaci(durum: Imparatorluk, onIseAl: (KadroUyesi) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        LiderKarti(durum)
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("ortak_1", "ortak_2", "ortak_3").forEach { ortakId ->
                val ortak = kadroUyesi(ortakId) ?: return@forEach
                val stajyer = KADRO_SABLONU.firstOrNull { it.ebeveynId == ortakId }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    KadroKarti(uye = ortak, durum = durum, onIseAl = onIseAl)
                    if (stajyer != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        KadroKarti(uye = stajyer, durum = durum, onIseAl = onIseAl)
                    }
                }
            }
        }
    }
}

@Composable
private fun LiderKarti(durum: Imparatorluk) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = avatarUrl(durum.avatarSeed),
                contentDescription = durum.ad,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(56.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(durum.ad, fontWeight = FontWeight.Bold)
                Text("Lider", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text("+${durum.toplamGelirSaniye()}/sn", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
private fun KadroKarti(uye: KadroUyesi, durum: Imparatorluk, onIseAl: (KadroUyesi) -> Unit) {
    val hireli = uye.id in durum.hireliKadroIdleri
    val kilitli = durum.kadroKilitliMi(uye)

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center) {
                AsyncImage(
                    model = avatarUrl(uye.avatarSeed),
                    contentDescription = uye.ad,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(48.dp).clip(CircleShape)
                )
                if (kilitli) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.45f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Lock, contentDescription = "Kilitli", tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                if (kilitli) "???" else uye.ad,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            if (!kilitli) {
                Text("${uye.yas} yaş", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(4.dp))
            if (hireli) {
                Text("+${uye.gelirSaniye}/sn", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            } else {
                Button(
                    onClick = { onIseAl(uye) },
                    enabled = !kilitli && durum.para >= uye.maliyet,
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text("${fmt.format(uye.maliyet)} ₺", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}
