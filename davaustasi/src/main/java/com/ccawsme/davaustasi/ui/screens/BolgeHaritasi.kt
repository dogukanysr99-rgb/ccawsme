package com.ccawsme.davaustasi.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ccawsme.davaustasi.data.BOLGELER
import com.ccawsme.davaustasi.data.Bolge
import com.ccawsme.davaustasi.data.Imparatorluk
import java.text.NumberFormat
import java.util.Locale

private val fmt = NumberFormat.getIntegerInstance(Locale("tr", "TR"))

@Composable
fun BolgeHaritasi(durum: Imparatorluk, onFethet: (Bolge) -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(340.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF263238), Color(0xFF37474F))
                )
            )
    ) {
        BOLGELER.forEach { b ->
            val fethedildi = b.id in durum.fethedilenBolgeIdleri
            BolgeCipi(
                bolge = b,
                fethedildi = fethedildi,
                durum = durum,
                onFethet = onFethet,
                modifier = Modifier
                    .offset(x = maxWidth * b.konumX - 52.dp, y = maxHeight * b.konumY - 24.dp)
                    .width(104.dp)
            )
        }
    }
}

@Composable
private fun BolgeCipi(
    bolge: Bolge,
    fethedildi: Boolean,
    durum: Imparatorluk,
    onFethet: (Bolge) -> Unit,
    modifier: Modifier = Modifier
) {
    val acilabilirMi = durum.itibarYuzdesi >= bolge.gerekliItibar
    Card(
        modifier = modifier.wrapContentWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (fethedildi) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        ),
        border = if (fethedildi) BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else null
    ) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(bolge.ad, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, maxLines = 1)
            Text(
                fmt.format(bolge.muvekkilPotansiyeli) + " müvekkil",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (fethedildi) {
                Text("Sizin", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            } else if (!acilabilirMi) {
                Text("İtibar %${bolge.gerekliItibar} gerekir", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error)
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
