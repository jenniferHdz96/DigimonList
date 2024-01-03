package com.example.digimon.data.model

data class Digimon(
    var name: String,
    var img: String,
    var level: String
){
    fun doesMatchSearchQuery(query: String): Boolean{
        val matching = listOf(
            name
        )

        return matching.any{
            it.contains(query, ignoreCase = true)
        }
    }
}