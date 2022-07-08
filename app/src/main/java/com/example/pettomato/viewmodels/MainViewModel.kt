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
    val petListLive: LiveData<List<PetEntity>>
    val playerLive: LiveData<PlayerEntity>

    init {
        val database = AppDatabase.getDatabase(application)
        val petDao = database.petDao()
        val playerDao = database.playerDao()
        playerRepository = PlayerRepository(petDao, playerDao)
        petListLive = playerRepository.petListLive
        playerLive = playerRepository.playerLive
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

    // Handles when feed button is pressed in actions listview.
    fun onFeedBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")
            val curPet = playerRepository.getPetById(1)

            if(curPlayer.money_amount < 10) curPlayer.money_amount = 0
            else curPlayer.money_amount -= 10
            if(curPet.hunger_level > 75) curPet.hunger_level = 100
            else curPet.hunger_level += 25

            playerRepository.updatePlayer(curPlayer)
            playerRepository.updatePet(curPet)
        }
    }

    // Handles when give water button is pressed in actions listview.
    fun onGiveWaterBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")
            val curPet = playerRepository.getPetById(1)

            if(curPlayer.money_amount < 5) curPlayer.money_amount = 0
            else curPlayer.money_amount -= 5
            if(curPet.thirst_level > 75) curPet.thirst_level = 100
            else curPet.thirst_level += 25

            playerRepository.updatePlayer(curPlayer)
            playerRepository.updatePet(curPet)
        }
    }

    // Handles when pet button is pressed in actions listview.
    fun onPetBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPet = playerRepository.getPetById(1)

            if(curPet.happiness_level > 75) curPet.happiness_level = 100
            else curPet.happiness_level += 25

            playerRepository.updatePet(curPet)
        }
    }

    // Handles when walk button is pressed in actions listview.
    fun onWalkBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPet = playerRepository.getPetById(1)

            if(curPet.happiness_level > 90) curPet.happiness_level = 100
            else curPet.happiness_level += 10
            if(curPet.fitness_level > 75) curPet.fitness_level = 100
            else curPet.fitness_level += 25

            playerRepository.updatePet(curPet)
        }
    }

    // Handles when go to work button is pressed in actions listview.
    fun onWorkBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")
            val curPet = playerRepository.getPetById(1)

            curPlayer.money_amount += 100
            if(curPet.happiness_level < 10) curPet.happiness_level = 0
            else curPet.happiness_level -= 10

            playerRepository.updatePlayer(curPlayer)
            playerRepository.updatePet(curPet)
        }
    }
}