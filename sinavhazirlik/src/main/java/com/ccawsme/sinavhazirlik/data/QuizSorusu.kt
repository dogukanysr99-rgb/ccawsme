package com.ccawsme.sinavhazirlik.data

data class QuizSorusu(
    val id: String,
    val konuId: String,
    val soru: String,
    val secenekler: List<String>,
    val dogruIndex: Int,
    val aciklama: String,
    val yil: Int? = null,
    val kaynak: String? = null
)

val QUIZ_SORULARI = listOf(
    QuizSorusu(
        "ana_q1", "anayasa",
        "Türkiye Cumhuriyeti Anayasası'na göre resmi dil nedir?",
        listOf("Türkçe", "Kürtçe", "Osmanlıca", "İngilizce"),
        0,
        "Anayasa m.3 uyarınca Türkiye Devleti'nin dili Türkçedir."
    ),
    QuizSorusu(
        "ana_q2", "anayasa",
        "Anayasa'nın ilk dört maddesi için hangisi doğrudur?",
        listOf("Değiştirilemez ve teklif edilemez", "Sadece halk oylamasıyla değişir", "Cumhurbaşkanı kararıyla değişir", "Değiştirilmesi tamamen serbesttir"),
        0,
        "Bu maddeler değiştirilemez, değiştirilmesi teklif dahi edilemez."
    ),
    QuizSorusu(
        "ana_q3", "anayasa",
        "TBMM üye tam sayısı kaçtır?",
        listOf("550", "600", "500", "650"),
        1,
        "2017 Anayasa değişikliği ile TBMM üye sayısı 600'e çıkarılmıştır."
    ),
    QuizSorusu(
        "med_q1", "medeni",
        "Gerçek kişilik ne zaman başlar?",
        listOf("Çocuğun sağ doğduğu anda", "18 yaşında", "Nüfusa kayıt anında", "Evlenme anında"),
        0,
        "Kişilik, çocuğun sağ olarak tamamıyla doğduğu anda başlar (TMK m.28)."
    ),
    QuizSorusu(
        "med_q2", "medeni",
        "Ergin (reşit) olma yaşı kaçtır?",
        listOf("15", "16", "18", "21"),
        2,
        "TMK'ya göre 18 yaşını dolduran kişi ergin olur."
    ),
    QuizSorusu(
        "med_q3", "medeni",
        "Yerleşim yeri kavramı neyi ifade eder?",
        listOf("Geçici olarak bulunulan yer", "Sürekli kalma niyetiyle oturulan yer", "Doğum yeri", "İş yeri"),
        1,
        "Yerleşim yeri, sürekli kalma niyetiyle oturulan yerdir."
    ),
    QuizSorusu(
        "ceza_q1", "ceza",
        "'Kanunsuz suç ve ceza olmaz' ilkesinin diğer adı nedir?",
        listOf("Kıyas yasağı ilkesi", "Kanunilik ilkesi", "Şahsilik ilkesi", "Orantılılık ilkesi"),
        1,
        "Bu ilkeye kanunilik ilkesi denir (TCK m.2)."
    ),
    QuizSorusu(
        "ceza_q2", "ceza",
        "Dikkat ve özen yükümlülüğüne aykırılıkla işlenen suç türü hangisidir?",
        listOf("Kast", "Taksir", "Teşebbüs", "İştirak"),
        1,
        "Taksir, dikkat ve özen yükümlülüğüne aykırılıktan kaynaklanır."
    ),
    QuizSorusu(
        "ceza_q3", "ceza",
        "Haksız bir saldırıya karşı zorunlu ve orantılı savunma yapılmasına ne denir?",
        listOf("Zorunluluk hali", "Meşru müdafaa", "Cebir", "Hata"),
        1,
        "Bu duruma meşru müdafaa denir ve ceza sorumluluğunu kaldırır."
    )
)

fun quizSorulari(konuId: String?): List<QuizSorusu> =
    if (konuId == null) QUIZ_SORULARI else QUIZ_SORULARI.filter { it.konuId == konuId }

fun cikmisSorular(yil: Int? = null): List<QuizSorusu> =
    QUIZ_SORULARI.filter { it.yil != null && (yil == null || it.yil == yil) }

fun cikmisYillar(): List<Int> =
    QUIZ_SORULARI.mapNotNull { it.yil }.distinct().sortedDescending()
