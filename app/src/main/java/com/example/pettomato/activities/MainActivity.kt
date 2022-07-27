package com.example.pettomato.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
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
    private val mainViewModel: MainViewModel by viewModels()

    // Constants
    private val TAG: String = "MainActivityTag"
    private val MENU_FADE_ANIMATION_DURATION: Long = 150
    private val PROGRESSBAR_ANIMATION_DURATION: Long = 300
    private val UPDATE_TEXT_FADE_DURATION: Long = 2000

    // View variables
    private lateinit var upgradesListView: ListView
    private lateinit var actionsListView: ListView
    private lateinit var hungerUpdateText: TextView
    private lateinit var thirstUpdateText: TextView
    private lateinit var happyUpdateText: TextView
    private lateinit var fitnessUpdateText: TextView
    private lateinit var moneyAmountText: TextView
    private lateinit var petImage: ImageView
    private lateinit var hungerProgressBar: ProgressBar
    private lateinit var thirstProgressBar: ProgressBar
    private lateinit var happinessProgressBar: ProgressBar
    private lateinit var fitnessProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up view variables
        upgradesListView = findViewById<ListView>(R.id.upgrades_listView)
        actionsListView = findViewById<ListView>(R.id.actions_listView)
        hungerUpdateText = findViewById<TextView>(R.id.hunger_update_text)
        thirstUpdateText = findViewById<TextView>(R.id.thirst_update_text)
        happyUpdateText = findViewById<TextView>(R.id.happy_update_text)
        fitnessUpdateText = findViewById<TextView>(R.id.fitness_update_text)
        moneyAmountText = findViewById<TextView>(R.id.money_amount_text)
        petImage = findViewById<ImageView>(R.id.pet_image)
        hungerProgressBar = findViewById<ProgressBar>(R.id.hunger_progressBar)
        thirstProgressBar = findViewById<ProgressBar>(R.id.thirst_progressBar)
        happinessProgressBar = findViewById<ProgressBar>(R.id.happy_progressBar)
        fitnessProgressBar = findViewById<ProgressBar>(R.id.fitness_progressBar)

        // Set up observer(s)
        // ---- FIRST RUN, FOR TESTING ONLY ----
        //mainViewModel.addPet(PetEntity(0, "Corgi", R.drawable.corgiface1, 1, 100, 100, 88, 55, 90, 80))
        //mainViewModel.addPlayer(PlayerEntity("Jtuck", 1000, 1))
        // ---- END FIRST RUN ----
        mainViewModel.petListLive.observe(this, Observer<List<PetEntity>>{ currentPetList ->
            updateFromPet(currentPetList[0])
        })
        mainViewModel.playerLive.observe(this, Observer<PlayerEntity>{ currentPlayer ->
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
        upgradesListView.adapter = UpgradesListViewAdapter(this)
        upgradesListView.visibility = View.INVISIBLE
        upgradesListView.isClickable = false

        actionsListView.adapter = ActionsListViewAdapter(this)
        actionsListView.visibility = View.INVISIBLE
        actionsListView.isClickable = false

        hungerUpdateText.visibility = View.INVISIBLE
        thirstUpdateText.visibility = View.INVISIBLE
        happyUpdateText.visibility = View.INVISIBLE
        fitnessUpdateText.visibility = View.INVISIBLE
    }

    private fun updateFromPet(pet: PetEntity) {
        // Pet image
        petImage.setImageResource(pet.image_id)

        // Status update texts
        animatePetStatusUpdateText(hungerUpdateText, pet.hunger_level - hungerProgressBar.progress)
        animatePetStatusUpdateText(thirstUpdateText, pet.thirst_level - thirstProgressBar.progress)
        animatePetStatusUpdateText(happyUpdateText, pet.happiness_level - happinessProgressBar.progress)
        animatePetStatusUpdateText(fitnessUpdateText, pet.fitness_level - fitnessProgressBar.progress)

        // Progress bars
        ObjectAnimator.ofInt(hungerProgressBar, "progress", pet.hunger_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        ObjectAnimator.ofInt(thirstProgressBar, "progress", pet.thirst_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        ObjectAnimator.ofInt(happinessProgressBar, "progress", pet.happiness_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        ObjectAnimator.ofInt(fitnessProgressBar, "progress", pet.fitness_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
    }

    // Animates the given textview to fade in and out while displaying a green or red
    // number based on the given change amount.
    // For example, if changeAmount = -10, the textview will fade in and out with a red "-10" text.
    private fun animatePetStatusUpdateText(curTextView: TextView, changeAmount: Int) {
        if(changeAmount == 0) return

        val generatedTextWithColor: List<String> = generatePetStatusUpdateText(changeAmount)
        curTextView.text = generatedTextWithColor[0]
        curTextView.setTextColor(Color.parseColor(generatedTextWithColor[1]))

        fadeInOutView(curTextView, UPDATE_TEXT_FADE_DURATION)
    }

    // Returns a list containing two string elements, the first being the update text
    // and the second being the color code.
    // For example, if changeAmount == 10, the function will return {"+10", "#00ff00"}
    private fun generatePetStatusUpdateText(changeAmount: Int): List<String> {
        return when {
            changeAmount > 0 -> listOf("+$changeAmount", "#00ff00")
            changeAmount < 0 -> listOf("$changeAmount", "#ff0000")
            else -> listOf("$changeAmount", "#ffff00")
        }
    }

    // Returns true if any of the views in the activity are currently visible, false otherwise.
    private fun checkViewsVisible(): Boolean {
        return upgradesListView.visibility == View.VISIBLE ||
                actionsListView.visibility == View.VISIBLE
    }

    // Animates the given view to fade in.
    private fun fadeInView(view: View, duration: Long) {
        view.apply {
            alpha = 0f
            visibility = View.VISIBLE

            animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null)
        }
    }

    // Animates the given view to fade out.
    private fun fadeOutView(view: View, duration: Long) {
        view.apply {
            animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        if(animation.isRunning) return
                        view.visibility = View.INVISIBLE
                    }
                })
        }
    }

    // Animates the given view to fade in and out.
    private fun fadeInOutView(view: View, duration: Long) {
        view.apply {
            alpha = 0f
            visibility = View.VISIBLE

            animate()
                .alpha(1f)
                .setDuration(duration/2)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        fadeOutView(view, duration/2)
                    }
                })
        }
    }

    fun onUpgradeBtnPress(view: View) {
        when (upgradesListView.visibility){
            View.INVISIBLE -> {
                if (checkViewsVisible()) return
                upgradesListView.isClickable = true
                fadeInView(upgradesListView, MENU_FADE_ANIMATION_DURATION)
                fadeOutView(petImage, MENU_FADE_ANIMATION_DURATION)
            }
            View.VISIBLE -> {
                upgradesListView.isClickable = false
                fadeOutView(upgradesListView, MENU_FADE_ANIMATION_DURATION)
                fadeInView(petImage, MENU_FADE_ANIMATION_DURATION)
            }
            else -> Log.e(TAG, "Error: onUpgradeBtnPress encountered unexpected visibility")
        }
    }

    fun onActionBtnPress(view: View) {
        when (actionsListView.visibility){
            View.INVISIBLE -> {
                if (checkViewsVisible()) return
                actionsListView.isClickable = true
                fadeInView(actionsListView, MENU_FADE_ANIMATION_DURATION)
                fadeOutView(petImage, MENU_FADE_ANIMATION_DURATION)
            }
            View.VISIBLE -> {
                actionsListView.isClickable = false
                fadeOutView(actionsListView, MENU_FADE_ANIMATION_DURATION)
                fadeInView(petImage, MENU_FADE_ANIMATION_DURATION)
            }
            else -> Log.e(TAG, "Error: onActionBtnPress encountered unexpected visibility")
        }
    }

    fun onActionListBtnPress(view: View) {
        when (actionsListView.getPositionForView(view)){
            0 -> mainViewModel.onFeedBtnPress()
            1 -> mainViewModel.onGiveWaterBtnPress()
            2 -> mainViewModel.onPetBtnPress()
            3 -> mainViewModel.onWalkBtnPress()
            4 -> mainViewModel.onWorkBtnPress()
            else -> Log.e(TAG, "Error: onActionListBtnPress encountered unexpected position")
        }
    }

    fun onArenaBtnPress(view: View) = startActivity(Intent(this, PetArenaActivity::class.java))
}