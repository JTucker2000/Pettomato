package com.example.pettomato.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pettomato.AppDatabase
import com.example.pettomato.repositories.PlayerRepository
import com.example.pettomato.roomentities.PetEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val playerRepository: PlayerRepository
    val petList: LiveData<List<PetEntity>>

    init {
        val petDao = AppDatabase.getDatabase(application).petDao()
        playerRepository = PlayerRepository(petDao)
        petList = playerRepository.petList
    }

    fun addPet(petEntity: PetEntity){
        viewModelScope.launch(Dispatchers.IO) {
            playerRepository.addPet(petEntity)
        }
    }
}