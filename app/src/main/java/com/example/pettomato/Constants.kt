package com.example.pettomato

// This file contains all non-local constants used in the Kotlin code.

// Animation constants
val MENU_FADE_ANIMATION_DURATION: Long = 150
val PROGRESSBAR_ANIMATION_DURATION: Long = 300
val UPDATE_TEXT_FADE_DURATION: Long = 2000

// Item constants
val itemOptions: Array<String> = arrayOf("Bandage", "First-aid Kit", "Iron Paws")
val BANDAGE_PRICE: Int = 80
val FIRSTAID_PRICE: Int = 200
val IRONPAW_PRICE: Int = 500
val itemPrices: Array<String> = arrayOf("-$BANDAGE_PRICE Coins", "-$FIRSTAID_PRICE Coins", "-$IRONPAW_PRICE Coins")


// Action constants
val actionOptions: Array<String> = arrayOf("Feed", "Give water", "Pet", "Go for a walk")
val FEED_PRICE: Int = 50
val GIVEWATER_PRICE: Int = 30
val PETACTION_PRICE: Int = 0 // Unused
val WALK_PRICE: Int = 0 // Unused
val actionPrices: Array<String> = arrayOf("-$FEED_PRICE Coins", "-$GIVEWATER_PRICE Coins", "Free", "Free")

// The ID of the currently selected pet/enemy.
val CURRENT_PET_ID: Int = 1
val CURRENT_ENEMY_ID: Int = 1

// Misc. constants
val PLAYER_USERNAME: String = "Jtuck"
val PREVIOUS_VAL_UNINITIALIZED: Int = -1