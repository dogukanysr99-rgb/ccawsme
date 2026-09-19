package com.ccawsme.davaustasi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ccawsme.davaustasi.data.Bolge
import com.ccawsme.davaustasi.data.Imparatorluk
import com.ccawsme.davaustasi.data.KadroUyesi
import java.text.NumberFormat
import java.util.Locale

private val fmt = NumberFormat.getIntegerInstance(Locale("tr", "TR"))

@Composable
fun AnaEkran(
    durum: Imparatorluk,
    onIseAl: (KadroUyesi) -> Unit,
    onFethet: (Bolge) -> Unit,
    onGunlukOdul: () -> Unit,
    onSonrakiGun: () -> Unit
) {
    var sekme by remember { mutableIntStateOf(0) }

    Scaffold(topBar = { HudCubugu(durum) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("${durum.gun}. Gün", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                if (durum.gunlukOdulAlinabilirMi()) {
                    Button(onClick = onGunlukOdul) {
                        Text("Günlük Ödül +${fmt.format(durum.gunlukOdulMiktari())} ₺")
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = onSonrakiGun, modifier = Modifier.fillMaxWidth()) {
                Text("Sonraki Gün")
            }
            Spacer(modifier = Modifier.height(16.dp))

            TabRow(selectedTabIndex = sekme) {
                Tab(selected = sekme == 0, onClick = { sekme = 0 }, text = { Text("Büro Kadrosu") })
                Tab(selected = sekme == 1, onClick = { sekme = 1 }, text = { Text("Şehir Haritası") })
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (sekme == 0) {
                KadroAgaci(durum = durum, onIseAl = onIseAl)
            } else {
                BolgeHaritasi(durum = durum, onFethet = onFethet)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun HudCubugu(durum: Imparatorluk) {
    Surface(tonalElevation = 2.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HudOgesi(Icons.Filled.AttachMoney, "${fmt.format(durum.para)} ₺", "+${durum.toplamGelirSaniye()}/sn")
            HudOgesi(Icons.Filled.Star, "%${durum.itibarYuzdesi}", "İtibar")
            HudOgesi(Icons.Filled.Group, fmt.format(durum.toplamMuvekkilSayisi()), "Müvekkil")
        }
    }
}

@Composable
private fun HudOgesi(ikon: ImageVector, deger: String, altYazi: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(ikon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(4.dp))
            Text(deger, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
        }
        Text(altYazi, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
