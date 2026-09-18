package com.ccawsme.davaustasi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ccawsme.davaustasi.data.DELIL_SECENEKLERI
import com.ccawsme.davaustasi.data.DavaSureci
import com.ccawsme.davaustasi.data.STRATEJI_SECENEKLERI
import com.ccawsme.davaustasi.data.Secenek
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DavaEkrani(
    surec: DavaSureci,
    onStratejiSec: (Secenek) -> Unit,
    onDelilSec: (Secenek) -> Unit,
    onTamam: () -> Unit,
    onKapat: () -> Unit
) {
    Dialog(onDismissRequest = onKapat, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                Text(surec.davaTuru.ad, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                when {
                    surec.sonuc != null -> SonucGorunumu(surec = surec, onTamam = onTamam)
                    surec.secilenStrateji == null -> SecenekListesi(
                        baslik = "1. Strateji seçin",
                        secenekler = STRATEJI_SECENEKLERI,
                        onSecim = onStratejiSec
                    )
                    else -> SecenekListesi(
                        baslik = "2. Delil sunun",
                        secenekler = DELIL_SECENEKLERI,
                        onSecim = onDelilSec
                    )
                }
            }
        }
    }
}

@Composable
private fun SecenekListesi(baslik: String, secenekler: List<Secenek>, onSecim: (Secenek) -> Unit) {
    Text(baslik, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(12.dp))
    LazyColumn(
        contentPadding = PaddingValues(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(secenekler) { secenek ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(secenek.etiket, fontWeight = FontWeight.Bold)
                    Text(secenek.aciklama, style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onSecim(secenek) }, modifier = Modifier.fillMaxWidth()) {
                        Text("Seç")
                    }
                }
            }
        }
    }
}

@Composable
private fun SonucGorunumu(surec: DavaSureci, onTamam: () -> Unit) {
    val sonuc = surec.sonuc ?: return
    val format = NumberFormat.getIntegerInstance(Locale("tr", "TR"))
    val basariYuzdesi = (sonuc.basariSansi * 100).toInt()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = if (sonuc.kazandi) "Davayı kazandınız!" else "Davayı kaybettiniz",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = if (sonuc.kazandi) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Başarı şansınız: %$basariYuzdesi")
        Spacer(modifier = Modifier.height(16.dp))
        if (sonuc.kazandi) {
            Text("+${format.format(sonuc.kazanilanPara)} ₺")
            Text("+${sonuc.kazanilanItibar} itibar")
        }
        Text("+${sonuc.kazanilanDeneyim} deneyim")
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onTamam, modifier = Modifier.fillMaxWidth()) {
            Text("Devam Et")
        }
    }
}
