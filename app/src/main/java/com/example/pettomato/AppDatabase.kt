package com.example.pettomato

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pettomato.roomdaos.PetDao
import com.example.pettomato.roomentities.PetEntity

@Database(entities = [PetEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
}
