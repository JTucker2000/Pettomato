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
                // Each one of these is a level for the player to complete in the pet arena.
                1 -> enemyRepository.updateEnemy(EnemyEntity(1, "Angry Mouse", R.drawable.angrymouse1, 1, 10, 10))
                2 -> enemyRepository.updateEnemy(EnemyEntity(1, "Bored Doggo", R.drawable.normaldog1, 2, 15, 15))
                3 -> enemyRepository.updateEnemy(EnemyEntity(1, "Injured Cat", R.drawable.sadcat2, 5, 15, 40))
                4 -> enemyRepository.updateEnemy(EnemyEntity(1, "Happy Husky", R.drawable.happydog2, 3, 20, 20))
                5 -> enemyRepository.updateEnemy(EnemyEntity(1, "Angry Lab Mouse", R.drawable.angrymouse2, 4, 25, 25))
                6 -> enemyRepository.updateEnemy(EnemyEntity(1, "Indifferent Cat", R.drawable.normalcat1, 4, 35, 35))
                7 -> enemyRepository.updateEnemy(EnemyEntity(1, "Angry Shiba", R.drawable.angrydog3, 6, 40, 40))
                8 -> enemyRepository.updateEnemy(EnemyEntity(1, "Mr.Whiskers", R.drawable.happycat2, 7, 50, 50))
                9 -> enemyRepository.updateEnemy(EnemyEntity(1, "Elated Lab Mouse", R.drawable.happymouse2, 8, 50, 50))
                10 -> enemyRepository.updateEnemy(EnemyEntity(1, "Whining Shiba", R.drawable.saddog3, 10, 65, 65))
                11 -> enemyRepository.updateEnemy(EnemyEntity(1, "Fat Cat", R.drawable.happycat1, 10, 80, 80))
                12 -> enemyRepository.updateEnemy(EnemyEntity(1, "Hungry Mouse", R.drawable.normalmouse1, 12, 70, 70))
                13 -> enemyRepository.updateEnemy(EnemyEntity(1, "Well Fed Husky", R.drawable.happydog2, 13, 100, 100))
                14 -> enemyRepository.updateEnemy(EnemyEntity(1, "THICC Cat", R.drawable.happycat2, 14, 110, 110))
                15 -> enemyRepository.updateEnemy(EnemyEntity(1, "Forgotten Lab Mouse", R.drawable.sadmouse2, 15, 100, 100))
                16 -> enemyRepository.updateEnemy(EnemyEntity(1, "Silly Shiba", R.drawable.normaldog3, 17, 120, 120))
                17 -> enemyRepository.updateEnemy(EnemyEntity(1, "Bored Cat", R.drawable.normalcat3, 18, 125, 125))
                18 -> enemyRepository.updateEnemy(EnemyEntity(1, "Sad Wolf", R.drawable.saddog1, 20, 140, 140))
                19 -> enemyRepository.updateEnemy(EnemyEntity(1, "HUSKY Husky", R.drawable.normaldog2, 21, 150, 150))
                20 -> enemyRepository.updateEnemy(EnemyEntity(1, "Injured Shiba", R.drawable.saddog3, 23, 100, 150))
                else -> enemyRepository.updateEnemy(EnemyEntity(1, "Mega Angry Shiba", R.drawable.angrydog3, 100, 1000, 1000))
            }
        }
    }

    // Checks to see if any goals have been completed and updates player information if needed.
    // Should be used after any action that may cause a goal to be reached.
    private suspend fun checkGoals(curPlayer: PlayerEntity) {
        var totalReward: Int = 0

        // Check each goal and reward the player.
        if(curPlayer.toFightsWonGoal <= 0) {
            totalReward += curPlayer.fightsWonGoalReward
            curPlayer.fightswongoal *= 10
        }
        if(curPlayer.toFightsLostGoal <= 0) {
            totalReward += curPlayer.fightsLostGoalReward
            curPlayer.fightslostgoal *= 10
        }
        if(curPlayer.toCoinsEarnedGoal <= 0) {
            totalReward += curPlayer.coinsEarnedGoalReward
            curPlayer.coinsearnedgoal *= 100
        }
        if(curPlayer.toBandagesUsedGoal <= 0) {
            totalReward += curPlayer.bandagesUsedGoalReward
            curPlayer.bandagesusedgoal *= 10
        }
        if(curPlayer.toFirstAidUsedGoal <= 0) {
            totalReward += curPlayer.firstAidUsedGoalReward
            curPlayer.firstaidusedgoal *= 10
        }
        if(curPlayer.toIronPawsUsedGoal <= 0) {
            totalReward += curPlayer.ironPawsUsedGoalReward
            curPlayer.ironpawsusedgoal *= 10
        }
        if(curPlayer.toDamageDealtGoal <= 0) {
            totalReward += curPlayer.damageDealtGoalReward
            curPlayer.damagedealtgoal *= 10
        }
        if(curPlayer.toDamageTakenGoal <= 0) {
            totalReward += curPlayer.damageTakenGoalReward
            curPlayer.damagetakengoal *= 10
        }

        curPlayer.money_amount += totalReward

        playerRepository.updatePlayer(curPlayer)
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
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")
            val curEnemy = enemyRepository.getEnemyById(1)

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
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")
            val curPet = playerRepository.getPetById(1)

            // Change pet status
            curPet.pet_health = curPet.pet_maxhp
            if(curPet.hunger_level > 25) curPet.hunger_level = 25
            if(curPet.thirst_level > 25) curPet.thirst_level = 25
            if(curPet.happiness_level > 25) curPet.happiness_level = 25
            if(curPet.fitness_level > 25) curPet.fitness_level = 25

            // Record lost fight
            curPlayer.num_fightslost += 1
            if(curPlayer.arena_level > 1) curPlayer.arena_level--

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
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")
            val curPet = playerRepository.getPetById(1)
            val curEnemy = enemyRepository.getEnemyById(1)

            // Reward the player
            val moneyEarned = 100 * curEnemy.enemy_level
            curPlayer.money_amount += moneyEarned
            curPlayer.num_arenacoinsearned += moneyEarned
            curPlayer.num_fightswon += 1
            curPlayer.arena_level += 1
            curPet.pet_health = curPet.pet_maxhp

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
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")
            val curPet = playerRepository.getPetById(1)

            if(curPlayer.num_bandages > 0) {
                curPlayer.num_bandages -= 1
                curPlayer.num_bandagesused += 1
                curPet.pet_health += (curPet.pet_maxhp / 10)
                if(curPet.pet_health > curPet.pet_maxhp) curPet.pet_health = curPet.pet_maxhp
            }

            playerRepository.updatePlayer(curPlayer)
            playerRepository.updatePet(curPet)

            checkGoals(curPlayer)
        }
    }

    // Handles when use firstaid button is pressed in items listview.
    fun onUseFirstAidBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")
            val curPet = playerRepository.getPetById(1)

            if(curPlayer.num_firstaid > 0) {
                curPlayer.num_firstaid -= 1
                curPlayer.num_firstaidused += 1
                curPet.pet_health += (curPet.pet_maxhp / 2)
                if(curPet.pet_health > curPet.pet_maxhp) curPet.pet_health = curPet.pet_maxhp
            }

            playerRepository.updatePlayer(curPlayer)
            playerRepository.updatePet(curPet)

            checkGoals(curPlayer)
        }
    }

    // Handles when use iron paws button is pressed in items listview.
    fun onUseIronPawsBtnPress() {
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")
            val curEnemy = enemyRepository.getEnemyById(1)

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