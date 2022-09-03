package com.example.pettomato.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.pettomato.AppDatabase
import com.example.pettomato.repositories.PlayerRepository
import com.example.pettomato.roomentities.PetEntity

class GameOverViewModel(application: Application) : AndroidViewModel(application) {
    private val playerRepository: PlayerRepository
    val petLive: LiveData<PetEntity>

    init {
        val database = AppDatabase.getDatabase(application)
        val petDao = database.petDao()
        val playerDao = database.playerDao()
        playerRepository = PlayerRepository(petDao, playerDao)
        petLive = playerRepository.petLive
    }
}