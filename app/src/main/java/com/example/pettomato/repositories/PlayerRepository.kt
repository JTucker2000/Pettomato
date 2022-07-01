package com.example.pettomato.repositories

import androidx.lifecycle.LiveData
import com.example.pettomato.roomdaos.PetDao
import com.example.pettomato.roomentities.PetEntity

class PlayerRepository(private val petDao: PetDao) {
    val petList: LiveData<List<PetEntity>> = petDao.getAllLive()

    suspend fun getPetById(pet_id: Int): PetEntity {
        return petDao.getById(pet_id)
    }

    suspend fun addPet(petEntity: PetEntity) {
        petDao.insert(petEntity)
    }

    suspend fun updatePet(petEntity: PetEntity) {
        petDao.update(petEntity)
    }
}