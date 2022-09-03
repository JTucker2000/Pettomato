package com.example.pettomato.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.pettomato.R
import com.example.pettomato.functions.animatePetDeath
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.viewmodels.GameOverViewModel

class GameOverActivity : AppCompatActivity() {
    private val gameOverViewModel: GameOverViewModel by viewModels()

    // View variables
    private lateinit var gameOverPetImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        // Set up view variables
        gameOverPetImage = findViewById<ImageView>(R.id.gameOverPet_image)

        // Set up observer(s)
        gameOverViewModel.petLive.observe(this, Observer<PetEntity>{ currentPet ->
            animateDeathFromPet(currentPet)
        })
    }

    fun animateDeathFromPet(pet: PetEntity) {
        // Set image, always set image to be sad
        gameOverPetImage.setImageResource(pet.sad_image_id)

        // Play death animation
        animatePetDeath(gameOverPetImage)
    }

    fun onHomeBtnPress(view: View) = startActivity(Intent(this, MainActivity::class.java))
}