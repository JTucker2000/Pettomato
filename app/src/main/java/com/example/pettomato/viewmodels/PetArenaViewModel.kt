package com.example.pettomato.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pettomato.AppDatabase
import com.example.pettomato.repositories.EnemyRepository
import com.example.pettomato.repositories.PlayerRepository
import com.example.pettomato.roomentities.EnemyEntity
import com.example.pettomato.roomentities.PetEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PetArenaViewModel(application: Application) : AndroidViewModel(application) {
    private val playerRepository: PlayerRepository
    private val enemyRepository: EnemyRepository
    val enemyLive: LiveData<EnemyEntity>
    val petListLive: LiveData<List<PetEntity>>

    init {
        val database = AppDatabase.getDatabase(application)
        val petDao = database.petDao()
        val playerDao = database.playerDao()
        val enemyDao = database.enemyDao()
        playerRepository = PlayerRepository(petDao, playerDao)
        enemyRepository = EnemyRepository(enemyDao, playerDao)
        enemyLive = enemyRepository.enemyLive
        petListLive = playerRepository.petListLive
    }

    fun addEnemy(enemyEntity: EnemyEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            enemyRepository.addEnemy(enemyEntity)
        }
    }

    fun onAttackBtnPress() {
        // Update pet and enemy data
    }
}