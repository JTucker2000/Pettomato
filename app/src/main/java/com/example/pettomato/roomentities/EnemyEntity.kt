package com.example.pettomato.roomentities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enemies")
data class EnemyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val enemy_name: String,
    var image_id: Int,
    var enemy_level: Int,
    var enemy_health: Int,
    val enemy_maxhp: Int
)