package com.ccawsme.davaustasi.data

import kotlin.random.Random

private val KISI_ISIMLERI = listOf(
    "Ahmet Yılmaz", "Ayşe Demir", "Mehmet Kaya", "Fatma Şahin", "Ali Çelik",
    "Zeynep Arslan", "Mustafa Aydın", "Elif Yıldız", "Hasan Koç", "Emine Polat"
)

private val SIRKET_ISIMLERI = listOf(
    "Akın Tekstil A.Ş.", "Deniz Lojistik Ltd. Şti.", "Güneş Gıda San. Tic.",
    "Kaya İnşaat A.Ş.", "Yıldız Elektronik Ltd.", "Berk Otomotiv A.Ş."
)

private val OLAY_SABLONLARI: Map<String, List<String>> = mapOf(
    "Sulh Hukuk Davası" to listOf(
        "Müvekkiliniz %1\$s, komşusu %2\$s ile ortak bahçe duvarı yüzünden anlaşmazlığa düştü. %2\$s, duvarın izinsiz yıkıldığını iddia ediyor.",
        "Müvekkiliniz %1\$s, kiraladığı dairenin depozitosunu geri alamadığı için ev sahibi %2\$s'i dava ediyor.",
        "Müvekkiliniz %1\$s, komşusu %2\$s'in gürültü kirliliği yaptığını iddia ederek tazminat talep ediyor."
    ),
    "İş Hukuku Davası" to listOf(
        "Müvekkiliniz %1\$s, %3\$s şirketinden haksız yere işten çıkarıldığını iddia ederek kıdem ve ihbar tazminatı talep ediyor.",
        "Müvekkiliniz %1\$s, %3\$s şirketinde fazla mesai ücretlerinin ödenmediğini iddia ediyor.",
        "Müvekkiliniz %1\$s, %3\$s şirketi tarafından mobbinge maruz bırakıldığını iddia ediyor."
    ),
    "Ticari Dava" to listOf(
        "Müvekkiliniz %3\$s, tedarikçisi %4\$s ile yapılan sözleşmeyi ihlal ettiği gerekçesiyle tazminat talep ediyor.",
        "Müvekkiliniz %3\$s, ortağı %4\$s ile şirket paylarının bölüşümü konusunda anlaşmazlık yaşıyor.",
        "Müvekkiliniz %3\$s, %4\$s'e kestiği faturaların ödenmediğini iddia ederek alacak davası açtı."
    ),
    "Ağır Ceza Davası" to listOf(
        "Müvekkiliniz %1\$s, %2\$s'e karşı işlenen bir saldırı iddiasıyla yargılanıyor; savunmanız meşru müdafaa temelli olacak.",
        "Müvekkiliniz %1\$s, %2\$s'in mal varlığına verilen zarardan sorumlu tutuluyor; kastının bulunmadığını savunacaksınız.",
        "Müvekkiliniz %1\$s hakkında %2\$s'in şikayeti üzerine soruşturma açıldı; delillerin yetersizliğini savunacaksınız."
    ),
    "Yargıtay Temyiz Başvurusu" to listOf(
        "Alt derece mahkemesinin %1\$s aleyhine verdiği kararı temyiz ediyorsunuz; usul hatası iddianız var.",
        "Bölge Adliye Mahkemesi'nin %1\$s lehine verdiği kararın bozulması için karşı taraf temyize gitti.",
        "Emsal kararlarla çelişen bir yerel mahkeme kararını, %1\$s adına temyiz ediyorsunuz."
    )
)

fun davaOlayiUret(davaTuru: DavaTuru): String {
    val sablonlar = OLAY_SABLONLARI[davaTuru.ad] ?: listOf("\"${davaTuru.ad}\" dosyası görüşülmeye başlanıyor.")
    val sablon = sablonlar[Random.nextInt(sablonlar.size)]

    val kisi1 = KISI_ISIMLERI[Random.nextInt(KISI_ISIMLERI.size)]
    var kisi2 = KISI_ISIMLERI[Random.nextInt(KISI_ISIMLERI.size)]
    while (kisi2 == kisi1) kisi2 = KISI_ISIMLERI[Random.nextInt(KISI_ISIMLERI.size)]

    val sirket1 = SIRKET_ISIMLERI[Random.nextInt(SIRKET_ISIMLERI.size)]
    var sirket2 = SIRKET_ISIMLERI[Random.nextInt(SIRKET_ISIMLERI.size)]
    while (sirket2 == sirket1) sirket2 = SIRKET_ISIMLERI[Random.nextInt(SIRKET_ISIMLERI.size)]

    return String.format(sablon, kisi1, kisi2, sirket1, sirket2)
}
