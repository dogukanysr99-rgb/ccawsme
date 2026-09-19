package com.ccawsme.davaustasi.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ccawsme.davaustasi.data.AktifTaraf

@Composable
fun DurusmaSahnesi(aktifTaraf: AktifTaraf, modifier: Modifier = Modifier) {
    val sahneArkaPlani = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.surface
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(sahneArkaPlani)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        KurulusKutusu(
            etiket = "HAKİM",
            ikon = Icons.Filled.Gavel,
            aktif = aktifTaraf == AktifTaraf.HAKIM,
            modifier = Modifier.fillMaxWidth(0.6f)
        )
        KurulusKutusu(
            etiket = "TANIK KÜRSÜSÜ",
            ikon = Icons.Filled.RecordVoiceOver,
            aktif = aktifTaraf == AktifTaraf.TANIK,
            modifier = Modifier.fillMaxWidth(0.55f)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            KurulusKutusu(
                etiket = "SİZ (SAVUNMA)",
                ikon = Icons.Filled.Person,
                aktif = aktifTaraf == AktifTaraf.SAVUNMA,
                modifier = Modifier.weight(1f)
            )
            KurulusKutusu(
                etiket = "KARŞI TARAF",
                ikon = Icons.Filled.Group,
                aktif = aktifTaraf == AktifTaraf.KARSI_TARAF,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun KurulusKutusu(etiket: String, ikon: ImageVector, aktif: Boolean, modifier: Modifier = Modifier) {
    val kenarRengi by animateColorAsState(
        targetValue = if (aktif) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
        label = "kenar_rengi"
    )
    val arkaPlanRengi by animateColorAsState(
        targetValue = if (aktif) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        label = "arka_plan_rengi"
    )
    val icerikRengi by animateColorAsState(
        targetValue = if (aktif) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "icerik_rengi"
    )

    val nabizGecisi = rememberInfiniteTransition(label = "nabiz")
    val olcek by nabizGecisi.animateFloat(
        initialValue = 1f,
        targetValue = if (aktif) 1.05f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 650, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "nabiz_olcek"
    )

    Column(
        modifier = modifier
            .scale(olcek)
            .border(width = if (aktif) 2.dp else 1.dp, color = kenarRengi, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(arkaPlanRengi)
            .padding(vertical = 10.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = ikon, contentDescription = etiket, tint = icerikRengi)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = etiket,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (aktif) FontWeight.Bold else FontWeight.Normal,
            color = icerikRengi,
            textAlign = TextAlign.Center
        )
    }
}
