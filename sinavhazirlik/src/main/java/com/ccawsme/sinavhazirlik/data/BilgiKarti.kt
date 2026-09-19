package com.ccawsme.sinavhazirlik.data

data class BilgiKarti(
    val id: String,
    val konuId: String,
    val onYuz: String,
    val arkaYuz: String
)

val BILGI_KARTLARI = listOf(
    BilgiKarti(
        "ana_1", "anayasa",
        "Anayasa'nın ilk dört maddesi için ne söylenebilir?",
        "Değiştirilemez ve değiştirilmesi teklif dahi edilemez: Devletin şekli, Cumhuriyetin nitelikleri, bütünlük, resmi dil ve başkent gibi hususları düzenler."
    ),
    BilgiKarti(
        "ana_2", "anayasa",
        "Türkiye Cumhuriyeti'nin yönetim şekli nedir?",
        "Cumhuriyettir (Anayasa m.1)."
    ),
    BilgiKarti(
        "ana_3", "anayasa",
        "Anayasa Mahkemesi'nin temel görevi nedir?",
        "Kanunların, Cumhurbaşkanlığı kararnamelerinin ve TBMM İçtüzüğü'nün Anayasa'ya şekil ve esas bakımından uygunluğunu denetlemektir."
    ),
    BilgiKarti(
        "ana_4", "anayasa",
        "Cumhurbaşkanı kaç yıl için seçilir?",
        "5 yıl için seçilir; bir kişi en fazla iki defa seçilebilir."
    ),
    BilgiKarti(
        "ana_5", "anayasa",
        "TBMM üye tam sayısı kaçtır?",
        "600'dür."
    ),
    BilgiKarti(
        "med_1", "medeni",
        "Gerçek kişilik ne zaman başlar?",
        "Çocuğun sağ olarak tamamıyla doğduğu anda başlar; cenin, sağ doğmak kaydıyla ana rahmine düştüğü andan itibaren hak ehliyetine sahiptir (TMK m.28)."
    ),
    BilgiKarti(
        "med_2", "medeni",
        "Ayırt etme gücü (temyiz kudreti) nedir?",
        "Akla uygun biçimde davranabilme, bir işin sebep ve sonuçlarını değerlendirebilme yeteneğidir."
    ),
    BilgiKarti(
        "med_3", "medeni",
        "Ergin (reşit) olma yaşı kaçtır?",
        "18 yaşını doldurmakla erginlik kazanılır; ayrıca evlenme de kişiyi ergin kılar."
    ),
    BilgiKarti(
        "med_4", "medeni",
        "Yerleşim yeri kavramı ne anlama gelir?",
        "Bir kimsenin sürekli kalma niyetiyle oturduğu yerdir."
    ),
    BilgiKarti(
        "med_5", "medeni",
        "Tüzel kişiler hak ehliyeti bakımından nasıl bir sınırla karşı karşıyadır?",
        "İnsana özgü nitelikler (cinsiyet, yaş gibi) dışında, kanuna göre hak ve borçlara ehildirler."
    ),
    BilgiKarti(
        "ceza_1", "ceza",
        "'Kanunsuz suç ve ceza olmaz' ilkesi (kanunilik ilkesi) nedir?",
        "Bir fiilin suç sayılması ve cezalandırılması, ancak kanunda açıkça düzenlenmiş olmasına bağlıdır (TCK m.2)."
    ),
    BilgiKarti(
        "ceza_2", "ceza",
        "Kast nedir?",
        "Suçun kanuni tanımındaki unsurların bilerek ve istenerek gerçekleştirilmesidir."
    ),
    BilgiKarti(
        "ceza_3", "ceza",
        "Taksir nedir?",
        "Dikkat ve özen yükümlülüğüne aykırılık dolayısıyla, bir davranışın suçun kanuni tanımındaki neticesi öngörülmeksizin gerçekleştirilmesidir."
    ),
    BilgiKarti(
        "ceza_4", "ceza",
        "Meşru müdafaa nedir?",
        "Haksız bir saldırıya karşı, kendisini veya başkasını korumak amacıyla zorunlu ve orantılı olarak yapılan savunmadır; ceza sorumluluğunu ortadan kaldırır."
    ),
    BilgiKarti(
        "ceza_5", "ceza",
        "Ceza hukukunda zamanaşımı ne anlama gelir?",
        "Belirli bir sürenin geçmesiyle devletin dava açma veya hükmedilen cezayı infaz etme yetkisinin ortadan kalkmasıdır."
    )
)

fun bilgiKartlari(konuId: String?): List<BilgiKarti> =
    if (konuId == null) BILGI_KARTLARI else BILGI_KARTLARI.filter { it.konuId == konuId }
