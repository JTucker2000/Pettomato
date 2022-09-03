package com.example.pettomato.repositories

import androidx.lifecycle.LiveData
import com.example.pettomato.CURRENT_PET_ID
import com.example.pettomato.PLAYER_USERNAME
import com.example.pettomato.roomdaos.PetDao
import com.example.pettomato.roomdaos.PlayerDao
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.roomentities.PlayerEntity

class PlayerRepository(private val petDao: PetDao, private val playerDao: PlayerDao) {
    val petLive: LiveData<PetEntity> = petDao.getByIdLive(CURRENT_PET_ID)
    val playerLive: LiveData<PlayerEntity> = playerDao.getByUsernameLive(PLAYER_USERNAME)

    suspend fun getPetById(pet_id: Int): PetEntity {
        return petDao.getById(pet_id)
    }

    suspend fun getPlayerByUsername(username: String): PlayerEntity {
        return playerDao.getByUsername(username)
    }

    suspend fun addPet(petEntity: PetEntity) {
        petDao.insert(petEntity)
    }

    suspend fun addPlayer(playerEntity: PlayerEntity) {
        playerDao.insert(playerEntity)
    }

    suspend fun updatePet(petEntity: PetEntity) {
        petDao.update(petEntity)
    }

    suspend fun updatePlayer(playerEntity: PlayerEntity) {
        playerDao.update(playerEntity)
    }
}