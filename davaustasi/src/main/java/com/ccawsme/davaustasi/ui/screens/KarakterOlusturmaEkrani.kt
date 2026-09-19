package com.ccawsme.davaustasi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ccawsme.davaustasi.ui.theme.CILT_TONLARI
import com.ccawsme.davaustasi.ui.theme.KIYAFET_RENKLERI
import com.ccawsme.davaustasi.ui.theme.SAC_RENKLERI

@Composable
fun KarakterOlusturmaEkrani(onOlustur: (ad: String, cilt: Int, sac: Int, kiyafet: Int) -> Unit) {
    var ad by remember { mutableStateOf("") }
    var ciltIndex by remember { mutableStateOf(0) }
    var sacIndex by remember { mutableStateOf(0) }
    var kiyafetIndex by remember { mutableStateOf(0) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Kariyerine Başla", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))

            KarakterPortresi(cildIndex = ciltIndex, sacIndex = sacIndex, kiyafetIndex = kiyafetIndex, boyut = 140.dp)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = ad,
                onValueChange = { ad = it },
                label = { Text("Adınız") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text("Cilt Tonu", style = MaterialTheme.typography.labelLarge, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))
            RenkSecici(renkler = CILT_TONLARI, seciliIndex = ciltIndex, onSec = { ciltIndex = it })
            Spacer(modifier = Modifier.height(16.dp))

            Text("Saç Rengi", style = MaterialTheme.typography.labelLarge, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))
            RenkSecici(renkler = SAC_RENKLERI, seciliIndex = sacIndex, onSec = { sacIndex = it })
            Spacer(modifier = Modifier.height(16.dp))

            Text("Kıyafet Rengi", style = MaterialTheme.typography.labelLarge, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))
            RenkSecici(renkler = KIYAFET_RENKLERI, seciliIndex = kiyafetIndex, onSec = { kiyafetIndex = it })
            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = { onOlustur(ad.trim(), ciltIndex, sacIndex, kiyafetIndex) },
                enabled = ad.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Kariyere Başla")
            }
        }
    }
}

@Composable
private fun RenkSecici(renkler: List<Color>, seciliIndex: Int, onSec: (Int) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        renkler.forEachIndexed { index, renk ->
            val seciliMi = index == seciliIndex
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(renk)
                    .border(
                        width = if (seciliMi) 3.dp else 1.dp,
                        color = if (seciliMi) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
                    .clickable { onSec(index) }
            )
        }
    }
}
