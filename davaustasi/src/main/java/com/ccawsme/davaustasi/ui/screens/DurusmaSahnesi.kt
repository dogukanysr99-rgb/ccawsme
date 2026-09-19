package com.ccawsme.davaustasi.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ccawsme.davaustasi.data.AktifTaraf

@Composable
fun DurusmaSahnesi(aktifTaraf: AktifTaraf, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
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
    val kenarRengi = if (aktif) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
    val arkaPlanRengi = if (aktif) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    val icerikRengi = if (aktif) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = modifier
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
