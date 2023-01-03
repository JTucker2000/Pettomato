package com.example.pettomato.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import com.example.pettomato.*
import com.example.pettomato.viewmodels.PetArenaViewModel
import com.example.pettomato.functions.animateStatusUpdateText
import com.example.pettomato.functions.fadeInOutView
import com.example.pettomato.functions.fadeInView
import com.example.pettomato.functions.fadeOutView
import com.example.pettomato.roomentities.EnemyEntity
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.roomentities.PlayerEntity
import com.example.pettomato.viewadapters.ItemsListViewAdapter

class PetArenaActivity : AppCompatActivity() {
    private val petArenaViewModel: PetArenaViewModel by viewModels()

    // Local constants
    private val TAG: String = "PetArenaActivityTag"
    private val REWARD_ANIMATION_DURATION: Long = 3000

    // View variables
    private lateinit var itemsListView: ListView
    private lateinit var playerHealthProgressBar: ProgressBar
    private lateinit var enemyHealthProgressBar: ProgressBar
    private lateinit var playerNameText: TextView
    private lateinit var enemyNameText: TextView
    private lateinit var playerHealthNumText: TextView
    private lateinit var enemyHealthNumText: TextView
    private lateinit var playerHealthUpdateText: TextView
    private lateinit var enemyHealthUpdateText: TextView
    private lateinit var playerLevelText: TextView
    private lateinit var enemyLevelText: TextView
    private lateinit var playerRewardText: TextView
    private lateinit var playerPetImage: ImageView
    private lateinit var enemyPetImage: ImageView
    private lateinit var attackBtn: Button
    private lateinit var itemsBtn: Button
    private lateinit var statsBtn: Button
    private lateinit var quitBtn: Button
    private lateinit var statsFragmentContainerView: FragmentContainerView

    // Adapter variables
    private lateinit var itemsListViewAdapter: ItemsListViewAdapter

    // UI update variables
    private var previousPlayerHealth: Int = PREVIOUS_VAL_UNINITIALIZED
    private var previousEnemyHealth: Int = PREVIOUS_VAL_UNINITIALIZED
    private var previousMoneyAmount: Int = PREVIOUS_VAL_UNINITIALIZED
    private var curIronPawsAmount: Int = NUM_OWNED_VAL_UNINITIALIZED

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_arena)

        // Set up view variables
        itemsListView = findViewById<ListView>(R.id.items_listView)
        playerHealthProgressBar = findViewById<ProgressBar>(R.id.playerHealth_progressBar)
        enemyHealthProgressBar = findViewById<ProgressBar>(R.id.enemyHealth_progressBar)
        playerNameText = findViewById<TextView>(R.id.playerName_text)
        enemyNameText = findViewById<TextView>(R.id.enemyName_text)
        playerHealthNumText = findViewById<TextView>(R.id.playerHealthNum_text)
        enemyHealthNumText = findViewById<TextView>(R.id.enemyHealthNum_text)
        playerHealthUpdateText = findViewById<TextView>(R.id.playerHealth_update_text)
        enemyHealthUpdateText = findViewById<TextView>(R.id.enemyHealth_update_text)
        playerLevelText = findViewById<TextView>(R.id.playerLevel_text)
        enemyLevelText = findViewById<TextView>(R.id.enemyLevel_text)
        playerRewardText = findViewById<TextView>(R.id.playerReward_text)
        playerPetImage = findViewById<ImageView>(R.id.player_petImage)
        enemyPetImage = findViewById<ImageView>(R.id.enemy_petImage)
        attackBtn = findViewById<Button>(R.id.attack_btn)
        itemsBtn = findViewById<Button>(R.id.items_btn)
        statsBtn = findViewById<Button>(R.id.stats_btn)
        quitBtn = findViewById<Button>(R.id.quit_btn)
        statsFragmentContainerView = findViewById<FragmentContainerView>(R.id.arena_stats_fragmentContainerView)

        // Set up observer(s)
        petArenaViewModel.petLive.observe(this, Observer<PetEntity> { currentPet ->
            if(previousPlayerHealth == PREVIOUS_VAL_UNINITIALIZED) {
                initializeUIFromPet(currentPet)
            }

            updateUIFromPet(currentPet)
        })
        petArenaViewModel.enemyLive.observe(this, Observer<EnemyEntity> { currentEnemy ->
            if(previousEnemyHealth == PREVIOUS_VAL_UNINITIALIZED) {
                initializeUIFromEnemy(currentEnemy)
            }

            updateUIFromEnemy(currentEnemy)
        })
        petArenaViewModel.playerLive.observe(this, Observer<PlayerEntity> { currentPlayer ->
            if(previousMoneyAmount == PREVIOUS_VAL_UNINITIALIZED) {
                initializeUIFromPlayer(currentPlayer)
            }

            updateUIFromPlayer(currentPlayer)
        })

        // Set up view(s)
        itemsListViewAdapter = ItemsListViewAdapter(this, arrayOf(0, 0, 0))
        itemsListView.adapter = itemsListViewAdapter
        itemsListView.visibility = View.INVISIBLE
        itemsListView.isClickable = false

        playerHealthUpdateText.visibility = View.INVISIBLE
        enemyHealthUpdateText.visibility = View.INVISIBLE
        playerRewardText.visibility = View.INVISIBLE

        statsFragmentContainerView.visibility = View.INVISIBLE
        statsFragmentContainerView.isClickable = false

        // Set onClickListener(s)
        attackBtn.setOnClickListener { onAttackBtnPress() }
        itemsBtn.setOnClickListener { onItemsBtnPress() }
        statsBtn.setOnClickListener { onStatsBtnPress() }
        quitBtn.setOnClickListener { onQuitBtnPress() }
    }

    private fun initializeUIFromPet(pet: PetEntity) {
        // Initialize 'previous' UI values
        previousPlayerHealth = pet.pet_health

        // Initialize progress bar
        playerHealthProgressBar.max = pet.petMaxHp
        playerHealthProgressBar.progress = pet.pet_health
    }

    private fun updateUIFromPet(pet: PetEntity) {
        // Check to see if player has been defeated
        if(pet.pet_health <= 0) {
            petArenaViewModel.onPlayerDefeat()
            startActivity(Intent(this, GameOverActivity::class.java))
            return
        }

        // Pet image
        pet.setImageFromPet(playerPetImage)

        // Status update texts
        animateStatusUpdateText(playerHealthUpdateText, pet.pet_health - previousPlayerHealth, UPDATE_TEXT_FADE_DURATION)

        // Name text
        playerNameText.text = pet.pet_name

        // Health text
        playerHealthNumText.text = "${pet.pet_health}/${pet.petMaxHp}"

        // Level text
        playerLevelText.text = "LVL ${pet.pet_level}"

        // Progress bars
        playerHealthProgressBar.max = pet.petMaxHp
        ObjectAnimator.ofInt(playerHealthProgressBar, "progress", pet.pet_health).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()

        // Update 'previous' UI values
        previousPlayerHealth = pet.pet_health
    }

    private fun initializeUIFromEnemy(enemy: EnemyEntity) {
        // Initialize 'previous' UI values
        previousEnemyHealth = enemy.enemy_health

        // Initialize progress bar
        enemyHealthProgressBar.max = enemy.enemy_maxhp
        enemyHealthProgressBar.progress = enemy.enemy_health

        // If this is the first time the game has been launched on this device, give image IDs to the database.
        if(enemy.image_id == IMAGE_ID_UNINITIALIZED) petArenaViewModel.initImageIDs()
    }

    private fun updateUIFromEnemy(enemy: EnemyEntity) {
        // Check to see if enemy has been defeated
        if(enemy.enemy_health <= 0) petArenaViewModel.onEnemyDefeat()

        // Enemy image
        enemyPetImage.setImageResource(enemy.image_id)

        // Status update texts
        animateStatusUpdateText(enemyHealthUpdateText, enemy.enemy_health - previousEnemyHealth, UPDATE_TEXT_FADE_DURATION)

        // Name text
        enemyNameText.text = enemy.enemy_name

        // Health text
        enemyHealthNumText.text = "${enemy.enemy_health}/${enemy.enemy_maxhp}"

        // Level text
        enemyLevelText.text = "LVL ${enemy.enemy_level}"

        // Progress bars
        enemyHealthProgressBar.max = enemy.enemy_maxhp
        ObjectAnimator.ofInt(enemyHealthProgressBar, "progress", enemy.enemy_health).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()

        // Update 'previous' UI values
        previousEnemyHealth = enemy.enemy_health
    }

    private fun initializeUIFromPlayer(player: PlayerEntity) {
        // Initialize 'previous' UI values
        previousMoneyAmount = player.money_amount

        // Initialize iron paw amount
        // (For checking if iron paw attack animation should play)
        curIronPawsAmount = player.num_ironpaw
    }

    private fun updateUIFromPlayer(player: PlayerEntity) {
        // Play animation for earning money
        val changeAmount = player.money_amount - previousMoneyAmount
        if(changeAmount > 0) {
            playerRewardText.text = "Reward Earned!\n+${changeAmount} Coins"
            fadeInOutView(playerRewardText, REWARD_ANIMATION_DURATION)
        }

        // Update num items owned
        itemsListViewAdapter.updateNumOwned(arrayOf(player.num_bandages, player.num_firstaid, player.num_ironpaw))

        // Update 'previous' UI values
        previousMoneyAmount = player.money_amount

        // Update iron paw amount
        curIronPawsAmount = player.num_ironpaw
    }

    // Returns true if an attack is ongoing or if stats menu is open, false otherwise.
    private fun isScreenBusy(): Boolean {
        return petArenaViewModel.attackIsOngoing ||
                statsFragmentContainerView.visibility == View.VISIBLE
    }

    private fun onAttackBtnPress() {
        if(!isScreenBusy()) {
            // Prevents multiple attacks until animation has finished
            petArenaViewModel.attackIsOngoing = true

            // TODO: Make these animations go towards the center of the screen, not just a pixel amount. This will make it work on more screen sizes.
            // Do animations for the player's attack
            playerPetImage.animate()
                .translationX(100f)
                .setDuration(100)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        playerPetImage.animate()
                            .translationX(0f)
                            .setDuration(600)
                            .setListener(null)
                    }
                })

            // Do animations for the enemy's attack
            enemyPetImage.animate()
                .translationX(-100f)
                .setDuration(100)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        enemyPetImage.animate()
                            .translationX(0f)
                            .setDuration(600)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    // Animation is over, player can attack again
                                    petArenaViewModel.attackIsOngoing = false
                                }
                            })
                    }
                })

            // Modify data in viewmodel
            petArenaViewModel.onAttackBtnPress()
        }
    }

    private fun onItemsBtnPress() {
        when (itemsListView.visibility) {
            View.INVISIBLE -> {
                if(isScreenBusy()) return
                itemsListView.isClickable = true
                fadeInView(itemsListView, MENU_FADE_ANIMATION_DURATION)
            }
            View.VISIBLE -> {
                itemsListView.isClickable = false
                fadeOutView(itemsListView, MENU_FADE_ANIMATION_DURATION)
            }
            else -> Log.e(TAG, "Error: onItemsBtnPress encountered unexpected visibility")
        }
    }

    fun onItemUseBtnPress(pos: Int) {
        when (pos) {
            0 -> petArenaViewModel.onUseBandagesBtnPress()
            1 -> petArenaViewModel.onUseFirstAidBtnPress()
            2 -> {
                if(!isScreenBusy() && (curIronPawsAmount > 0)) {
                    // Prevents multiple attacks until animation has finished
                    petArenaViewModel.attackIsOngoing = true

                    // TODO: Make these animations go towards the center of the screen, not just a pixel amount. This will make it work on more screen sizes.
                    // Do animations for the player's attack
                    playerPetImage.animate()
                        .translationX(100f)
                        .setDuration(100)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                playerPetImage.animate()
                                    .translationX(0f)
                                    .setDuration(600)
                                    .setListener(object : AnimatorListenerAdapter() {
                                        override fun onAnimationEnd(animation: Animator) {
                                            // Animation is over, player can attack again
                                            petArenaViewModel.attackIsOngoing = false
                                        }
                                    })
                            }
                        })

                    // Modify data in viewmodel
                    petArenaViewModel.onUseIronPawsBtnPress()
                }
            }
            else -> Log.e(TAG, "Error: onItemsListBtnPress encountered unexpected position")
        }
    }

    private fun onStatsBtnPress() {
        when (statsFragmentContainerView.visibility) {
            View.INVISIBLE -> {
                if(isScreenBusy() || itemsListView.visibility == View.VISIBLE) return
                statsFragmentContainerView.isClickable = true
                fadeInView(statsFragmentContainerView, MENU_FADE_ANIMATION_DURATION)
            }
            View.VISIBLE -> {
                statsFragmentContainerView.isClickable = false
                fadeOutView(statsFragmentContainerView, MENU_FADE_ANIMATION_DURATION)
            }
            else -> Log.e(TAG, "Error: onStatsBtnPress encountered unexpected visibility")
        }
    }

    private fun onQuitBtnPress() = startActivity(Intent(this, MainActivity::class.java))
}
