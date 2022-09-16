package com.example.pettomato.roomdaos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pettomato.roomentities.PetEntity

@Dao
interface PetDao {
    @Query("SELECT * FROM pets")
    fun getAll(): List<PetEntity>

    @Query("SELECT * FROM pets WHERE id=(:pet_id)")
    fun getById(pet_id: Int): PetEntity

    @Query("SELECT * FROM pets")
    fun getAllLive(): LiveData<List<PetEntity>>

    @Query("SELECT * FROM pets WHERE id=(:pet_id)")
    fun getByIdLive(pet_id: Int): LiveData<PetEntity>

    @Insert
    fun insert(vararg pets: PetEntity)

    @Update
    fun update(vararg pets: PetEntity)

    @Query("UPDATE pets SET pet_name=(:petName), normal_image_id=(:normalImageId), happy_image_id=(:happyImageId)," +
            "sad_image_id=(:sadImageId), angry_image_id=(:angryImageId), pet_level=(:petLevel), pet_health=(:petHealth)," +
            "hunger_level=(:hungerLevel), thirst_level=(:thirstLevel), happiness_level=(:happinessLevel), fitness_level=(:fitnessLevel) " +
            "WHERE id=(:id)")
    fun updateExplicit(id: Int, petName: String, normalImageId: Int, happyImageId: Int,
                       sadImageId: Int, angryImageId: Int, petLevel: Int, petHealth: Int,
                       hungerLevel: Int, thirstLevel: Int, happinessLevel: Int, fitnessLevel: Int)

    @Delete
    fun delete(vararg pets: PetEntity)
}