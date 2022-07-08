package com.example.pettomato.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pettomato.AppDatabase
import com.example.pettomato.repositories.PlayerRepository
import com.example.pettomato.roomdaos.PlayerDao
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.roomentities.PlayerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val playerRepository: PlayerRepository
    val petList: LiveData<List<PetEntity>>
    val player: LiveData<PlayerEntity>

    init {
        val database = AppDatabase.getDatabase(application)
        val petDao = database.petDao()
        val playerDao = database.playerDao()
        playerRepository = PlayerRepository(petDao, playerDao)
        petList = playerRepository.petList
        player = playerRepository.player
    }

    fun addPet(petEntity: PetEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepository.addPet(petEntity)
        }
    }

    fun addPlayer(playerEntity: PlayerEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepository.addPlayer(playerEntity)
        }
    }
}