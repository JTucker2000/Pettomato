package com.example.pettomato.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.pettomato.viewmodels.MainViewModel
import com.example.pettomato.R
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.roomentities.PlayerEntity
import com.example.pettomato.viewadapters.ActionsListViewAdapter
import com.example.pettomato.viewadapters.UpgradesListViewAdapter
import com.example.pettomato.workers.PetStatusUpdateWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivityTag"
    private val FADE_ANIMATION_DURATION: Long = 150
    private val PROGRESSBAR_ANIMATION_DURATION: Long = 300
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up observer(s)
        // ---- FIRST RUN, FOR TESTING ONLY ----
        //mainViewModel.addPet(PetEntity(0, "Corgi", R.drawable.corgiface1, 1, 88, 55, 90, 80))
        //mainViewModel.addPlayer(PlayerEntity("Jtuck", 1000))
        // ---- END FIRST RUN ----
        mainViewModel.petListLive.observe(this, Observer<List<PetEntity>>{ currentPetList ->
            updateFromPet(currentPetList[0])
        })
        mainViewModel.playerLive.observe(this, Observer<PlayerEntity>{ currentPlayer ->
            val moneyAmountText = findViewById<TextView>(R.id.money_amount_text)
            moneyAmountText.text = currentPlayer.money_amount.toString()
        })

        // Set up worker(s)
        val updatePetStatusWorkRequest = PeriodicWorkRequestBuilder<PetStatusUpdateWorker>(15, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork (
                "petStatusUpdate",
                ExistingPeriodicWorkPolicy.KEEP,
                updatePetStatusWorkRequest
            )

        // Set up view(s)
        val upgradesListView = findViewById<ListView>(R.id.upgrades_listView)
        upgradesListView.adapter = UpgradesListViewAdapter(this)
        upgradesListView.visibility = View.INVISIBLE
        upgradesListView.isClickable = false

        val actionsListView = findViewById<ListView>(R.id.actions_listView)
        actionsListView.adapter = ActionsListViewAdapter(this)
        actionsListView.visibility = View.INVISIBLE
        actionsListView.isClickable = false

        val arenaFragmentContainer = findViewById<FragmentContainerView>(R.id.arena_fragment_container)
        arenaFragmentContainer.visibility = View.INVISIBLE
        arenaFragmentContainer.isClickable = false
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
        ObjectAnimator.ofInt(hungerProgressBar, "progress", pet.hunger_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        ObjectAnimator.ofInt(thirstProgressBar, "progress", pet.thirst_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        ObjectAnimator.ofInt(happinessProgressBar, "progress", pet.happiness_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        ObjectAnimator.ofInt(fitnessProgressBar, "progress", pet.fitness_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
    }

    // Returns true if any of the views in the activity are currently visible, false otherwise.
    private fun checkViewsVisible(): Boolean {
        val upgradesListView = findViewById<ListView>(R.id.upgrades_listView)
        val actionsListView = findViewById<ListView>(R.id.actions_listView)
        val arenaFragmentContainer = findViewById<FragmentContainerView>(R.id.arena_fragment_container)

        return upgradesListView.visibility == View.VISIBLE ||
                actionsListView.visibility == View.VISIBLE ||
                arenaFragmentContainer.visibility == View.VISIBLE
    }

    // Animates the given view to fade in.
    private fun fadeInView(view: View) {
        view.apply {
            alpha = 0f
            visibility = View.VISIBLE

            animate()
                .alpha(1f)
                .setDuration(FADE_ANIMATION_DURATION)
                .setListener(null)
        }
    }

    // Animates the given view to fade out.
    private fun fadeOutView(view: View) {
        view.apply {
            animate()
                .alpha(0f)
                .setDuration(FADE_ANIMATION_DURATION)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = View.INVISIBLE
                    }
                })
        }
    }

    fun onUpgradeBtnPress(view: View) {
        val petImage = findViewById<ImageView>(R.id.pet_image)
        val upgradesListView = findViewById<ListView>(R.id.upgrades_listView)

        when (upgradesListView.visibility){
            View.INVISIBLE -> {
                if (checkViewsVisible()) return
                upgradesListView.isClickable = true
                fadeInView(upgradesListView)
                fadeOutView(petImage)
            }
            View.VISIBLE -> {
                upgradesListView.isClickable = false
                fadeOutView(upgradesListView)
                fadeInView(petImage)
            }
            else -> Log.e(TAG, "Error: onUpgradeBtnPress encountered unexpected visibility")
        }
    }

    fun onActionBtnPress(view: View) {
        val petImage = findViewById<ImageView>(R.id.pet_image)
        val actionsListView = findViewById<ListView>(R.id.actions_listView)

        when (actionsListView.visibility){
            View.INVISIBLE -> {
                if (checkViewsVisible()) return
                actionsListView.isClickable = true
                fadeInView(actionsListView)
                fadeOutView(petImage)
            }
            View.VISIBLE -> {
                actionsListView.isClickable = false
                fadeOutView(actionsListView)
                fadeInView(petImage)
            }
            else -> Log.e(TAG, "Error: onActionBtnPress encountered unexpected visibility")
        }
    }

    fun onActionListBtnPress(view: View) {
        val actionsListView = findViewById<ListView>(R.id.actions_listView)
        when (actionsListView.getPositionForView(view)){
            0 -> mainViewModel.onFeedBtnPress()
            1 -> mainViewModel.onGiveWaterBtnPress()
            2 -> mainViewModel.onPetBtnPress()
            3 -> mainViewModel.onWalkBtnPress()
            4 -> mainViewModel.onWorkBtnPress()
            else -> Log.e(TAG, "Error: onActionListBtnPress encountered unexpected position")
        }
    }

    fun onArenaBtnPress(view: View) {
        val petImage = findViewById<ImageView>(R.id.pet_image)
        val arenaFragmentContainer = findViewById<FragmentContainerView>(R.id.arena_fragment_container)

        when (arenaFragmentContainer.visibility){
            View.INVISIBLE -> {
                if (checkViewsVisible()) return
                arenaFragmentContainer.isClickable = true
                fadeInView(arenaFragmentContainer)
                fadeOutView(petImage)
            }
            View.VISIBLE -> {
                arenaFragmentContainer.isClickable = false
                fadeOutView(arenaFragmentContainer)
                fadeInView(petImage)
            }
            else -> Log.e(TAG, "Error: onArenaBtnPress encountered unexpected visibility")
        }
    }
}