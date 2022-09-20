package com.example.pettomato.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pettomato.AppDatabase
import com.example.pettomato.CURRENT_PET_ID
import com.example.pettomato.repositories.PlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PetStatusUpdateWorker(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            val database = AppDatabase.getDatabase(applicationContext)
            val petDao = database.petDao()
            val playerDao = database.playerDao()
            val playerRepository = PlayerRepository(petDao, playerDao)

            updatePetStatus(playerRepository)
        }
        return Result.success()
    }

    // Decrements the currently selected pet's statuses to simulate the pet getting hungry, thirsty, etc.
    private suspend fun updatePetStatus(playerRepository: PlayerRepository) {
        var curPet = playerRepository.getPetById(CURRENT_PET_ID)

        curPet.hunger_level = (curPet.hunger_level / 4) * 3
        curPet.thirst_level = (curPet.thirst_level / 4) * 3
        curPet.happiness_level = (curPet.happiness_level / 4) * 3
        curPet.fitness_level = (curPet.fitness_level / 4) * 3

        playerRepository.updatePet(curPet)
    }
}