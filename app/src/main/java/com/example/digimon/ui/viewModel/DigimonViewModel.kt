package com.example.digimon.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digimon.data.model.Digimon
import com.example.digimon.ui.usecases.GetDigimon
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DigimonViewModel : ViewModel(){

    private val getDigimonUseCase = GetDigimon()

    private val _digimonList = mutableStateListOf<Digimon>()
    val digimonList: List<Digimon> get() = _digimonList

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _loading.value = false
        }
    }

    fun getDigimonList() = viewModelScope.launch{
        getDigimonUseCase().let {
            if (it != null) {
                _digimonList.clear()
                _digimonList.addAll(it)
            }
            else {
                _digimonList.clear()
            }
        }
    }
}