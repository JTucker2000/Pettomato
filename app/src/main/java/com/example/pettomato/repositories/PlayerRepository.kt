package com.example.pettomato.repositories

import com.example.pettomato.R
import com.example.pettomato.dataclasses.Pet

class PlayerRepository {
    private val petList: MutableList<Pet> = initPetList()
    private var moneyAmount: Int = initMoneyAmount()

    private fun initPetList(): MutableList<Pet> {
        // Placeholder
        return mutableListOf(Pet ("Corgi", R.drawable.corgiface1,1,95, 80, 100, 50))
    }

    private fun initMoneyAmount(): Int {
        // Placeholder
        return 0
    }

    fun getCurrentPet(): Pet = petList[0]
    fun getMoneyAmount(): Int = moneyAmount
}