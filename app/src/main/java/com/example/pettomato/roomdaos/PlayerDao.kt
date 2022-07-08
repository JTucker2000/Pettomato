package com.example.pettomato.roomdaos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pettomato.roomentities.PlayerEntity

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players")
    fun getAll(): List<PlayerEntity>

    @Query("SELECT * FROM players WHERE username=(:username)")
    fun getByUsername(username: String): PlayerEntity

    @Query("SELECT * FROM players")
    fun getAllLive(): LiveData<List<PlayerEntity>>

    @Query("SELECT * FROM players WHERE username=(:username)")
    fun getByUsernameLive(username: String): LiveData<PlayerEntity>

    @Insert
    fun insert(vararg players: PlayerEntity)

    @Update
    fun update(vararg players: PlayerEntity)

    @Delete
    fun delete(vararg players: PlayerEntity)
}