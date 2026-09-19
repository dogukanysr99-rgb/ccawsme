package com.ccawsme.davaustasi.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import com.ccawsme.davaustasi.ui.theme.CILT_TONLARI
import com.ccawsme.davaustasi.ui.theme.KIYAFET_RENKLERI
import com.ccawsme.davaustasi.ui.theme.SAC_RENKLERI

@Composable
fun KarakterPortresi(
    cildIndex: Int,
    sacIndex: Int,
    kiyafetIndex: Int,
    boyut: Dp,
    modifier: Modifier = Modifier
) {
    val ciltRenk = CILT_TONLARI[cildIndex.coerceIn(CILT_TONLARI.indices)]
    val sacRenk = SAC_RENKLERI[sacIndex.coerceIn(SAC_RENKLERI.indices)]
    val kiyafetRenk = KIYAFET_RENKLERI[kiyafetIndex.coerceIn(KIYAFET_RENKLERI.indices)]

    Canvas(modifier = modifier.size(boyut)) {
        val genislik = size.width
        val yukseklik = size.height
        val merkezX = genislik / 2f

        val govdeUst = yukseklik * 0.62f
        drawRoundRect(
            color = kiyafetRenk,
            topLeft = Offset(genislik * 0.12f, govdeUst),
            size = Size(genislik * 0.76f, yukseklik - govdeUst),
            cornerRadius = CornerRadius(genislik * 0.3f, genislik * 0.3f)
        )

        val sacMerkezi = Offset(merkezX, yukseklik * 0.34f)
        drawCircle(color = sacRenk, radius = genislik * 0.30f, center = sacMerkezi)

        val yuzMerkezi = Offset(merkezX, yukseklik * 0.42f)
        drawCircle(color = ciltRenk, radius = genislik * 0.25f, center = yuzMerkezi)
    }
}
