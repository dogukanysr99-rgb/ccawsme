package com.ccawsme.davaustasi.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
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
private val CIZGI_RENGI = Color(0xFF2B2B2B)

@Composable
fun KadroAgaci(durum: Imparatorluk, onIseAl: (KadroUyesi) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        PortreRozet(
            avatarUrl = avatarUrl(durum.avatarSeed),
            ad = durum.ad,
            boyut = 76.dp,
            yas = null,
            gelirSaniye = durum.toplamGelirSaniye(),
            kilitli = false
        )

        Canvas(modifier = Modifier.fillMaxWidth().height(22.dp)) {
            val genislik = size.width
            val govdeY = size.height * 0.5f
            val merkezler = listOf(genislik / 6f, genislik / 2f, genislik * 5f / 6f)
            drawLine(CIZGI_RENGI, Offset(genislik / 2f, 0f), Offset(genislik / 2f, govdeY), strokeWidth = 4f)
            drawLine(CIZGI_RENGI, Offset(merkezler.first(), govdeY), Offset(merkezler.last(), govdeY), strokeWidth = 4f)
            merkezler.forEach { cx -> drawLine(CIZGI_RENGI, Offset(cx, govdeY), Offset(cx, size.height), strokeWidth = 4f) }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
            listOf("ortak_1", "ortak_2", "ortak_3").forEach { ortakId ->
                val ortak = kadroUyesi(ortakId) ?: return@forEach
                val stajyer = KADRO_SABLONU.firstOrNull { it.ebeveynId == ortakId }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    KadroDalKarti(uye = ortak, durum = durum, onIseAl = onIseAl)
                    if (stajyer != null) {
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .height(16.dp)
                                .background(CIZGI_RENGI)
                        )
                        KadroDalKarti(uye = stajyer, durum = durum, onIseAl = onIseAl)
                    }
                }
            }
        }
    }
}

@Composable
private fun KadroDalKarti(uye: KadroUyesi, durum: Imparatorluk, onIseAl: (KadroUyesi) -> Unit) {
    val hireli = uye.id in durum.hireliKadroIdleri
    val kilitli = durum.kadroKilitliMi(uye)

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 4.dp)) {
        PortreRozet(
            avatarUrl = avatarUrl(uye.avatarSeed),
            ad = if (kilitli) "???" else uye.ad,
            boyut = 56.dp,
            yas = if (kilitli) null else uye.yas,
            gelirSaniye = if (hireli) uye.gelirSaniye else null,
            kilitli = kilitli
        )
        if (!hireli) {
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = { onIseAl(uye) },
                enabled = !kilitli && durum.para >= uye.maliyet,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("${fmt.format(uye.maliyet)} ₺", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
private fun PortreRozet(
    avatarUrl: String,
    ad: String,
    boyut: Dp,
    yas: Int?,
    gelirSaniye: Long?,
    kilitli: Boolean
) {
    Box(contentAlignment = Alignment.Center) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = ad,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(boyut)
                .clip(CircleShape)
        )
        if (kilitli) {
            Box(
                modifier = Modifier
                    .size(boyut)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Lock, contentDescription = "Kilitli", tint = Color.White, modifier = Modifier.size(boyut / 2.5f))
            }
        }
        if (yas != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(yas.toString(), color = Color.White, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            }
        }
    }
    Box(
        modifier = Modifier
            .padding(top = 2.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(ad, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, maxLines = 1)
    }
    if (gelirSaniye != null) {
        Box(
            modifier = Modifier
                .padding(top = 2.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(
                "+$gelirSaniye/sn",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
