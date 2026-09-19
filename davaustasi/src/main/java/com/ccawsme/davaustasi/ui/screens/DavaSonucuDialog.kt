package com.ccawsme.davaustasi.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ccawsme.davaustasi.data.DavaSonucu
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DavaSonucuDialog(sonuc: DavaSonucu, onKapat: () -> Unit) {
    val format = NumberFormat.getIntegerInstance(Locale("tr", "TR"))
    val basariYuzdesi = (sonuc.basariSansi * 100).toInt()
    val vurguRengi = if (sonuc.kazandi) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

    Dialog(onDismissRequest = onKapat) {
        Surface(shape = RoundedCornerShape(20.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.Gavel,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = vurguRengi
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (sonuc.kazandi) "Davayı Kazandınız!" else "Davayı Kaybettiniz",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = vurguRengi
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Başarı şansınız: %$basariYuzdesi")
                Spacer(modifier = Modifier.height(16.dp))
                if (sonuc.kazandi) {
                    Text("+${format.format(sonuc.kazanilanPara)} ₺")
                }
                Text("İtibar: ${if (sonuc.itibarDegisimi >= 0) "+" else ""}${sonuc.itibarDegisimi}")
                Text("+${sonuc.kazanilanKariyerPuani} kariyer puanı")
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = onKapat, modifier = Modifier.fillMaxWidth()) {
                    Text("Devam Et")
                }
            }
        }
    }
}
