package com.ccawsme.emsaldefteri.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ccawsme.emsaldefteri.data.Karar
import com.ccawsme.emsaldefteri.ui.KararViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DuzenleEkrani(
    kararId: Long?,
    viewModel: KararViewModel,
    onGeri: () -> Unit
) {
    var mevcutKarar by remember { mutableStateOf<Karar?>(null) }
    var yukleniyor by remember { mutableStateOf(kararId != null) }

    var baslik by remember { mutableStateOf("") }
    var mahkeme by remember { mutableStateOf("") }
    var kararNo by remember { mutableStateOf("") }
    var kararTarihi by remember { mutableStateOf("") }
    var ozet by remember { mutableStateOf("") }
    var metin by remember { mutableStateOf("") }
    var notlar by remember { mutableStateOf("") }
    var etiketler by remember { mutableStateOf("") }

    LaunchedEffect(kararId) {
        if (kararId != null) {
            val k = viewModel.getir(kararId)
            mevcutKarar = k
            k?.let {
                baslik = it.baslik
                mahkeme = it.mahkeme
                kararNo = it.kararNo
                kararTarihi = it.kararTarihi
                ozet = it.ozet
                metin = it.metin
                notlar = it.notlar
                etiketler = it.etiketler
            }
            yukleniyor = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (kararId == null) "Yeni Emsal Karar" else "Kararı Düzenle") },
                navigationIcon = {
                    IconButton(onClick = onGeri) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                },
                actions = {
                    IconButton(
                        enabled = baslik.isNotBlank(),
                        onClick = {
                            val kaydedilecek = (mevcutKarar ?: Karar(baslik = "")).copy(
                                baslik = baslik.trim(),
                                mahkeme = mahkeme.trim(),
                                kararNo = kararNo.trim(),
                                kararTarihi = kararTarihi.trim(),
                                ozet = ozet.trim(),
                                metin = metin.trim(),
                                notlar = notlar.trim(),
                                etiketler = etiketler.trim()
                            )
                            viewModel.kaydet(kaydedilecek) { onGeri() }
                        }
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Kaydet")
                    }
                }
            )
        }
    ) { padding ->
        if (yukleniyor) {
            Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = baslik,
                onValueChange = { baslik = it },
                label = { Text("Başlık *") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true
            )
            OutlinedTextField(
                value = mahkeme,
                onValueChange = { mahkeme = it },
                label = { Text("Mahkeme / Daire") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = kararNo,
                    onValueChange = { kararNo = it },
                    label = { Text("Karar No") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = kararTarihi,
                    onValueChange = { kararTarihi = it },
                    label = { Text("Karar Tarihi") },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("GG.AA.YYYY") },
                    singleLine = true
                )
            }
            OutlinedTextField(
                value = etiketler,
                onValueChange = { etiketler = it },
                label = { Text("Etiketler (virgülle ayırın)") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("iş hukuku, kıdem tazminatı") },
                singleLine = true
            )
            OutlinedTextField(
                value = ozet,
                onValueChange = { ozet = it },
                label = { Text("Özet") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 4
            )
            OutlinedTextField(
                value = metin,
                onValueChange = { metin = it },
                label = { Text("Emsal Metni") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                maxLines = 12
            )
            OutlinedTextField(
                value = notlar,
                onValueChange = { notlar = it },
                label = { Text("Kişisel Notlarım") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 8
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
