package com.ccawsme.davaustasi.data

enum class ZamanDilimi(val etiket: String) {
    SABAH("Sabah"),
    OGLEN("Öğlen"),
    AKSAM("Akşam"),
    GECE("Gece")
}

enum class Konum(val etiket: String) {
    EV("Ev"),
    OFIS("Ofis"),
    MAHKEME("Mahkeme"),
    DISARISI("Dışarısı")
}
