package com.example.pettomato

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val playerRepository: PlayerRepository by lazy {
        PlayerRepository()
    }

    private val currentPet: MutableLiveData<Pet> by lazy {
        MutableLiveData<Pet>(playerRepository.getCurrentPet())
    }

    fun getCurrentPet(): LiveData<Pet> {
        return currentPet
    }
}