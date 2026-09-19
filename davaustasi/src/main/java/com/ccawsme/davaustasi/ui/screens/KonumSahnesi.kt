package com.ccawsme.davaustasi.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ccawsme.davaustasi.data.Karakter
import com.ccawsme.davaustasi.data.Konum

private data class KonumGorunumu(val ustRenk: Color, val altRenk: Color, val ikon: ImageVector)

private fun konumGorunumu(konum: Konum): KonumGorunumu = when (konum) {
    Konum.EV -> KonumGorunumu(Color(0xFF8D6E63), Color(0xFFD7CCC8), Icons.Filled.Home)
    Konum.OFIS -> KonumGorunumu(Color(0xFF37474F), Color(0xFFB0BEC5), Icons.Filled.Work)
    Konum.MAHKEME -> KonumGorunumu(Color(0xFF1B5E20), Color(0xFFA5D6A7), Icons.Filled.Gavel)
    Konum.DISARISI -> KonumGorunumu(Color(0xFF01579B), Color(0xFF81D4FA), Icons.Filled.Group)
}

@Composable
fun KonumSahnesi(konum: Konum, karakter: Karakter, modifier: Modifier = Modifier) {
    val gorunum = konumGorunumu(konum)
    val ustRenk by animateColorAsState(targetValue = gorunum.ustRenk, label = "konum_ust_rengi")
    val altRenk by animateColorAsState(targetValue = gorunum.altRenk, label = "konum_alt_rengi")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.verticalGradient(colors = listOf(ustRenk, altRenk))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        KarakterPortresi(
            cildIndex = karakter.cildTonuIndex,
            sacIndex = karakter.sacRengiIndex,
            kiyafetIndex = karakter.kiyafetRengiIndex,
            boyut = 92.dp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = gorunum.ikon, contentDescription = konum.etiket, tint = Color.White)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = konum.etiket,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
