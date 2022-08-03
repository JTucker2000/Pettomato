package com.example.pettomato.activities

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
import com.example.pettomato.dataclasses.Enemy
import com.example.pettomato.roomentities.PetEntity

class PetArenaActivity : AppCompatActivity() {
    private val petArenaViewModel: PetArenaViewModel by viewModels()

    // Constants
    private val PROGRESSBAR_ANIMATION_DURATION: Long = 300

    // View variables
    private lateinit var playerHealthProgressBar: ProgressBar
    private lateinit var enemyHealthProgressBar: ProgressBar
    private lateinit var playerHealthNumText: TextView
    private lateinit var enemyHealthNumText: TextView
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
        playerPetImage = findViewById<ImageView>(R.id.player_petImage)
        enemyPetImage = findViewById<ImageView>(R.id.enemy_petImage)

        // Set up observer(s)
        petArenaViewModel.petListLive.observe(this, Observer<List<PetEntity>>{ currentPetList ->
            updateUIFromPet(currentPetList[0])
        })
        petArenaViewModel.enemyLive.observe(this, Observer<Enemy>{ currentEnemy ->
            updateUIFromEnemy(currentEnemy)
        })
    }

    private fun updateUIFromPet(pet: PetEntity) {
        // Pet image
        playerPetImage.setImageResource(pet.image_id)

        // Health text
        playerHealthNumText.text = "${pet.pet_health}/${pet.pet_maxhp}"

        // Progress bars
        playerHealthProgressBar.max = pet.pet_maxhp
        ObjectAnimator.ofInt(playerHealthProgressBar, "progress", pet.pet_health).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
    }

    private fun updateUIFromEnemy(enemy: Enemy) {
        // Pet image
        enemyPetImage.setImageResource(enemy.image_id)

        // Health text
        enemyHealthNumText.text = "${enemy.enemy_health}/${enemy.enemy_maxhp}"

        // Progress bars
        enemyHealthProgressBar.max = enemy.enemy_maxhp
        ObjectAnimator.ofInt(enemyHealthProgressBar, "progress", enemy.enemy_health).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
    }

    fun onQuitBtnPress(view: View) = startActivity(Intent(this, MainActivity::class.java))
}
