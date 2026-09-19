package com.ccawsme.sinavhazirlik.data

data class DogruYanlisSorusu(
    val id: String,
    val konuId: String,
    val ifade: String,
    val dogruMu: Boolean,
    val aciklama: String
)

val DOGRU_YANLIS_SORULARI = listOf(
    DogruYanlisSorusu(
        "ana_dy1", "anayasa",
        "Anayasa'nın ilk dört maddesi değiştirilebilir.",
        false,
        "Bu maddeler değiştirilemez ve değiştirilmesi teklif dahi edilemez."
    ),
    DogruYanlisSorusu(
        "ana_dy2", "anayasa",
        "Türkiye Cumhuriyeti'nin resmi dili Türkçedir.",
        true,
        "Anayasa m.3 uyarınca devletin dili Türkçedir."
    ),
    DogruYanlisSorusu(
        "ana_dy3", "anayasa",
        "Cumhurbaşkanı 7 yıl için seçilir.",
        false,
        "Cumhurbaşkanı 5 yıl için seçilir."
    ),
    DogruYanlisSorusu(
        "med_dy1", "medeni",
        "Cenin, sağ doğmak kaydıyla hak ehliyetine sahiptir.",
        true,
        "TMK m.28 uyarınca cenin, sağ doğmak şartıyla ana rahmine düştüğü andan itibaren hak ehliyetine sahiptir."
    ),
    DogruYanlisSorusu(
        "med_dy2", "medeni",
        "Ergin olmayan herkes ayırt etme gücünden tamamen yoksundur.",
        false,
        "Ayırt etme gücü yaşla birebir ölçülmez; küçükler de somut olayda ayırt etme gücüne sahip olabilir."
    ),
    DogruYanlisSorusu(
        "med_dy3", "medeni",
        "Tüzel kişiler hiçbir şekilde hak ehliyetine sahip olamaz.",
        false,
        "Tüzel kişiler, insana özgü nitelikler dışında kalan hak ve borçlara ehildir."
    ),
    DogruYanlisSorusu(
        "ceza_dy1", "ceza",
        "Taksirli suçlarda fail, neticeyi bilerek ve isteyerek gerçekleştirir.",
        false,
        "Bilerek ve isteyerek gerçekleştirme kasttır; taksirde netice öngörülmeden meydana gelir."
    ),
    DogruYanlisSorusu(
        "ceza_dy2", "ceza",
        "Meşru müdafaa, ceza sorumluluğunu ortadan kaldıran bir hukuka uygunluk sebebidir.",
        true,
        "Meşru müdafaa halinde fail cezalandırılmaz."
    ),
    DogruYanlisSorusu(
        "ceza_dy3", "ceza",
        "Kanunda açıkça suç olarak tanımlanmayan bir fiil cezalandırılamaz.",
        true,
        "Bu, kanunilik ilkesinin bir sonucudur."
    )
)

fun dogruYanlisSorulari(konuId: String?): List<DogruYanlisSorusu> =
    if (konuId == null) DOGRU_YANLIS_SORULARI else DOGRU_YANLIS_SORULARI.filter { it.konuId == konuId }
