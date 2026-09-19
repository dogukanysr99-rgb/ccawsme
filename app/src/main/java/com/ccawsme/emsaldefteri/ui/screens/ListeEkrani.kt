package com.ccawsme.emsaldefteri.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ccawsme.emsaldefteri.data.Karar
import com.ccawsme.emsaldefteri.ui.KararViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListeEkrani(
    viewModel: KararViewModel,
    onKararTikla: (Long) -> Unit,
    onYeniEkle: () -> Unit
) {
    val kararlar by viewModel.kararlar.collectAsState()
    val sorgu by viewModel.sorgu.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Emsal Kararlarım") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onYeniEkle) {
                Icon(Icons.Default.Add, contentDescription = "Yeni ekle")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            OutlinedTextField(
                value = sorgu,
                onValueChange = viewModel::aramaGuncelle,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                placeholder = { Text("Başlık, mahkeme, etiket veya metinde ara...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            if (kararlar.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = if (sorgu.isBlank()) {
                            "Henüz emsal karar eklemediniz.\nSağ alttaki + butonuyla başlayın."
                        } else {
                            "Aramanızla eşleşen bir karar bulunamadı."
                        },
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)) {
                    items(kararlar, key = { it.id }) { karar ->
                        KararKarti(karar = karar, onTikla = { onKararTikla(karar.id) })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}

@Composable
private fun KararKarti(karar: Karar, onTikla: () -> Unit) {
    ElevatedCard(onClick = onTikla, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = karar.baslik.ifBlank { "(Başlıksız)" },
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (karar.favori) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Favori",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            val altBilgi = listOfNotNull(
                karar.mahkeme.takeIf { it.isNotBlank() },
                karar.kararNo.takeIf { it.isNotBlank() },
                karar.kararTarihi.takeIf { it.isNotBlank() }
            ).joinToString(" · ")

            if (altBilgi.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = altBilgi,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (karar.ozet.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = karar.ozet,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            val etiketler = karar.etiketler.split(",").map { it.trim() }.filter { it.isNotBlank() }
            if (etiketler.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    etiketler.take(4).forEach { etiket ->
                        AssistChip(
                            onClick = {},
                            label = { Text(etiket, style = MaterialTheme.typography.labelSmall) },
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
