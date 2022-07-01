package com.example.pettomato.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.pettomato.viewmodels.MainViewModel
import com.example.pettomato.R
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.viewadapters.UpgradesListViewAdapter
import com.example.pettomato.workers.PetStatusUpdateWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivityTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the viewModel and set up observer(s) for UI.
        val mainModel: MainViewModel by viewModels()
        mainModel.petList.observe(this, Observer<List<PetEntity>>{ currentPetList ->
            updateFromPet(currentPetList[0])
        })
        val moneyAmount = 0 // placeholder
        val moneyAmountText = findViewById<TextView>(R.id.money_amount_text)
        moneyAmountText.text = moneyAmount.toString()

        // Set up worker(s)
        val updatePetStatusWorkRequest = PeriodicWorkRequestBuilder<PetStatusUpdateWorker>(15, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork (
                "petStatusUpdate",
                ExistingPeriodicWorkPolicy.KEEP,
                updatePetStatusWorkRequest
            )

        // Set up upgrades list view
        val upgradesListView = findViewById<ListView>(R.id.upgrades_listView)
        upgradesListView.adapter = UpgradesListViewAdapter(this)
        upgradesListView.visibility = View.INVISIBLE
    }

    private fun updateFromPet(pet: PetEntity) {
        // Set up pet image
        val petImage = findViewById<ImageView>(R.id.pet_image)
        petImage.setImageResource(pet.image_id)

        // Set up progress bars
        val hungerProgressBar = findViewById<ProgressBar>(R.id.hunger_progressBar)
        val thirstProgressBar = findViewById<ProgressBar>(R.id.thirst_progressBar)
        val happinessProgressBar = findViewById<ProgressBar>(R.id.happy_progressBar)
        val fitnessProgressBar = findViewById<ProgressBar>(R.id.fitness_progressBar)
        hungerProgressBar.progress = pet.hunger_level
        thirstProgressBar.progress = pet.thirst_level
        happinessProgressBar.progress = pet.happiness_level
        fitnessProgressBar.progress = pet.fitness_level
    }

    fun onUpgradeBtnPress(view: View) {
        val upgradesListView = findViewById<ListView>(R.id.upgrades_listView)
        val petImage = findViewById<ImageView>(R.id.pet_image)

        when (upgradesListView.visibility){
            View.INVISIBLE -> {
                upgradesListView.isClickable = true
                upgradesListView.visibility = View.VISIBLE
                petImage.visibility = View.INVISIBLE
            }
            View.VISIBLE -> {
                upgradesListView.isClickable = false
                upgradesListView.visibility = View.INVISIBLE
                petImage.visibility = View.VISIBLE
            }
            else -> Log.e(TAG, "Error: onUpgradeBtnPress encountered unexpected visibility")
        }
    }
}