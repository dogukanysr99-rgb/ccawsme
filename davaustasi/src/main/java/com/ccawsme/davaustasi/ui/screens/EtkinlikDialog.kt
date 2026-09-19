package com.ccawsme.davaustasi.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
            Column(
                modifier = Modifier.fillMaxWidth().padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = avatarUrl(etkinlik.avatarSeed),
                    contentDescription = etkinlik.karakterAdi,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(72.dp).clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(etkinlik.karakterAdi, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Text(etkinlik.mesaj, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(20.dp))

                SecenekButonu(etkinlik.secenekA, onSecim, birincil = true)
                Spacer(modifier = Modifier.height(8.dp))
                SecenekButonu(etkinlik.secenekB, onSecim, birincil = false)
            }
        }
    }
}

@Composable
private fun SecenekButonu(secenek: EtkinlikSecenegi, onSecim: (EtkinlikSecenegi) -> Unit, birincil: Boolean) {
    val etkiMetni = buildString {
        if (secenek.paraEtkisi != 0L) append((if (secenek.paraEtkisi > 0) "+" else "") + secenek.paraEtkisi + " ₺  ")
        if (secenek.itibarEtkisi != 0) append((if (secenek.itibarEtkisi > 0) "+" else "") + secenek.itibarEtkisi + " İtibar")
    }.trim()

    val icerik: @Composable () -> Unit = {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(secenek.etiket)
            if (etkiMetni.isNotEmpty()) {
                Text(etkiMetni, style = MaterialTheme.typography.labelSmall)
            }
        }
    }

    if (birincil) {
        Button(onClick = { onSecim(secenek) }, modifier = Modifier.fillMaxWidth()) { icerik() }
    } else {
        OutlinedButton(onClick = { onSecim(secenek) }, modifier = Modifier.fillMaxWidth()) { icerik() }
    }
}
