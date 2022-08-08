package com.example.pettomato.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.pettomato.viewmodels.PetArenaViewModel
import com.example.pettomato.R
import com.example.pettomato.roomentities.EnemyEntity
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.roomentities.PlayerEntity

class PetArenaActivity : AppCompatActivity() {
    private val petArenaViewModel: PetArenaViewModel by viewModels()

    private var attackIsOngoing: Boolean = false

    // Constants
    private val PROGRESSBAR_ANIMATION_DURATION: Long = 300

    // View variables
    private lateinit var playerHealthProgressBar: ProgressBar
    private lateinit var enemyHealthProgressBar: ProgressBar
    private lateinit var playerHealthNumText: TextView
    private lateinit var enemyHealthNumText: TextView
    private lateinit var playerLevelText: TextView
    private lateinit var enemyLevelText: TextView
    private lateinit var playerPetImage: ImageView
    private lateinit var enemyPetImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_arena)

        // Set up view variables
        playerHealthProgressBar = findViewById<ProgressBar>(R.id.playerHealth_progressBar)
        enemyHealthProgressBar = findViewById<ProgressBar>(R.id.enemyHealth_progressBar)
        playerHealthNumText = findViewById<TextView>(R.id.playerHealthNum_text)
        enemyHealthNumText = findViewById<TextView>(R.id.enemyHealthNum_text)
        playerLevelText = findViewById<TextView>(R.id.playerLevel_text)
        enemyLevelText = findViewById<TextView>(R.id.enemyLevel_text)
        playerPetImage = findViewById<ImageView>(R.id.player_petImage)
        enemyPetImage = findViewById<ImageView>(R.id.enemy_petImage)

        // ---- FIRST RUN, FOR PREPOPULATING DATABASE ----
        //petArenaViewModel.addEnemy(EnemyEntity(0, "AngryCorgi", R.drawable.corgiface1, 1, 10, 10))
        //petArenaViewModel.addEnemy(EnemyEntity(0, "AngryShepard", R.drawable.germanshepard1, 5, 40, 40))
        // ---- END FIRST RUN ----

        // Set up observer(s)
        petArenaViewModel.petListLive.observe(this, Observer<List<PetEntity>>{ currentPetList ->
            updateUIFromPet(currentPetList[0])
        })
        petArenaViewModel.enemyLive.observe(this, Observer<EnemyEntity>{ currentEnemy ->
            updateUIFromEnemy(currentEnemy)
        })
        petArenaViewModel.playerLive.observe(this, Observer<PlayerEntity>{ currentPlayer ->
            updateUIFromPlayer(currentPlayer)
        })
    }

    private fun updateUIFromPet(pet: PetEntity) {
        // TODO: Check HERE to see if the player has been defeated and respond

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
        // TODO: Check HERE to see if the enemy has been defeated and respond

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

    private fun updateUIFromPlayer(player: PlayerEntity) {
        // TODO: Play animation for earning money, that's all it needs to do here.
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

    fun onQuitBtnPress(view: View) = startActivity(Intent(this, MainActivity::class.java))
}
