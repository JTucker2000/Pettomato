package com.example.pettomato.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pettomato.*
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
    val petLive: LiveData<PetEntity>
    val playerLive: LiveData<PlayerEntity>

    var attackIsOngoing: Boolean = false

    init {
        val database = AppDatabase.getDatabase(application)
        val petDao = database.petDao()
        val playerDao = database.playerDao()
        val enemyDao = database.enemyDao()
        playerRepository = PlayerRepository(petDao, playerDao)
        enemyRepository = EnemyRepository(enemyDao, playerDao)
        enemyLive = enemyRepository.enemyLive
        petLive = playerRepository.petLive
        playerLive = playerRepository.playerLive
    }

    // Changes the current enemy based on the given arena level.
    private fun setEnemy(arenaLevel: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if(arenaLevel <= ENEMY_LIST.size) enemyRepository.updateEnemy(ENEMY_LIST[arenaLevel-1])
            else enemyRepository.updateEnemy(EnemyEntity(CURRENT_ENEMY_ID, "Mega Angry Shiba", R.drawable.angrydog3, 100, 1000, 1000))
        }
    }

    // Checks to see if any goals have been completed and updates player information if needed.
    // Should be used after any action that may cause a goal to be reached.
    private suspend fun checkGoals(curPlayer: PlayerEntity) {
        var totalReward: Int = 0
        var checkAgain: Boolean = false

        // Check each goal and reward the player.
        if(curPlayer.toFightsWonGoal <= 0) {
            totalReward += curPlayer.fightsWonGoalReward
            curPlayer.fightswongoal *= 5
            checkAgain = true
        }
        if(curPlayer.toFightsLostGoal <= 0) {
            totalReward += curPlayer.fightsLostGoalReward
            curPlayer.fightslostgoal *= 5
            checkAgain = true
        }
        if(curPlayer.toCoinsEarnedGoal <= 0) {
            totalReward += curPlayer.coinsEarnedGoalReward
            curPlayer.coinsearnedgoal *= 20
            checkAgain = true
        }
        if(curPlayer.toBandagesUsedGoal <= 0) {
            totalReward += curPlayer.bandagesUsedGoalReward
            curPlayer.bandagesusedgoal *= 2
            checkAgain = true
        }
        if(curPlayer.toFirstAidUsedGoal <= 0) {
            totalReward += curPlayer.firstAidUsedGoalReward
            curPlayer.firstaidusedgoal *= 2
            checkAgain = true
        }
        if(curPlayer.toIronPawsUsedGoal <= 0) {
            totalReward += curPlayer.ironPawsUsedGoalReward
            curPlayer.ironpawsusedgoal *= 2
            checkAgain = true
        }
        if(curPlayer.toDamageDealtGoal <= 0) {
            totalReward += curPlayer.damageDealtGoalReward
            curPlayer.damagedealtgoal *= 5
            checkAgain = true
        }
        if(curPlayer.toDamageTakenGoal <= 0) {
            totalReward += curPlayer.damageTakenGoalReward
            curPlayer.damagetakengoal *= 5
            checkAgain = true
        }

        curPlayer.money_amount += totalReward
        curPlayer.num_arenacoinsearned += totalReward

        playerRepository.updatePlayer(curPlayer)
        if (checkAgain) checkGoals(curPlayer)
    }

    // Gives image IDs to database on the first run on the game on this device.
    // Always sets to the angry mouse image since everyone starts on that enemy.
    fun initImageIDs() {
        viewModelScope.launch(Dispatchers.IO) {
            val curEnemy = enemyRepository.getEnemyById(CURRENT_ENEMY_ID)

            curEnemy.image_id = R.drawable.angrymouse1

            enemyRepository.updateEnemy(curEnemy)
        }
    }

    fun onAttackBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPet = playerRepository.getPetById(CURRENT_PET_ID)
            val curPlayer = playerRepository.getPlayerByUsername(PLAYER_USERNAME)
            val curEnemy = enemyRepository.getEnemyById(CURRENT_ENEMY_ID)

            // Update pet values
            val enemyDamage = curEnemy.enemy_level * 2
            if(curPet.pet_health >= enemyDamage) curPet.pet_health -= enemyDamage
            else curPet.pet_health = 0

            // Update enemy values
            val playerDamage = curPet.pet_level * 2
            if(curEnemy.enemy_health >= playerDamage) curEnemy.enemy_health -= playerDamage
            else curEnemy.enemy_health = 0

            // Record damage statistics
            curPlayer.num_damagedealt += playerDamage
            curPlayer.num_damagetaken += enemyDamage

            playerRepository.updatePet(curPet)
            playerRepository.updatePlayer(curPlayer)
            enemyRepository.updateEnemy(curEnemy)

            checkGoals(curPlayer)
        }
    }

    // Resets player / enemy health and reduces pet's statuses.
    fun onPlayerDefeat() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername(PLAYER_USERNAME)
            val curPet = playerRepository.getPetById(CURRENT_PET_ID)

            // Change pet status
            curPet.pet_health = curPet.petMaxHp
            if(curPet.hunger_level > 25) curPet.hunger_level = 25
            if(curPet.thirst_level > 25) curPet.thirst_level = 25
            if(curPet.happiness_level > 25) curPet.happiness_level = 25
            if(curPet.fitness_level > 25) curPet.fitness_level = 25

            // Record lost fight
            curPlayer.num_fightslost += 1

            // Reset the enemy
            setEnemy(curPlayer.arena_level)

            playerRepository.updatePet(curPet)
            playerRepository.updatePlayer(curPlayer)

            checkGoals(curPlayer)
        }
    }

    // Rewards the player for defeating the opponent and changes to the next enemy.
    // The next enemy is determined by the player's arena level.
    fun onEnemyDefeat() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername(PLAYER_USERNAME)
            val curPet = playerRepository.getPetById(CURRENT_PET_ID)
            val curEnemy = enemyRepository.getEnemyById(CURRENT_ENEMY_ID)

            // Reward the player
            val moneyEarned = 100 * curEnemy.enemy_level
            curPlayer.money_amount += moneyEarned
            curPlayer.num_arenacoinsearned += moneyEarned
            curPlayer.num_fightswon += 1
            curPlayer.arena_level += 1
            if(curPlayer.arena_level > curPlayer.max_arena_level) curPlayer.max_arena_level = curPlayer.arena_level
            curPet.pet_health = curPet.petMaxHp

            // Change the enemy to new enemy
            setEnemy(curPlayer.arena_level)

            playerRepository.updatePet(curPet)
            playerRepository.updatePlayer(curPlayer)

            checkGoals(curPlayer)
        }
    }

    // Handles when use bandages button is pressed in items listview.
    fun onUseBandagesBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername(PLAYER_USERNAME)
            val curPet = playerRepository.getPetById(CURRENT_PET_ID)

            if(curPlayer.num_bandages > 0) {
                curPlayer.num_bandages -= 1
                curPlayer.num_bandagesused += 1
                curPet.pet_health += (curPet.petMaxHp / 10)
                if(curPet.pet_health > curPet.petMaxHp) curPet.pet_health = curPet.petMaxHp
            }

            playerRepository.updatePlayer(curPlayer)
            playerRepository.updatePet(curPet)

            checkGoals(curPlayer)
        }
    }

    // Handles when use firstaid button is pressed in items listview.
    fun onUseFirstAidBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername(PLAYER_USERNAME)
            val curPet = playerRepository.getPetById(CURRENT_PET_ID)

            if(curPlayer.num_firstaid > 0) {
                curPlayer.num_firstaid -= 1
                curPlayer.num_firstaidused += 1
                curPet.pet_health += (curPet.petMaxHp / 2)
                if(curPet.pet_health > curPet.petMaxHp) curPet.pet_health = curPet.petMaxHp
            }

            playerRepository.updatePlayer(curPlayer)
            playerRepository.updatePet(curPet)

            checkGoals(curPlayer)
        }
    }

    // Handles when use iron paws button is pressed in items listview.
    fun onUseIronPawsBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername(PLAYER_USERNAME)
            val curEnemy = enemyRepository.getEnemyById(CURRENT_ENEMY_ID)

            // Note: (curPlayer.num_ironpaw > 0) is already checked for in the activity
            // Remove if it's too inefficient, currently left here for safety
            if(curPlayer.num_ironpaw > 0) {
                curPlayer.num_ironpaw -= 1
                curPlayer.num_ironpawsused += 1
                val damageDealt = curEnemy.enemy_maxhp / 4
                curEnemy.enemy_health -= damageDealt
                curPlayer.num_damagedealt += damageDealt
            }

            playerRepository.updatePlayer(curPlayer)
            enemyRepository.updateEnemy(curEnemy)

            checkGoals(curPlayer)
        }
    }
}