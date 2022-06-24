package com.example.pettomato.roomentities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "pets")
data class PetEntity(
    @PrimaryKey val id: Int,
    var pet_name: String,
    var image_id: Int,
    var pet_level: Int,
    var hunger_level: Int,
    var thirst_level: Int,
    var happiness_level: Int,
    var fitness_level: Int
)