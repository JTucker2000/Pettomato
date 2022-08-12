package com.example.pettomato.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pettomato.AppDatabase
import com.example.pettomato.R
import com.example.pettomato.repositories.EnemyRepository
import com.example.pettomato.repositories.PlayerRepository
import com.example.pettomato.roomentities.EnemyEntity
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.roomentities.PlayerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PetArenaViewModel(application: Application) : AndroidViewModel(application) {
    private val playerRepository: PlayerRepository
    private val enemyRepository: EnemyRepository
    val enemyLive: LiveData<EnemyEntity>
    val petListLive: LiveData<List<PetEntity>>
    val playerLive: LiveData<PlayerEntity>

    init {
        val database = AppDatabase.getDatabase(application)
        val petDao = database.petDao()
        val playerDao = database.playerDao()
        val enemyDao = database.enemyDao()
        playerRepository = PlayerRepository(petDao, playerDao)
        enemyRepository = EnemyRepository(enemyDao, playerDao)
        enemyLive = enemyRepository.enemyLive
        petListLive = playerRepository.petListLive
        playerLive = playerRepository.playerLive
    }

    // Changes the current enemy based on the given arena level.
    private fun setEnemy(arenaLevel: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (arenaLevel) {
                1 -> enemyRepository.updateEnemy(EnemyEntity(1, "AngryCorgi", R.drawable.corgiface1, 1, 10, 10))
                2 -> enemyRepository.updateEnemy(EnemyEntity(1, "AngryShepard", R.drawable.germanshepard1, 3, 35, 35))
                else -> enemyRepository.updateEnemy(EnemyEntity(1, "MegaAngryCorgi", R.drawable.corgiface1, 100, 1000, 1000))
            }
        }
    }

    // FOR PREPOPULATING DATABASE IN TESTING
    fun addEnemy(enemyEntity: EnemyEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            enemyRepository.addEnemy(enemyEntity)
        }
    }

    fun onAttackBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPet = playerRepository.getPetById(1)
            val curEnemy = enemyRepository.getEnemyById(1)

            // Update pet values
            val enemyDamage = curEnemy.enemy_level * 2
            if(curPet.pet_health >= enemyDamage) curPet.pet_health -= enemyDamage
            else curPet.pet_health = 0

            // Update enemy values
            val playerDamage = curPet.pet_level * 2
            if(curEnemy.enemy_health >= playerDamage) curEnemy.enemy_health -= playerDamage
            else curEnemy.enemy_health = 0

            playerRepository.updatePet(curPet)
            enemyRepository.updateEnemy(curEnemy)
        }
    }

    // Resets player / enemy health and reduces pet's statuses.
    fun onPlayerDefeat() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")
            val curPet = playerRepository.getPetById(1)

            // Change pet status
            curPet.pet_health = curPet.pet_maxhp
            if(curPet.hunger_level > 25) curPet.hunger_level = 25
            if(curPet.thirst_level > 25) curPet.thirst_level = 25
            if(curPet.happiness_level > 25) curPet.happiness_level = 25
            if(curPet.fitness_level > 25) curPet.fitness_level = 25

            // Reset the enemy
            setEnemy(curPlayer.arena_level)

            playerRepository.updatePet(curPet)
        }
    }

    // Rewards the player for defeating the opponent and changes to the next enemy.
    // The next enemy is determined by the player's arena level.
    fun onEnemyDefeat() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")
            val curEnemy = enemyRepository.getEnemyById(1)

            // Reward the player
            curPlayer.money_amount += 100 * curEnemy.enemy_level
            curPlayer.arena_level += 1

            // Change the enemy to new enemy
            setEnemy(curPlayer.arena_level)

            playerRepository.updatePlayer(curPlayer)
        }
    }
}