package com.example.pettomato.roomdaos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.pettomato.roomentities.PetEntity

@Dao
interface PetDao {
    @Insert
    fun insert(vararg pets: PetEntity)

    @Update
    fun update(vararg pets: PetEntity)

    @Delete
    fun delete(vararg pets: PetEntity)
}