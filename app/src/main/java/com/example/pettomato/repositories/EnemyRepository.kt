package com.example.pettomato.repositories

import androidx.lifecycle.LiveData
import com.example.pettomato.roomdaos.EnemyDao
import com.example.pettomato.roomdaos.PlayerDao
import com.example.pettomato.roomentities.EnemyEntity

class EnemyRepository(private val enemyDao: EnemyDao, private val playerDao: PlayerDao) {
    val enemyLive: LiveData<EnemyEntity> = enemyDao.getByIdLive(1)

    suspend fun getEnemyById(enemy_id: Int): EnemyEntity {
        return enemyDao.getById(enemy_id)
    }

    suspend fun addEnemy(enemyEntity: EnemyEntity) {
        enemyDao.insert(enemyEntity)
    }

    suspend fun updateEnemy(enemyEntity: EnemyEntity) {
        enemyDao.update(enemyEntity)
    }

    suspend fun getNumEnemies(): Int = enemyDao.getNumEnemies()
}