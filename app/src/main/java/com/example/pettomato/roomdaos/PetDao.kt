package com.example.pettomato.roomdaos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pettomato.roomentities.PetEntity

@Dao
interface PetDao {
    @Query("SELECT * FROM pets")
    fun getAll(): LiveData<List<PetEntity>>

    @Query("SELECT * FROM pets WHERE id=(:pet_id)")
    fun getById(pet_id: Int): LiveData<PetEntity>

    @Insert
    fun insert(vararg pets: PetEntity)

    @Update
    fun update(vararg pets: PetEntity)

    @Delete
    fun delete(vararg pets: PetEntity)
}