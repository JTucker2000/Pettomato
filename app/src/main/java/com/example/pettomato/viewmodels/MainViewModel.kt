package com.example.pettomato.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pettomato.AppDatabase
import com.example.pettomato.repositories.PlayerRepository
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

    // FOR PREPOPULATING DATABASE IN TESTING
    fun addPet(petEntity: PetEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepository.addPet(petEntity)
        }
    }

    // FOR PREPOPULATING DATABASE IN TESTING
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

            if(curPet.hunger_level < 100) {
                // Update pet values
                if(curPet.hunger_level > 75) curPet.hunger_level = 100
                else curPet.hunger_level += 25
                if(curPlayer.money_amount < 10) curPlayer.money_amount = 0
                else curPlayer.money_amount -= 10
            }

            playerRepository.updatePlayer(curPlayer)
            playerRepository.updatePet(curPet)
        }
    }

    // Handles when give water button is pressed in actions listview.
    fun onGiveWaterBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")
            val curPet = playerRepository.getPetById(1)

            if(curPet.thirst_level < 100) {
                // Update pet values
                if(curPlayer.money_amount < 5) curPlayer.money_amount = 0
                else curPlayer.money_amount -= 5
                if(curPet.thirst_level > 75) curPet.thirst_level = 100
                else curPet.thirst_level += 25
            }

            playerRepository.updatePlayer(curPlayer)
            playerRepository.updatePet(curPet)
        }
    }

    // Handles when pet button is pressed in actions listview.
    fun onPetBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPet = playerRepository.getPetById(1)

            if(curPet.happiness_level < 100) {
                // Update pet values
                if(curPet.happiness_level > 75) curPet.happiness_level = 100
                else curPet.happiness_level += 25
            }

            playerRepository.updatePet(curPet)
        }
    }

    // Handles when walk button is pressed in actions listview.
    fun onWalkBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPet = playerRepository.getPetById(1)

            if((curPet.happiness_level < 100) || (curPet.fitness_level < 100)) {
                // Update pet values
                if(curPet.happiness_level > 90) curPet.happiness_level = 100
                else curPet.happiness_level += 10
                if(curPet.fitness_level > 75) curPet.fitness_level = 100
                else curPet.fitness_level += 25
            }

            playerRepository.updatePet(curPet)
        }
    }

    // Handles when go to work button is pressed in actions listview.
    fun onWorkBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")
            val curPet = playerRepository.getPetById(1)

            if(curPet.happiness_level > 0) {
                // Update pet values
                curPlayer.money_amount += 100
                if(curPet.happiness_level < 10) curPet.happiness_level = 0
                else curPet.happiness_level -= 10
            }

            playerRepository.updatePlayer(curPlayer)
            playerRepository.updatePet(curPet)
        }
    }

    // Handles when buy bandages button is pressed in shop listview.
    fun onBuyBandagesBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")

            if(curPlayer.money_amount >= 30) {
                curPlayer.money_amount -= 30
                curPlayer.num_bandages += 1
            }

            playerRepository.updatePlayer(curPlayer)
        }
    }

    // Handles when buy firstaid button is pressed in shop listview.
    fun onBuyFirstAidBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")

            if(curPlayer.money_amount >= 100) {
                curPlayer.money_amount -= 100
                curPlayer.num_firstaid += 1
            }

            playerRepository.updatePlayer(curPlayer)
        }
    }

    // Handles when buy iron paws button is pressed in shop listview.
    fun onBuyIronPawsBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")

            if(curPlayer.money_amount >= 50) {
                curPlayer.money_amount -= 50
                curPlayer.num_ironpaw += 1
            }

            playerRepository.updatePlayer(curPlayer)
        }
    }
}