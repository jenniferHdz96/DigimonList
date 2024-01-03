package com.example.digimon.data.api

import com.example.digimon.data.model.Digimon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DigimonApiService{
    @GET("digimon")
    suspend fun getDigimon(): Response<List<Digimon>>

    @GET("digimon/name/{name}")
    suspend fun getDigimonByName(@Path("name") name: String): Response<Digimon>
}