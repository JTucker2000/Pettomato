package com.example.pettomato.roomentities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "players")
data class PlayerEntity(
    @PrimaryKey val username: String,
    var money_amount: Int
)