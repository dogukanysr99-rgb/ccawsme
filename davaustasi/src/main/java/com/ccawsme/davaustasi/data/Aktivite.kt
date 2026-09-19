package com.ccawsme.davaustasi.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness2
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector

enum class AktiviteId {
    CALIS, DAVA_AL, DINLEN, DERS_CALIS, SOSYALLES, UYU
}

data class Aktivite(
    val id: AktiviteId,
    val etiket: String,
    val aciklama: String,
    val ikon: ImageVector,
    val konum: Konum,
    val enerjiMaliyeti: Int,
    val mutlulukEtkisi: Int,
    val bilgiEtkisi: Int,
    val itibarEtkisi: Int,
    val paraEtkisi: Long,
    val kariyerPuaniEtkisi: Long,
    val uygunZamanlar: Set<ZamanDilimi>,
    val minUnvanIndex: Int = 0,
    val minEnerji: Int = 0
)

val AKTIVITELER = listOf(
    Aktivite(
        id = AktiviteId.CALIS,
        etiket = "Çalış",
        aciklama = "Ofiste dosyalarınıza bakın, para ve kariyer puanı kazanın.",
        ikon = Icons.Filled.Work,
        konum = Konum.OFIS,
        enerjiMaliyeti = 25,
        mutlulukEtkisi = -5,
        bilgiEtkisi = 1,
        itibarEtkisi = 0,
        paraEtkisi = 0,
        kariyerPuaniEtkisi = 120,
        uygunZamanlar = setOf(ZamanDilimi.SABAH, ZamanDilimi.OGLEN),
        minEnerji = 25
    ),
    Aktivite(
        id = AktiviteId.DAVA_AL,
        etiket = "Dava Al",
        aciklama = "Mahkemeye çıkın, riskli ama ödülü yüksek bir dava alın.",
        ikon = Icons.Filled.Gavel,
        konum = Konum.MAHKEME,
        enerjiMaliyeti = 30,
        mutlulukEtkisi = -10,
        bilgiEtkisi = 2,
        itibarEtkisi = 0,
        paraEtkisi = 0,
        kariyerPuaniEtkisi = 0,
        uygunZamanlar = setOf(ZamanDilimi.SABAH, ZamanDilimi.OGLEN),
        minUnvanIndex = 1,
        minEnerji = 30
    ),
    Aktivite(
        id = AktiviteId.DINLEN,
        etiket = "Dinlen",
        aciklama = "Evde dinlenerek enerjinizi toplayın.",
        ikon = Icons.Filled.Hotel,
        konum = Konum.EV,
        enerjiMaliyeti = -40,
        mutlulukEtkisi = 5,
        bilgiEtkisi = 0,
        itibarEtkisi = 0,
        paraEtkisi = 0,
        kariyerPuaniEtkisi = 0,
        uygunZamanlar = setOf(ZamanDilimi.SABAH, ZamanDilimi.OGLEN, ZamanDilimi.AKSAM)
    ),
    Aktivite(
        id = AktiviteId.DERS_CALIS,
        etiket = "Ders Çalış",
        aciklama = "Mevzuat ve içtihat okuyarak bilginizi geliştirin.",
        ikon = Icons.Filled.School,
        konum = Konum.EV,
        enerjiMaliyeti = 15,
        mutlulukEtkisi = -2,
        bilgiEtkisi = 8,
        itibarEtkisi = 0,
        paraEtkisi = 0,
        kariyerPuaniEtkisi = 20,
        uygunZamanlar = setOf(ZamanDilimi.AKSAM, ZamanDilimi.GECE),
        minEnerji = 15
    ),
    Aktivite(
        id = AktiviteId.SOSYALLES,
        etiket = "Sosyalleş",
        aciklama = "Meslektaşlarınızla vakit geçirin, çevrenizi genişletin.",
        ikon = Icons.Filled.Group,
        konum = Konum.DISARISI,
        enerjiMaliyeti = 15,
        mutlulukEtkisi = 15,
        bilgiEtkisi = 0,
        itibarEtkisi = 2,
        paraEtkisi = -50,
        kariyerPuaniEtkisi = 10,
        uygunZamanlar = setOf(ZamanDilimi.AKSAM),
        minEnerji = 15
    ),
    Aktivite(
        id = AktiviteId.UYU,
        etiket = "Uyu",
        aciklama = "Günü kapatın, yeni bir güne enerjik başlayın.",
        ikon = Icons.Filled.Brightness2,
        konum = Konum.EV,
        enerjiMaliyeti = -100,
        mutlulukEtkisi = 0,
        bilgiEtkisi = 0,
        itibarEtkisi = 0,
        paraEtkisi = 0,
        kariyerPuaniEtkisi = 0,
        uygunZamanlar = setOf(ZamanDilimi.GECE)
    )
)

fun uygunAktiviteler(karakter: Karakter): List<Aktivite> {
    val zaman = karakter.zamanDilimi()
    return AKTIVITELER.filter { aktivite ->
        zaman in aktivite.uygunZamanlar &&
            karakter.unvanIndex >= aktivite.minUnvanIndex &&
            (aktivite.enerjiMaliyeti <= 0 || karakter.enerji >= aktivite.minEnerji)
    }
}
