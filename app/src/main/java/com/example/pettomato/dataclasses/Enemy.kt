package com.example.pettomato.dataclasses

data class Enemy(
    var enemy_name: String,
    var image_id: Int,
    var enemy_level: Int,
    var enemy_health: Int,
    val enemy_maxhp: Int
)