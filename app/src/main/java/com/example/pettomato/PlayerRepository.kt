package com.example.pettomato

import android.content.Context
import androidx.lifecycle.MutableLiveData
import java.io.File

class PlayerRepository {
    private val petList: MutableList<Pet> = getPetList()
    private var moneyAmount: Int = getMoneyAmount()

    private fun getPetList(): MutableList<Pet> {
        // Placeholder
        return mutableListOf(Pet ("Corgi", R.drawable.corgiface1,1,95, 80, 100, 50))
    }

    private fun getMoneyAmount(): Int {
        // Placeholder
        return 0
    }

    fun getCurrentPet(): Pet {
        // Placeholder
        return petList[0]
    }
}