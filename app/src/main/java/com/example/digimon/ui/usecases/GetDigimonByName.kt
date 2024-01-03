package com.example.digimon.ui.usecases

import com.example.digimon.data.api.DigimonApiService
import com.example.digimon.data.api.RetrofitHelper
import com.example.digimon.data.model.Digimon

class GetDigimonByName {
    suspend operator fun invoke(name: String): Digimon? {
        var model: Digimon? = null;

        try {
            val result = RetrofitHelper.getInstance().create(DigimonApiService::class.java).getDigimonByName(name)
            if (result.isSuccessful) model = result.body()
        } catch (e: Throwable){ return model }

        return model
    }
}