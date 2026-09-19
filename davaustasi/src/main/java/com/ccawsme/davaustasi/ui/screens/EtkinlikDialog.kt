package com.ccawsme.davaustasi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.ccawsme.davaustasi.data.Etkinlik
import com.ccawsme.davaustasi.data.EtkinlikSecenegi
import com.ccawsme.davaustasi.data.avatarUrl

@Composable
fun EtkinlikDialog(etkinlik: Etkinlik, onSecim: (EtkinlikSecenegi) -> Unit) {
    Dialog(onDismissRequest = {}) {
        Surface(shape = RoundedCornerShape(20.dp)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box {
                    AsyncImage(
                        model = avatarUrl(etkinlik.avatarSeed),
                        contentDescription = etkinlik.karakterAdi,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.4f)
                    )
                }
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        etkinlik.karakterAdi,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        etkinlik.mesaj,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(18.dp))

                    SecenekSatiri(etkinlik.secenekA, onSecim)
                    Spacer(modifier = Modifier.height(8.dp))
                    SecenekSatiri(etkinlik.secenekB, onSecim)
                }
            }
        }
    }
}

@Composable
private fun SecenekSatiri(secenek: EtkinlikSecenegi, onSecim: (EtkinlikSecenegi) -> Unit) {
    Card(onClick = { onSecim(secenek) }, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(10.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(secenek.etiket, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
            Column(horizontalAlignment = Alignment.End) {
                if (secenek.paraEtkisi != 0L) {
                    Text(
                        (if (secenek.paraEtkisi > 0) "+" else "") + secenek.paraEtkisi + " ₺",
                        color = if (secenek.paraEtkisi > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (secenek.itibarEtkisi != 0) {
                    Text(
                        (if (secenek.itibarEtkisi > 0) "+" else "") + secenek.itibarEtkisi + " İtibar",
                        color = if (secenek.itibarEtkisi > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
