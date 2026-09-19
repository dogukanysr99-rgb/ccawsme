package com.ccawsme.davaustasi.data

data class DavaSonucu(
    val kazandi: Boolean,
    val basariSansi: Double,
    val kazanilanPara: Long,
    val kazanilanItibar: Long,
    val kazanilanDeneyim: Long
)

data class DavaSureci(
    val davaTuru: DavaTuru,
    val asama: DavaAsamasi = DavaAsamasi.ACILIS,
    val olayMetni: String = "",
    val karsiIddiaMetni: String = "",
    val secilenStrateji: Secenek? = null,
    val stratejiTepkisi: String = "",
    val secilenDelil: Secenek? = null,
    val delilTepkisi: String = "",
    val sonuc: DavaSonucu? = null
) {
    fun aktifTaraf(): AktifTaraf = when (asama) {
        DavaAsamasi.ACILIS -> AktifTaraf.HAKIM
        DavaAsamasi.KARSI_IDDIA -> AktifTaraf.KARSI_TARAF
        DavaAsamasi.STRATEJI -> AktifTaraf.SAVUNMA
        DavaAsamasi.DELIL -> AktifTaraf.TANIK
        DavaAsamasi.KARAR -> AktifTaraf.HAKIM
    }
}
