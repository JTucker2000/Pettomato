package com.example.pettomato.activities

import android.animation.ObjectAnimator
import android.content.Intent
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
import com.example.pettomato.functions.animateStatusUpdateText
import com.example.pettomato.functions.fadeInView
import com.example.pettomato.functions.fadeOutView
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.roomentities.PlayerEntity
import com.example.pettomato.viewadapters.ActionsListViewAdapter
import com.example.pettomato.viewadapters.ShopListViewAdapter
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
    private lateinit var shopListView: ListView
    private lateinit var actionsListView: ListView
    private lateinit var hungerUpdateText: TextView
    private lateinit var thirstUpdateText: TextView
    private lateinit var happyUpdateText: TextView
    private lateinit var fitnessUpdateText: TextView
    private lateinit var moneyUpdateText: TextView
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
        shopListView = findViewById<ListView>(R.id.shop_listView)
        actionsListView = findViewById<ListView>(R.id.actions_listView)
        hungerUpdateText = findViewById<TextView>(R.id.hunger_update_text)
        thirstUpdateText = findViewById<TextView>(R.id.thirst_update_text)
        happyUpdateText = findViewById<TextView>(R.id.happy_update_text)
        fitnessUpdateText = findViewById<TextView>(R.id.fitness_update_text)
        moneyUpdateText = findViewById<TextView>(R.id.money_update_text)
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
            updateUIFromPet(currentPetList[0])
        })
        mainViewModel.playerLive.observe(this, Observer<PlayerEntity>{ currentPlayer ->
            updateUIFromPlayer(currentPlayer)
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
        shopListView.adapter = ShopListViewAdapter(this)
        shopListView.visibility = View.INVISIBLE
        shopListView.isClickable = false

        actionsListView.adapter = ActionsListViewAdapter(this)
        actionsListView.visibility = View.INVISIBLE
        actionsListView.isClickable = false

        hungerUpdateText.visibility = View.INVISIBLE
        thirstUpdateText.visibility = View.INVISIBLE
        happyUpdateText.visibility = View.INVISIBLE
        fitnessUpdateText.visibility = View.INVISIBLE
        moneyUpdateText.visibility = View.INVISIBLE
    }

    private fun updateUIFromPet(pet: PetEntity) {
        // Pet image
        petImage.setImageResource(pet.image_id)

        // Status update texts
        animateStatusUpdateText(hungerUpdateText, pet.hunger_level - hungerProgressBar.progress, UPDATE_TEXT_FADE_DURATION)
        animateStatusUpdateText(thirstUpdateText, pet.thirst_level - thirstProgressBar.progress, UPDATE_TEXT_FADE_DURATION)
        animateStatusUpdateText(happyUpdateText, pet.happiness_level - happinessProgressBar.progress, UPDATE_TEXT_FADE_DURATION)
        animateStatusUpdateText(fitnessUpdateText, pet.fitness_level - fitnessProgressBar.progress, UPDATE_TEXT_FADE_DURATION)

        // Progress bars
        ObjectAnimator.ofInt(hungerProgressBar, "progress", pet.hunger_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        ObjectAnimator.ofInt(thirstProgressBar, "progress", pet.thirst_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        ObjectAnimator.ofInt(happinessProgressBar, "progress", pet.happiness_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        ObjectAnimator.ofInt(fitnessProgressBar, "progress", pet.fitness_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
    }

    private fun updateUIFromPlayer(player: PlayerEntity) {
        // Money update text
        animateStatusUpdateText(moneyUpdateText, player.money_amount - moneyAmountText.text.toString().toInt(), UPDATE_TEXT_FADE_DURATION)

        // Money amount text
        moneyAmountText.text = player.money_amount.toString()
    }

    // Returns true if any of the views in the activity are currently visible, false otherwise.
    private fun checkViewsVisible(): Boolean {
        return shopListView.visibility == View.VISIBLE ||
                actionsListView.visibility == View.VISIBLE
    }

    fun onShopBtnPress(view: View) {
        when (shopListView.visibility){
            View.INVISIBLE -> {
                if (checkViewsVisible()) return
                shopListView.isClickable = true
                fadeInView(shopListView, MENU_FADE_ANIMATION_DURATION)
                fadeOutView(petImage, MENU_FADE_ANIMATION_DURATION)
            }
            View.VISIBLE -> {
                shopListView.isClickable = false
                fadeOutView(shopListView, MENU_FADE_ANIMATION_DURATION)
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

    fun onShopListBtnPress(view: View) {
        // Do stuff based on what item was clicked in the shop
    }

    fun onArenaBtnPress(view: View) = startActivity(Intent(this, PetArenaActivity::class.java))
}