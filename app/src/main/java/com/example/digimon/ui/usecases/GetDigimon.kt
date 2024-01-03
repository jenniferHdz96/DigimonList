package com.example.digimon.ui.usecases

import com.example.digimon.data.api.DigimonApiService
import com.example.digimon.data.api.RetrofitHelper
import com.example.digimon.data.model.Digimon

class GetDigimon {
    suspend operator fun invoke(): List<Digimon>? {
        var list: List<Digimon>? = null;

        try {
            val result = RetrofitHelper.getInstance().create(DigimonApiService::class.java).getDigimon()
            if (result.isSuccessful) list = result.body() ?: emptyList()
        } catch (e: Throwable){
            var message = e.message
            return list
        }

        return list
    }
}