package com.example.digimon.ui.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digimon.data.model.Digimon
import com.example.digimon.ui.usecases.GetDigimon
import com.example.digimon.ui.usecases.GetDigimonByName
import kotlinx.coroutines.launch

class DigimonViewModel : ViewModel(){

    private val getDigimonUseCase = GetDigimon()
    private val getDigimonByNameUseCase = GetDigimonByName()

    private val _digimonList = mutableStateListOf<Digimon>()
    val digimonList: List<Digimon> get() = _digimonList

    private val _digimonByName = MutableLiveData<Digimon>()
    val digimonByNameStateLiveData: LiveData<Digimon> = _digimonByName

    private val _connectionState = MutableLiveData<Boolean>(false)

    fun getDigimonList() = viewModelScope.launch{
        _connectionState.value = true
        getDigimonUseCase().let {
            _digimonList.clear()
            _digimonList.addAll(it!!)
        }
        returnConnectionState()
    }

    fun getDigimonByName(name: String) = viewModelScope.launch{
        getDigimonByNameUseCase(name).let { _digimonByName.value = it }
    }

    private fun returnConnectionState() {
        _connectionState.value = false
    }
}