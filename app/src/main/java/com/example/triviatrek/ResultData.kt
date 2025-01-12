package com.example.triviatrek

data class ResultData(
    val tokenName: String,
    var results: List<Pair<String, Int>> = listOf(Pair("No User",0))
)