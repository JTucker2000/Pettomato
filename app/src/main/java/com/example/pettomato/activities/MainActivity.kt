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
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.pettomato.*
import com.example.pettomato.fragments.PetDisplayFragment
import com.example.pettomato.viewmodels.MainViewModel
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

    // Local constants
    private val TAG: String = "MainActivityTag"

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
    private lateinit var levelUpFragmentContainerView: FragmentContainerView
    private lateinit var petDisplayFragmentContainerView: FragmentContainerView

    // Adapter variables
    private lateinit var shopListViewAdapter: ShopListViewAdapter

    // UI update variables
    private var previousHungerLevel: Int = PREVIOUS_VAL_UNINITIALIZED
    private var previousThirstLevel: Int = PREVIOUS_VAL_UNINITIALIZED
    private var previousHappyLevel: Int = PREVIOUS_VAL_UNINITIALIZED
    private var previousFitnessLevel: Int = PREVIOUS_VAL_UNINITIALIZED
    private var previousMoneyAmount: Int = PREVIOUS_VAL_UNINITIALIZED

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
        levelUpFragmentContainerView = findViewById<FragmentContainerView>(R.id.levelUp_fragmentContainerView)
        petDisplayFragmentContainerView = findViewById<FragmentContainerView>(R.id.petDisplay_fragmentContainerView)

        // Set up observer(s)
        mainViewModel.petLive.observe(this, Observer<PetEntity>{ currentPet ->
            if(previousHungerLevel == PREVIOUS_VAL_UNINITIALIZED) {
                initializeUIFromPet(currentPet)
            }

            updateUIFromPet(currentPet)
        })
        mainViewModel.playerLive.observe(this, Observer<PlayerEntity>{ currentPlayer ->
            if(previousMoneyAmount == PREVIOUS_VAL_UNINITIALIZED) {
                initializeUIFromPlayer(currentPlayer)
            }

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
        shopListViewAdapter = ShopListViewAdapter(this, arrayOf(0, 0, 0))
        shopListView.adapter = shopListViewAdapter
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

        levelUpFragmentContainerView.visibility = View.INVISIBLE
        levelUpFragmentContainerView.isClickable = false

        petDisplayFragmentContainerView.visibility = View.INVISIBLE
        petDisplayFragmentContainerView.isClickable = false
    }

    private fun initializeUIFromPet(pet: PetEntity) {
        // Initialize 'previous' UI values
        previousHungerLevel = pet.hunger_level
        previousThirstLevel = pet.thirst_level
        previousHappyLevel = pet.happiness_level
        previousFitnessLevel = pet.fitness_level

        // Initialize progress bars
        hungerProgressBar.progress = pet.hunger_level
        thirstProgressBar.progress = pet.thirst_level
        happinessProgressBar.progress = pet.happiness_level
        fitnessProgressBar.progress = pet.fitness_level

        // If this is the first time the game has been launched on this device, give image IDs to the database.
        if(pet.normal_image_id == IMAGE_ID_UNINITIALIZED) mainViewModel.initImageIDs()
    }

    private fun updateUIFromPet(pet: PetEntity) {
        // Pet image
        pet.setImageFromPet(petImage)

        // Status update texts
        animateStatusUpdateText(hungerUpdateText, pet.hunger_level - previousHungerLevel, UPDATE_TEXT_FADE_DURATION)
        animateStatusUpdateText(thirstUpdateText, pet.thirst_level - previousThirstLevel, UPDATE_TEXT_FADE_DURATION)
        animateStatusUpdateText(happyUpdateText, pet.happiness_level - previousHappyLevel, UPDATE_TEXT_FADE_DURATION)
        animateStatusUpdateText(fitnessUpdateText, pet.fitness_level - previousFitnessLevel, UPDATE_TEXT_FADE_DURATION)

        // Progress bars
        ObjectAnimator.ofInt(hungerProgressBar, "progress", pet.hunger_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        ObjectAnimator.ofInt(thirstProgressBar, "progress", pet.thirst_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        ObjectAnimator.ofInt(happinessProgressBar, "progress", pet.happiness_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        ObjectAnimator.ofInt(fitnessProgressBar, "progress", pet.fitness_level).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()

        // Update 'previous' UI values
        previousHungerLevel = pet.hunger_level
        previousThirstLevel = pet.thirst_level
        previousHappyLevel = pet.happiness_level
        previousFitnessLevel = pet.fitness_level
    }

    private fun initializeUIFromPlayer(player: PlayerEntity) {
        // Initialize 'previous' UI values
        previousMoneyAmount = player.money_amount

        // Initialize money amount text
        moneyAmountText.text = player.money_amount.toString()
    }

    private fun updateUIFromPlayer(player: PlayerEntity) {
        // Money update text
        animateStatusUpdateText(moneyUpdateText, player.money_amount - previousMoneyAmount, UPDATE_TEXT_FADE_DURATION)

        // Money amount text
        moneyAmountText.text = player.money_amount.toString()

        // Update num shop items owned
        shopListViewAdapter.updateNumOwned(arrayOf(player.num_bandages, player.num_firstaid, player.num_ironpaw))

        // Update 'previous' UI values
        previousMoneyAmount = player.money_amount
    }

    // Returns true if any of the menu views in the activity are currently visible, false otherwise.
    private fun checkMenuViewsVisible(): Boolean {
        return shopListView.visibility == View.VISIBLE ||
                actionsListView.visibility == View.VISIBLE ||
                levelUpFragmentContainerView.visibility == View.VISIBLE ||
                petDisplayFragmentContainerView.visibility == View.VISIBLE
    }

    fun onShopBtnPress(view: View) {
        when (shopListView.visibility) {
            View.INVISIBLE -> {
                if (checkMenuViewsVisible()) return
                shopListView.isClickable = true
                fadeInView(shopListView, MENU_FADE_ANIMATION_DURATION)
                fadeOutView(petImage, MENU_FADE_ANIMATION_DURATION)
            }
            View.VISIBLE -> {
                shopListView.isClickable = false
                fadeOutView(shopListView, MENU_FADE_ANIMATION_DURATION)
                fadeInView(petImage, MENU_FADE_ANIMATION_DURATION)
            }
            else -> Log.e(TAG, "Error: onShopBtnPress encountered unexpected visibility")
        }
    }

    fun onActionBtnPress(view: View) {
        when (actionsListView.visibility) {
            View.INVISIBLE -> {
                if (checkMenuViewsVisible()) return
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
        when (actionsListView.getPositionForView(view)) {
            0 -> mainViewModel.onFeedBtnPress()
            1 -> mainViewModel.onGiveWaterBtnPress()
            2 -> mainViewModel.onPetBtnPress()
            3 -> mainViewModel.onWalkBtnPress()
            else -> Log.e(TAG, "Error: onActionListBtnPress encountered unexpected position")
        }
    }

    fun onShopListBtnPress(view: View) {
        when (shopListView.getPositionForView(view)) {
            0 -> mainViewModel.onBuyBandagesBtnPress()
            1 -> mainViewModel.onBuyFirstAidBtnPress()
            2 -> mainViewModel.onBuyIronPawsBtnPress()
            else -> Log.e(TAG, "Error: onShopListBtnPress encountered unexpected position")
        }
    }

    fun onArenaBtnPress(view: View) = startActivity(Intent(this, PetArenaActivity::class.java))

    fun onLevelUpBtnPress(view: View) {
        when (levelUpFragmentContainerView.visibility) {
            View.INVISIBLE -> {
                if (checkMenuViewsVisible()) return
                levelUpFragmentContainerView.isClickable = true
                fadeInView(levelUpFragmentContainerView, MENU_FADE_ANIMATION_DURATION)
                fadeOutView(petImage, MENU_FADE_ANIMATION_DURATION)
            }
            View.VISIBLE -> {
                levelUpFragmentContainerView.isClickable = false
                fadeOutView(levelUpFragmentContainerView, MENU_FADE_ANIMATION_DURATION)
                fadeInView(petImage, MENU_FADE_ANIMATION_DURATION)
            }
            else -> Log.e(TAG, "Error: onLevelUpBtnPress encountered unexpected visibility")
        }
    }

    fun onConfirmLevelUpBtnPress(view: View) {
        // Level up the player
        // TODO: Move animation handling for this out of view model by getting fragment (See onSelectOwnedPetBtnPress)
        mainViewModel.onConfirmLevelUpBtnPress()
    }

    fun onCancelLevelUpBtnPress(view: View) {
        // Close the level up menu
        levelUpFragmentContainerView.isClickable = false
        fadeOutView(levelUpFragmentContainerView, MENU_FADE_ANIMATION_DURATION)
        fadeInView(petImage, MENU_FADE_ANIMATION_DURATION)
    }

    fun onPetDisplayBtnPress(view: View) {
        when (petDisplayFragmentContainerView.visibility) {
            View.INVISIBLE -> {
                if (checkMenuViewsVisible()) return
                petDisplayFragmentContainerView.isClickable = true
                fadeInView(petDisplayFragmentContainerView, MENU_FADE_ANIMATION_DURATION)
                fadeOutView(petImage, MENU_FADE_ANIMATION_DURATION)
            }
            View.VISIBLE -> {
                petDisplayFragmentContainerView.isClickable = false
                fadeOutView(petDisplayFragmentContainerView, MENU_FADE_ANIMATION_DURATION)
                fadeInView(petImage, MENU_FADE_ANIMATION_DURATION)
            }
            else -> Log.e(TAG, "Error: onPetDisplayBtnPress encountered unexpected visibility")
        }
    }

    fun onSelectOwnedPetBtnPress(view: View) {
        val fragment = petDisplayFragmentContainerView.getFragment<PetDisplayFragment>()
        fragment.onSelectOwnedPetBtnPress(view)
    }

    fun onBuyPetBtnPress(view: View) {
        val fragment = petDisplayFragmentContainerView.getFragment<PetDisplayFragment>()
        fragment.onBuyPetBtnPress(view)
    }
}