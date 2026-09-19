package com.ccawsme.davaustasi.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ccawsme.davaustasi.data.BOLGELER
import com.ccawsme.davaustasi.data.Bolge
import com.ccawsme.davaustasi.data.Imparatorluk
import com.ccawsme.davaustasi.data.avatarUrl
import java.text.NumberFormat
import java.util.Locale

private val fmt = NumberFormat.getIntegerInstance(Locale("tr", "TR"))
private val SAHIP_RENGI = Color(0xFF2E7D32)
private val ACILABILIR_RENGI = Color(0xFFD81B60)

@Composable
fun BolgeHaritasi(durum: Imparatorluk, onFethet: (Bolge) -> Unit) {
    val hedefBolgeId = remember(durum.para, durum.itibarYuzdesi, durum.fethedilenBolgeIdleri) {
        BOLGELER
            .filter { it.id !in durum.fethedilenBolgeIdleri && durum.itibarYuzdesi >= it.gerekliItibar }
            .minByOrNull { it.maliyet }
            ?.id
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(340.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(colors = listOf(Color(0xFF263238), Color(0xFF37474F)))
            )
    ) {
        BOLGELER.forEach { b ->
            val fethedildi = b.id in durum.fethedilenBolgeIdleri
            BolgeCipi(
                bolge = b,
                fethedildi = fethedildi,
                hedefMi = b.id == hedefBolgeId,
                durum = durum,
                onFethet = onFethet,
                modifier = Modifier
                    .offset(x = maxWidth * b.konumX - 52.dp, y = maxHeight * b.konumY - 34.dp)
                    .width(104.dp)
            )
        }
    }
}

@Composable
private fun BolgeCipi(
    bolge: Bolge,
    fethedildi: Boolean,
    hedefMi: Boolean,
    durum: Imparatorluk,
    onFethet: (Bolge) -> Unit,
    modifier: Modifier = Modifier
) {
    val acilabilirMi = durum.itibarYuzdesi >= bolge.gerekliItibar

    val nabiz = rememberInfiniteTransition(label = "bolge_nabiz")
    val olcek by nabiz.animateFloat(
        initialValue = 1f,
        targetValue = if (hedefMi) 1.06f else 1f,
        animationSpec = infiniteRepeatable(tween(700, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bolge_olcek"
    )

    Column(
        modifier = modifier.wrapContentWidth().scale(olcek),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = avatarUrl(bolge.id),
            contentDescription = bolge.ad,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .border(
                    2.dp,
                    if (fethedildi) SAHIP_RENGI else if (acilabilirMi) ACILABILIR_RENGI else Color.Gray,
                    CircleShape
                )
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = when {
                    fethedildi -> SAHIP_RENGI
                    acilabilirMi -> ACILABILIR_RENGI
                    else -> MaterialTheme.colorScheme.surface
                }
            ),
            border = if (hedefMi) BorderStroke(2.dp, Color.Yellow) else null
        ) {
            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                val metinRengi = if (fethedildi || acilabilirMi) Color.White else MaterialTheme.colorScheme.onSurface
                Text(bolge.ad, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, maxLines = 1, color = metinRengi)
                Text(
                    fmt.format(bolge.muvekkilPotansiyeli),
                    style = MaterialTheme.typography.labelSmall,
                    color = metinRengi.copy(alpha = 0.85f)
                )
                if (fethedildi) {
                    Text("Sizin", style = MaterialTheme.typography.labelSmall, color = Color.White, fontWeight = FontWeight.Bold)
                } else if (!acilabilirMi) {
                    Text("İtibar %${bolge.gerekliItibar}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error)
                } else {
                    Button(
                        onClick = { onFethet(bolge) },
                        enabled = durum.para >= bolge.maliyet,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("${fmt.format(bolge.maliyet)} ₺", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}
