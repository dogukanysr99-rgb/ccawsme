package com.ccawsme.emsaldefteri.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.ccawsme.emsaldefteri.data.Karar
import com.ccawsme.emsaldefteri.ui.KararViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetayEkrani(
    kararId: Long,
    viewModel: KararViewModel,
    onGeri: () -> Unit,
    onDuzenle: () -> Unit
) {
    var karar by remember { mutableStateOf<Karar?>(null) }
    var silmeOnayi by remember { mutableStateOf(false) }
    val clipboard = LocalClipboardManager.current

    LaunchedEffect(kararId) { karar = viewModel.getir(kararId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Karar Detayı") },
                navigationIcon = {
                    IconButton(onClick = onGeri) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                },
                actions = {
                    karar?.let { k ->
                        IconButton(onClick = {
                            val guncel = k.copy(favori = !k.favori)
                            viewModel.kaydet(guncel)
                            karar = guncel
                        }) {
                            Icon(
                                if (k.favori) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "Favori"
                            )
                        }
                    }
                    IconButton(onClick = onDuzenle) {
                        Icon(Icons.Default.Edit, contentDescription = "Düzenle")
                    }
                    IconButton(onClick = { silmeOnayi = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Sil")
                    }
                }
            )
        }
    ) { padding ->
        karar?.let { k ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(k.baslik.ifBlank { "(Başlıksız)" }, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))

                listOf(
                    "Mahkeme/Daire" to k.mahkeme,
                    "Karar No" to k.kararNo,
                    "Karar Tarihi" to k.kararTarihi
                ).forEach { (etiket, deger) ->
                    if (deger.isNotBlank()) {
                        Text(etiket, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(deger, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                val etiketler = k.etiketler.split(",").map { it.trim() }.filter { it.isNotBlank() }
                if (etiketler.isNotEmpty()) {
                    Text("Etiketler", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Row {
                        etiketler.forEach {
                            AssistChip(
                                onClick = {},
                                label = { Text(it) },
                                modifier = Modifier.padding(end = 4.dp, top = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (k.ozet.isNotBlank()) {
                    Text("Özet", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(k.ozet, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (k.metin.isNotBlank()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Emsal Metni",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(onClick = { clipboard.setText(AnnotatedString(k.metin)) }) {
                            Text("Kopyala")
                        }
                    }
                    Text(k.metin, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (k.notlar.isNotBlank()) {
                    Text("Kişisel Notlarım", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(k.notlar, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }

    if (silmeOnayi && karar != null) {
        AlertDialog(
            onDismissRequest = { silmeOnayi = false },
            title = { Text("Kararı sil") },
            text = { Text("Bu emsal kararı silmek istediğinizden emin misiniz? Bu işlem geri alınamaz.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.sil(karar!!)
                    silmeOnayi = false
                    onGeri()
                }) { Text("Sil") }
            },
            dismissButton = {
                TextButton(onClick = { silmeOnayi = false }) { Text("Vazgeç") }
            }
        )
    }
}
