package com.example.pettomato.roomdaos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pettomato.roomentities.EnemyEntity

@Dao
interface EnemyDao {
    @Query("SELECT * FROM enemies")
    fun getAll(): List<EnemyEntity>

    @Query("SELECT * FROM enemies WHERE id=(:enemy_id)")
    fun getById(enemy_id: Int): EnemyEntity

    @Query("SELECT * FROM enemies")
    fun getAllLive(): LiveData<List<EnemyEntity>>

    @Query("SELECT * FROM enemies WHERE id=(:enemy_id)")
    fun getByIdLive(enemy_id: Int): LiveData<EnemyEntity>

    @Insert
    fun insert(vararg enemies: EnemyEntity)

    @Update
    fun update(vararg enemies: EnemyEntity)

    @Delete
    fun delete(vararg enemies: EnemyEntity)
}