package com.ccawsme.davaustasi.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ccawsme.davaustasi.data.DELIL_SECENEKLERI
import com.ccawsme.davaustasi.data.DavaAsamasi
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
    onDevamEt: () -> Unit,
    onTamam: () -> Unit,
    onKapat: () -> Unit
) {
    Dialog(onDismissRequest = onKapat, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                Text(surec.davaTuru.ad, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                DurusmaSahnesi(aktifTaraf = surec.aktifTaraf())
                Spacer(modifier = Modifier.height(16.dp))

                Crossfade(
                    targetState = surec.asama,
                    animationSpec = tween(durationMillis = 350),
                    label = "dava_asamasi"
                ) { asamaHedefi ->
                    when (asamaHedefi) {
                        DavaAsamasi.ACILIS -> AcilisAsamasi(surec = surec, onDevamEt = onDevamEt)
                        DavaAsamasi.KARSI_IDDIA -> KarsiIddiaAsamasi(surec = surec, onDevamEt = onDevamEt)
                        DavaAsamasi.STRATEJI -> if (surec.secilenStrateji == null) {
                            SecenekListesi(
                                baslik = "Savunma stratejinizi seçin",
                                secenekler = STRATEJI_SECENEKLERI,
                                onSecim = onStratejiSec
                            )
                        } else {
                            TepkiAsamasi(tepkiMetni = surec.stratejiTepkisi, devamEtiketi = "Delil Sunmaya Geç", onDevamEt = onDevamEt)
                        }
                        DavaAsamasi.DELIL -> if (surec.secilenDelil == null) {
                            SecenekListesi(
                                baslik = "Delil / tanık sunun",
                                secenekler = DELIL_SECENEKLERI,
                                onSecim = onDelilSec
                            )
                        } else {
                            TepkiAsamasi(tepkiMetni = surec.delilTepkisi, devamEtiketi = "Kararı Öğren", onDevamEt = onDevamEt)
                        }
                        DavaAsamasi.KARAR -> SonucGorunumu(surec = surec, onTamam = onTamam)
                    }
                }
            }
        }
    }
}

@Composable
private fun AcilisAsamasi(surec: DavaSureci, onDevamEt: () -> Unit) {
    Column {
        Text("Duruşma Başlıyor", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Hakim celseyi açtı. \"${surec.davaTuru.ad}\" dosyası görüşülmeye başlanıyor.")
        if (surec.olayMetni.isNotBlank()) {
            Spacer(modifier = Modifier.height(12.dp))
            Card(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Dosyanın Özeti", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(surec.olayMetni)
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onDevamEt, modifier = Modifier.fillMaxWidth()) {
            Text("Duruşmayı Başlat")
        }
    }
}

@Composable
private fun KarsiIddiaAsamasi(surec: DavaSureci, onDevamEt: () -> Unit) {
    Column {
        Text("Karşı Tarafın İddiası", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(surec.karsiIddiaMetni)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onDevamEt, modifier = Modifier.fillMaxWidth()) {
            Text("Savunmaya Geç")
        }
    }
}

@Composable
private fun TepkiAsamasi(tepkiMetni: String, devamEtiketi: String, onDevamEt: () -> Unit) {
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        ) {
            Text(
                text = tepkiMetni,
                modifier = Modifier.padding(16.dp),
                fontStyle = FontStyle.Italic
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onDevamEt, modifier = Modifier.fillMaxWidth()) {
            Text(devamEtiketi)
        }
    }
}

@Composable
private fun SecenekListesi(baslik: String, secenekler: List<Secenek>, onSecim: (Secenek) -> Unit) {
    Column {
        Text(baslik, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            secenekler.forEach { secenek ->
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
}

@Composable
private fun SonucGorunumu(surec: DavaSureci, onTamam: () -> Unit) {
    val sonuc = surec.sonuc ?: return
    val format = NumberFormat.getIntegerInstance(Locale("tr", "TR"))
    val basariYuzdesi = (sonuc.basariSansi * 100).toInt()

    Column {
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
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onTamam, modifier = Modifier.fillMaxWidth()) {
            Text("Devam Et")
        }
    }
}
