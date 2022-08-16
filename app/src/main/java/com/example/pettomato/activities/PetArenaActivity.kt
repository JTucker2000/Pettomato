package com.example.pettomato.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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
import com.example.pettomato.MENU_FADE_ANIMATION_DURATION
import com.example.pettomato.PREVIOUS_VAL_UNINITIALIZED
import com.example.pettomato.PROGRESSBAR_ANIMATION_DURATION
import com.example.pettomato.viewmodels.PetArenaViewModel
import com.example.pettomato.R
import com.example.pettomato.functions.fadeInOutView
import com.example.pettomato.functions.fadeInView
import com.example.pettomato.functions.fadeOutView
import com.example.pettomato.roomentities.EnemyEntity
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.roomentities.PlayerEntity
import com.example.pettomato.viewadapters.ItemsListViewAdapter

class PetArenaActivity : AppCompatActivity() {
    private val petArenaViewModel: PetArenaViewModel by viewModels()

    private var attackIsOngoing: Boolean = false

    // Local constants
    private val TAG: String = "PetArenaActivityTag"
    private val REWARD_ANIMATION_DURATION: Long = 3000

    // View variables
    private  lateinit var itemsListView: ListView
    private lateinit var playerHealthProgressBar: ProgressBar
    private lateinit var enemyHealthProgressBar: ProgressBar
    private lateinit var playerHealthNumText: TextView
    private lateinit var enemyHealthNumText: TextView
    private lateinit var playerLevelText: TextView
    private lateinit var enemyLevelText: TextView
    private lateinit var playerRewardText: TextView
    private lateinit var playerPetImage: ImageView
    private lateinit var enemyPetImage: ImageView

    // Adapter variables
    private lateinit var itemsListViewAdapter: ItemsListViewAdapter

    // UI update variables
    private var previousMoneyAmount: Int = PREVIOUS_VAL_UNINITIALIZED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_arena)

        // Set up view variables
        itemsListView = findViewById<ListView>(R.id.items_listView)
        playerHealthProgressBar = findViewById<ProgressBar>(R.id.playerHealth_progressBar)
        enemyHealthProgressBar = findViewById<ProgressBar>(R.id.enemyHealth_progressBar)
        playerHealthNumText = findViewById<TextView>(R.id.playerHealthNum_text)
        enemyHealthNumText = findViewById<TextView>(R.id.enemyHealthNum_text)
        playerLevelText = findViewById<TextView>(R.id.playerLevel_text)
        enemyLevelText = findViewById<TextView>(R.id.enemyLevel_text)
        playerRewardText = findViewById<TextView>(R.id.playerReward_text)
        playerPetImage = findViewById<ImageView>(R.id.player_petImage)
        enemyPetImage = findViewById<ImageView>(R.id.enemy_petImage)

        // ---- FIRST RUN, FOR PREPOPULATING DATABASE ----
        //petArenaViewModel.addEnemy(EnemyEntity(0, "AngryCorgi", R.drawable.corgiface1, 1, 10, 10))
        // ---- END FIRST RUN ----

        // Set up observer(s)
        petArenaViewModel.petListLive.observe(this, Observer<List<PetEntity>>{ currentPetList ->
            updateUIFromPet(currentPetList[0])
        })
        petArenaViewModel.enemyLive.observe(this, Observer<EnemyEntity>{ currentEnemy ->
            updateUIFromEnemy(currentEnemy)
        })
        petArenaViewModel.playerLive.observe(this, Observer<PlayerEntity>{ currentPlayer ->
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

        playerRewardText.visibility = View.INVISIBLE
    }

    private fun updateUIFromPet(pet: PetEntity) {
        // Check to see if player has been defeated
        if(pet.pet_health <= 0) {
            petArenaViewModel.onPlayerDefeat()
            startActivity(Intent(this, GameOverActivity::class.java))
            return
        }

        // Pet image
        playerPetImage.setImageResource(pet.image_id)

        // Health text
        playerHealthNumText.text = "${pet.pet_health}/${pet.pet_maxhp}"

        // Level text
        playerLevelText.text = "LVL ${pet.pet_level}"

        // Progress bars
        playerHealthProgressBar.max = pet.pet_maxhp
        ObjectAnimator.ofInt(playerHealthProgressBar, "progress", pet.pet_health).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
    }

    private fun updateUIFromEnemy(enemy: EnemyEntity) {
        // Check to see if enemy has been defeated
        if(enemy.enemy_health <= 0) petArenaViewModel.onEnemyDefeat()

        // Pet image
        enemyPetImage.setImageResource(enemy.image_id)

        // Health text
        enemyHealthNumText.text = "${enemy.enemy_health}/${enemy.enemy_maxhp}"

        // Level text
        enemyLevelText.text = "LVL ${enemy.enemy_level}"

        // Progress bars
        enemyHealthProgressBar.max = enemy.enemy_maxhp
        ObjectAnimator.ofInt(enemyHealthProgressBar, "progress", enemy.enemy_health).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
    }

    private fun initializeUIFromPlayer(player: PlayerEntity) {
        // Initialize 'previous' UI values
        previousMoneyAmount = player.money_amount
    }

    private fun updateUIFromPlayer(player: PlayerEntity) {
        // Play animation for earning money
        val changeAmount = player.money_amount - previousMoneyAmount
        if(changeAmount > 0) {
            playerRewardText.text = "Enemy Defeated!\n+${changeAmount} Coins"
            fadeInOutView(playerRewardText, REWARD_ANIMATION_DURATION)
        }

        // Update num items owned
        itemsListViewAdapter.updateNumOwned(arrayOf(player.num_bandages, player.num_firstaid, player.num_ironpaw))

        // Update 'previous' UI values
        previousMoneyAmount = player.money_amount
    }

    fun onAttackBtnPress(view: View) {
        if(!attackIsOngoing) {
            // Prevents multiple attacks until animation has finished
            attackIsOngoing = true

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
                                    attackIsOngoing = false
                                }
                            })
                    }
                })

            // Modify data in viewmodel
            petArenaViewModel.onAttackBtnPress()
        }
    }

    fun onItemsBtnPress(view: View) {
        when (itemsListView.visibility) {
            View.INVISIBLE -> {
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

    fun onItemsListBtnPress(view: View) {
        when (itemsListView.getPositionForView(view)) {
            0 -> petArenaViewModel.onUseBandagesBtnPress()
            1 -> petArenaViewModel.onUseFirstAidBtnPress()
            2 -> petArenaViewModel.onUseIronPawsBtnPress()
            else -> Log.e(TAG, "Error: onItemsListBtnPress encountered unexpected position")
        }
    }

    fun onQuitBtnPress(view: View) = startActivity(Intent(this, MainActivity::class.java))
}
