package com.example.pettomato.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pettomato.dataclasses.Pet
import com.example.pettomato.repositories.PlayerRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val playerRepository: PlayerRepository by lazy {
        PlayerRepository(application)
    }

    private val currentPet: MutableLiveData<Pet> by lazy {
        MutableLiveData<Pet>(playerRepository.getCurrentPet())
    }

    private val moneyAmount: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(playerRepository.getMoneyAmount())
    }

    fun getCurrentPet(): LiveData<Pet> = currentPet
    fun getMoneyAmount(): LiveData<Int> = moneyAmount
}