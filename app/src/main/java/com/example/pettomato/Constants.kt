package com.example.pettomato

import com.example.pettomato.roomentities.EnemyEntity
import com.example.pettomato.roomentities.PetEntity

// This file contains all non-local constants used in the Kotlin code.

// Animation constants
val MENU_FADE_ANIMATION_DURATION: Long = 150
val PROGRESSBAR_ANIMATION_DURATION: Long = 300
val UPDATE_TEXT_FADE_DURATION: Long = 2000

// Item constants
val ITEM_OPTIONS: Array<String> = arrayOf("Bandage", "First-aid Kit", "Iron Paws")
val BANDAGE_PRICE: Int = 80
val FIRSTAID_PRICE: Int = 200
val IRONPAW_PRICE: Int = 500
val ITEM_PRICES: Array<String> = arrayOf("-$BANDAGE_PRICE Coins", "-$FIRSTAID_PRICE Coins", "-$IRONPAW_PRICE Coins")

// The ID of the currently selected pet/enemy.
val CURRENT_PET_ID: Int = 1
val CURRENT_ENEMY_ID: Int = 1

// Enemy list constant
// Each one of these is a level for the player to complete in the pet arena.
val ENEMY_LIST: Array<EnemyEntity> = arrayOf(
    EnemyEntity(CURRENT_ENEMY_ID, "Angry Mouse", R.drawable.angrymouse1, 1, 10, 10),
    EnemyEntity(CURRENT_ENEMY_ID, "Bored Doggo", R.drawable.normaldog1, 2, 15, 15),
    EnemyEntity(CURRENT_ENEMY_ID, "Injured Cat", R.drawable.sadcat2, 5, 15, 40),
    EnemyEntity(CURRENT_ENEMY_ID, "Happy Husky", R.drawable.happydog2, 3, 20, 20),
    EnemyEntity(CURRENT_ENEMY_ID, "Angry Lab Mouse", R.drawable.angrymouse2, 4, 25, 25),
    EnemyEntity(CURRENT_ENEMY_ID, "Indifferent Cat", R.drawable.normalcat1, 4, 35, 35),
    EnemyEntity(CURRENT_ENEMY_ID, "Angry Shiba", R.drawable.angrydog3, 6, 40, 40),
    EnemyEntity(CURRENT_ENEMY_ID, "Mr.Whiskers", R.drawable.happycat2, 7, 50, 50),
    EnemyEntity(CURRENT_ENEMY_ID, "Elated Lab Mouse", R.drawable.happymouse2, 8, 50, 50),
    EnemyEntity(CURRENT_ENEMY_ID, "Whining Shiba", R.drawable.saddog3, 10, 65, 65),
    EnemyEntity(CURRENT_ENEMY_ID, "Fat Cat", R.drawable.happycat1, 10, 80, 80),
    EnemyEntity(CURRENT_ENEMY_ID, "Hungry Mouse", R.drawable.normalmouse1, 12, 70, 70),
    EnemyEntity(CURRENT_ENEMY_ID, "Well Fed Husky", R.drawable.happydog2, 13, 100, 100),
    EnemyEntity(CURRENT_ENEMY_ID, "THICC Cat", R.drawable.happycat2, 14, 110, 110),
    EnemyEntity(CURRENT_ENEMY_ID, "Forgotten Lab Mouse", R.drawable.sadmouse2, 15, 100, 100),
    EnemyEntity(CURRENT_ENEMY_ID, "Silly Shiba", R.drawable.normaldog3, 17, 120, 120),
    EnemyEntity(CURRENT_ENEMY_ID, "Bored Cat", R.drawable.normalcat3, 18, 125, 125),
    EnemyEntity(CURRENT_ENEMY_ID, "Sad Wolf", R.drawable.saddog1, 20, 140, 140),
    EnemyEntity(CURRENT_ENEMY_ID, "HUSKY Husky", R.drawable.normaldog2, 21, 150, 150),
    EnemyEntity(CURRENT_ENEMY_ID, "Injured Shiba", R.drawable.saddog3, 23, 100, 150),
    EnemyEntity(CURRENT_ENEMY_ID, "Obese Feline", R.drawable.happycat3, 24, 175, 175),
    EnemyEntity(CURRENT_ENEMY_ID, "Pizza Addicted Mouse", R.drawable.happymouse1, 25, 175, 175),
    EnemyEntity(CURRENT_ENEMY_ID, "Spike the Dog", R.drawable.normaldog1, 25, 180, 180),
    EnemyEntity(CURRENT_ENEMY_ID, "Upset Cat", R.drawable.sadcat2, 26, 180, 180),
    EnemyEntity(CURRENT_ENEMY_ID, "Doge", R.drawable.normaldog3, 27, 195, 195)
)

// Pet shop constants
val PURCHASEABLE_PETS: Array<PetEntity> = arrayOf(
    PetEntity(0, "Husky", R.drawable.normaldog2, R.drawable.happydog2, R.drawable.saddog2, R.drawable.angrydog2, 1, 20, 100, 100, 100, 100),
    PetEntity(0, "Lab rat", R.drawable.normalmouse2, R.drawable.happymouse2, R.drawable.sadmouse2, R.drawable.angrymouse2, 1, 20, 100, 100, 100, 100),
    PetEntity(0, "Grey Cat", R.drawable.normalcat2, R.drawable.happycat2, R.drawable.sadcat2, R.drawable.angrycat2, 1, 20, 100, 100, 100, 100),
    PetEntity(0, "Shiba", R.drawable.normaldog3, R.drawable.happydog3, R.drawable.saddog3, R.drawable.angrydog3, 1, 20, 100, 100, 100, 100),
    PetEntity(0, "Pitbull", R.drawable.normaldog1, R.drawable.happydog1, R.drawable.saddog1, R.drawable.angrydog1, 1, 20, 100, 100, 100, 100)
)
val PET_PRICES: Array<Int> = arrayOf(1000, 500, 1500, 3000, 2000)

// Action constants
val ACTION_OPTIONS: Array<String> = arrayOf("Feed", "Give water", "Pet", "Go for a walk")
val FEED_PRICE: Int = 50
val GIVEWATER_PRICE: Int = 30
val PETACTION_PRICE: Int = 0 // Unused
val WALK_PRICE: Int = 0 // Unused
val ACTION_PRICES: Array<String> = arrayOf("-$FEED_PRICE Coins", "-$GIVEWATER_PRICE Coins", "Free", "Free")

// Uninitialized value constants
val PREVIOUS_VAL_UNINITIALIZED: Int = -1
val IMAGE_ID_UNINITIALIZED: Int = 0
val NUM_OWNED_VAL_UNINITIALIZED: Int = -1

// Misc. constants
val PLAYER_USERNAME: String = "Jtuck"
