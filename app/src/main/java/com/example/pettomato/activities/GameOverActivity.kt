package com.example.pettomato.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.example.pettomato.R
import com.example.pettomato.functions.animatePetDeath
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.viewmodels.GameOverViewModel

class GameOverActivity : AppCompatActivity() {
    private val gameOverViewModel: GameOverViewModel by viewModels()

    // View variables
    private lateinit var gameOverPetImage: ImageView
    private lateinit var homeBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        // Set up view variables
        gameOverPetImage = findViewById<ImageView>(R.id.gameOverPet_image)
        homeBtn = findViewById<Button>(R.id.home_btn)

        // Set up observer(s)
        gameOverViewModel.petLive.observe(this, Observer<PetEntity> { currentPet ->
            animateDeathFromPet(currentPet)
        })

        // Set onClickListener(s)
        homeBtn.setOnClickListener { onHomeBtnPress() }
    }

    fun animateDeathFromPet(pet: PetEntity) {
        // Set image, always set image to be sad
        gameOverPetImage.setImageResource(pet.sad_image_id)

        // Play death animation
        animatePetDeath(gameOverPetImage)
    }

    private fun onHomeBtnPress() = startActivity(Intent(this, MainActivity::class.java))
}