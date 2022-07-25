package com.example.pettomato.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pettomato.AppDatabase
import com.example.pettomato.R
import com.example.pettomato.dataclasses.Enemy
import com.example.pettomato.repositories.PlayerRepository
import com.example.pettomato.roomentities.PetEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PetArenaViewModel(application: Application) : AndroidViewModel(application) {
    private val playerRepository: PlayerRepository
    private var _enemyLive: MutableLiveData<Enemy> = MutableLiveData()
        val enemyLive: LiveData<Enemy>
            get() = _enemyLive
    val petListLive: LiveData<List<PetEntity>>

    init {
        val database = AppDatabase.getDatabase(application)
        val petDao = database.petDao()
        val playerDao = database.playerDao()
        playerRepository = PlayerRepository(petDao, playerDao)
        petListLive = playerRepository.petListLive

        // Set the starting enemy based on players arena level
        viewModelScope.launch(Dispatchers.IO) {
            val curPlayer = playerRepository.getPlayerByUsername("Jtuck")
            when (curPlayer.arena_level){
                1 -> _enemyLive.value = Enemy("angrycorgi", R.drawable.corgiface1, 1, 10, 10)
                2 -> _enemyLive.value = Enemy("angrygershepard", R.drawable.germanshepard1, 10, 100, 100)
                else -> _enemyLive.value = Enemy("error", R.drawable.testpet1, 0, 0, 0)
            }
        }
    }
}