package com.example.pettomato.workers

import android.content.Context
import androidx.lifecycle.Observer
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.pettomato.AppDatabase
import com.example.pettomato.repositories.PlayerRepository
import com.example.pettomato.roomentities.PetEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PetStatusUpdateWorker(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            val petDao = AppDatabase.getDatabase(applicationContext).petDao()
            val playerRepository: PlayerRepository = PlayerRepository(petDao)

            updatePetStatus(playerRepository)
        }
        return Result.success()
    }

    // Decrements the currently selected pet's statuses to simulate the
    // pet getting hungry, thirsty, etc.
    private suspend fun updatePetStatus(playerRepository: PlayerRepository) {
        // First pet in list is always the current pet, just for testing and will change this later.
        var curPet = playerRepository.getPetById(1)

        // Just a flat 3/4 decrease for now, in the future this can be affected by upgrades.
        curPet.hunger_level = (curPet.hunger_level / 4) * 3
        curPet.thirst_level = (curPet.thirst_level / 4) * 3
        curPet.happiness_level = (curPet.happiness_level / 4) * 3
        curPet.fitness_level = (curPet.fitness_level / 4) * 3

        playerRepository.updatePet(curPet)
    }
}