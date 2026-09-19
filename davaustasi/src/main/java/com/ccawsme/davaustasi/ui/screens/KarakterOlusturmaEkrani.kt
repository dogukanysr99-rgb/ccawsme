package com.ccawsme.davaustasi.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ccawsme.davaustasi.data.avatarUrl
import kotlin.random.Random

private fun yeniSeed(): String = "buro-" + Random.nextInt(100000, 999999)

@Composable
fun KarakterOlusturmaEkrani(onOlustur: (ad: String, avatarSeed: String) -> Unit) {
    var ad by remember { mutableStateOf("") }
    var seed by remember { mutableStateOf(yeniSeed()) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Bürona Başla", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))

            AsyncImage(
                model = avatarUrl(seed),
                contentDescription = "Avatar önizleme",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(onClick = { seed = yeniSeed() }) {
                Text("Yeniden Oluştur")
            }
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = ad,
                onValueChange = { ad = it },
                label = { Text("Büronuzun / adınızın adı") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { if (ad.isNotBlank()) onOlustur(ad.trim(), seed) },
                enabled = ad.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("İmparatorluğu Kur")
            }
        }
    }
}
