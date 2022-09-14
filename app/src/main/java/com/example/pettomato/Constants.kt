package com.example.pettomato

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

// The ID of the currently selected pet/enemy.
val CURRENT_PET_ID: Int = 1
val CURRENT_ENEMY_ID: Int = 1

// Misc. constants
val PLAYER_USERNAME: String = "Jtuck"
val PREVIOUS_VAL_UNINITIALIZED: Int = -1
val IMAGE_ID_UNINITIALIZED: Int = 0