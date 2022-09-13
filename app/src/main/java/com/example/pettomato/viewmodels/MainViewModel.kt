package com.example.pettomato.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pettomato.*
import com.example.pettomato.repositories.PlayerRepository
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.roomentities.PlayerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val playerRepository: PlayerRepository
    val petLive: LiveData<PetEntity>
    val petListLive: LiveData<List<PetEntity>>
    val playerLive: LiveData<PlayerEntity>

    // Animation variables
    var playLevelUpSuccessAnimation: Boolean = false
    var playLevelUpFailAnimation: Boolean = false

    init {
        val database = AppDatabase.getDatabase(application)
        val petDao = database.petDao()
        val playerDao = database.playerDao()
        playerRepository = PlayerRepository(petDao, playerDao)
        petLive = playerRepository.petLive
        petListLive = playerRepository.petListLive
        playerLive = playerRepository.playerLive
    }

    // Gives image IDs to database on the first run on the game on this device.
    // Always sets to the orange cat image set since everyone starts with that pet.
    fun initImageIDs() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPet = playerRepository.getPetById(CURRENT_PET_ID)

            curPet.normal_image_id = R.drawable.normalcat1
            curPet.happy_image_id = R.drawable.happycat1
            curPet.sad_image_id = R.drawable.sadcat1
            curPet.angry_image_id = R.drawable.angrycat1

            playerRepository.updatePet(curPet)
        }
    }

    // Handles when feed button is pressed in actions listview.
    fun onFeedBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername(PLAYER_USERNAME)
            val curPet = playerRepository.getPetById(CURRENT_PET_ID)

            if(curPet.hunger_level < 100 && curPlayer.money_amount >= FEED_PRICE) {
                // Update pet values
                curPlayer.money_amount -= FEED_PRICE
                if(curPet.hunger_level > 75) curPet.hunger_level = 100
                else curPet.hunger_level += 25
            }

            playerRepository.updatePlayer(curPlayer)
            playerRepository.updatePet(curPet)
        }
    }

    // Handles when give water button is pressed in actions listview.
    fun onGiveWaterBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername(PLAYER_USERNAME)
            val curPet = playerRepository.getPetById(CURRENT_PET_ID)

            if(curPet.thirst_level < 100 && curPlayer.money_amount >= GIVEWATER_PRICE) {
                // Update values
                curPlayer.money_amount -= GIVEWATER_PRICE
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
            val curPet = playerRepository.getPetById(CURRENT_PET_ID)

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
            val curPet = playerRepository.getPetById(CURRENT_PET_ID)

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

    // Handles when buy bandages button is pressed in shop listview.
    fun onBuyBandagesBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername(PLAYER_USERNAME)

            if(curPlayer.money_amount >= BANDAGE_PRICE) {
                curPlayer.money_amount -= BANDAGE_PRICE
                curPlayer.num_bandages += 1
            }

            playerRepository.updatePlayer(curPlayer)
        }
    }

    // Handles when buy firstaid button is pressed in shop listview.
    fun onBuyFirstAidBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername(PLAYER_USERNAME)

            if(curPlayer.money_amount >= FIRSTAID_PRICE) {
                curPlayer.money_amount -= FIRSTAID_PRICE
                curPlayer.num_firstaid += 1
            }

            playerRepository.updatePlayer(curPlayer)
        }
    }

    // Handles when buy iron paws button is pressed in shop listview.
    fun onBuyIronPawsBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername(PLAYER_USERNAME)

            if(curPlayer.money_amount >= IRONPAW_PRICE) {
                curPlayer.money_amount -= IRONPAW_PRICE
                curPlayer.num_ironpaw += 1
            }

            playerRepository.updatePlayer(curPlayer)
        }
    }

    // Handles when confirm level up button has been pressed.
    fun onConfirmLevelUpBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPet = playerRepository.getPetById(CURRENT_PET_ID)
            val curPlayer = playerRepository.getPlayerByUsername(PLAYER_USERNAME)

            if(curPlayer.money_amount >= curPet.levelUpCost) {
                curPlayer.money_amount -= curPet.levelUpCost
                curPet.pet_level++
                playLevelUpSuccessAnimation = true
            } else {
                playLevelUpFailAnimation = true
            }

            playerRepository.updatePet(curPet)
            playerRepository.updatePlayer(curPlayer)
        }
    }
}