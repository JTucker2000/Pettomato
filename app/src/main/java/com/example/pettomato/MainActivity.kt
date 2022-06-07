package com.example.pettomato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivityTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create test pet
        var testPet = Pet("Corgi", R.drawable.corgiface1,1,95, 80, 100, 50)

        // Set up pet image
        val petImage = findViewById<ImageView>(R.id.pet_image)
        petImage.setImageResource(testPet.image_id)

        // Set up upgrades list view
        val upgradesListView = findViewById<ListView>(R.id.upgrades_listView)
        upgradesListView.adapter = UpgradesListViewAdapter(this)
        upgradesListView.visibility = View.INVISIBLE

        // Set up progress bars
        val hungerProgressBar = findViewById<ProgressBar>(R.id.hunger_progressBar)
        val thirstProgressBar = findViewById<ProgressBar>(R.id.thirst_progressBar)
        val happinessProgressBar = findViewById<ProgressBar>(R.id.happy_progressBar)
        val fitnessProgressBar = findViewById<ProgressBar>(R.id.fitness_progressBar)
        hungerProgressBar.progress = testPet.hunger_level
        thirstProgressBar.progress = testPet.thirst_level
        happinessProgressBar.progress = testPet.happiness_level
        fitnessProgressBar.progress = testPet.fitness_level
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