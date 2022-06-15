package com.example.pettomato

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val currentPet: MutableLiveData<Pet> by lazy {
        // Directly loads in a test pet for now
        MutableLiveData<Pet>(Pet ("Corgi", R.drawable.corgiface1,1,95, 80, 100, 50))
    }

    fun getCurrentPet(): LiveData<Pet> {
        return currentPet
    }
}