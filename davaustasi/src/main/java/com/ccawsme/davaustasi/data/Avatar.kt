package com.ccawsme.davaustasi.data

fun avatarUrl(seed: String): String =
    "https://api.dicebear.com/9.x/personas/png?seed=${seed}&size=160"
