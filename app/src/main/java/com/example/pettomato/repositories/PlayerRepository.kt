package com.example.pettomato.repositories

import androidx.lifecycle.LiveData
import com.example.pettomato.roomdaos.PetDao
import com.example.pettomato.roomentities.PetEntity

class PlayerRepository(private val petDao: PetDao) {
    val petList: LiveData<List<PetEntity>> = petDao.getAll()

    suspend fun addPet(petEntity: PetEntity){
        petDao.insert(petEntity)
    }
}