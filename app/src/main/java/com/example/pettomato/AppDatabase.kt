package com.example.pettomato

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pettomato.roomdaos.EnemyDao
import com.example.pettomato.roomdaos.PetDao
import com.example.pettomato.roomdaos.PlayerDao
import com.example.pettomato.roomentities.EnemyEntity
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.roomentities.PlayerEntity

@Database(entities = [PetEntity::class, PlayerEntity::class, EnemyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun playerDao(): PlayerDao
    abstract fun enemyDao(): EnemyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "pettomatodb"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
