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
    val secilenStrateji: Secenek? = null,
    val secilenDelil: Secenek? = null,
    val sonuc: DavaSonucu? = null
)
