package com.ccawsme.davaustasi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ccawsme.davaustasi.data.DAVA_TURLERI
import com.ccawsme.davaustasi.data.DavaTuru
import com.ccawsme.davaustasi.data.GameState
import com.ccawsme.davaustasi.data.PersonelRolu
import java.text.NumberFormat
import java.util.Locale

private val turkceSayiFormati: NumberFormat = NumberFormat.getIntegerInstance(Locale("tr", "TR"))

private fun formatSayi(deger: Long): String = turkceSayiFormati.format(deger)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnaEkran(
    durum: GameState,
    onPersonelSatinAl: (PersonelRolu) -> Unit,
    onDavaBaslat: (DavaTuru) -> Unit,
    onKariyerIlerlet: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Dava Ustası") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { DurumKarti(durum = durum, onKariyerIlerlet = onKariyerIlerlet) }

            item {
                Text("Kadro", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            items(PersonelRolu.entries.toList()) { rol ->
                PersonelKarti(rol = rol, durum = durum, onSatinAl = { onPersonelSatinAl(rol) })
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text("Davalar", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            items(DAVA_TURLERI) { davaTuru ->
                DavaKarti(
                    davaTuru = davaTuru,
                    kilitliMi = durum.enYuksekItibar < davaTuru.gerekliItibar,
                    onDavaAl = { onDavaBaslat(davaTuru) }
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun DurumKarti(durum: GameState, onKariyerIlerlet: () -> Unit) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(durum.mevcutUnvan().ad, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            SatirDegeri("Para", "${formatSayi(durum.para)} ₺")
            SatirDegeri("Gelir", "+${String.format(Locale("tr", "TR"), "%.1f", durum.saniyeGeliri())} ₺/sn")
            SatirDegeri("İtibar", formatSayi(durum.itibar))
            SatirDegeri("En Yüksek İtibar", formatSayi(durum.enYuksekItibar))
            SatirDegeri("Deneyim", formatSayi(durum.deneyim))

            val sonraki = durum.sonrakiUnvan()
            if (sonraki != null) {
                Spacer(modifier = Modifier.height(12.dp))
                val ilerleme = (durum.enYuksekItibar.toFloat() / sonraki.gerekliEnYuksekItibar.toFloat()).coerceIn(0f, 1f)
                Text(
                    "Sonraki unvan: ${sonraki.ad} (${formatSayi(durum.enYuksekItibar)}/${formatSayi(sonraki.gerekliEnYuksekItibar)} itibar)",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(progress = { ilerleme }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onKariyerIlerlet,
                    enabled = durum.kariyerIlerletebilirMi(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Kariyer İlerlet (para ve kadro sıfırlanır, kalıcı +%50 gelir kazanılır)")
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                Text("En üst unvana ulaştınız!", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun SatirDegeri(etiket: String, deger: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(etiket, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(deger, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun PersonelKarti(rol: PersonelRolu, durum: GameState, onSatinAl: () -> Unit) {
    val sayi = durum.personelSayisi(rol)
    val maliyet = rol.sonrakiMaliyet(sayi)
    val alinabilir = durum.para >= maliyet

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("${rol.etiket} (${sayi})", fontWeight = FontWeight.Bold)
                Text(rol.aciklama, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    "+${String.format(Locale("tr", "TR"), "%.1f", rol.saniyeGeliri)} ₺/sn",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = onSatinAl, enabled = alinabilir) {
                Text("${formatSayi(maliyet)} ₺")
            }
        }
    }
}

@Composable
private fun DavaKarti(davaTuru: DavaTuru, kilitliMi: Boolean, onDavaAl: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(davaTuru.ad, fontWeight = FontWeight.Bold)
                Text(
                    "Ödül: ${formatSayi(davaTuru.temelOdulPara)} ₺ · ${davaTuru.itibarOdulu} itibar · ${davaTuru.deneyimOdulu} deneyim",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (kilitliMi) {
                    Text(
                        "Gerekli itibar: ${formatSayi(davaTuru.gerekliItibar)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            if (kilitliMi) {
                Icon(Icons.Default.Lock, contentDescription = "Kilitli", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                OutlinedButton(onClick = onDavaAl) { Text("Dava Al") }
            }
        }
    }
}
