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
        gameOverViewModel.petListLive.observe(this, Observer<List<PetEntity>>{ currentPetList ->
            animateDeathFromPet(currentPetList[0])
        })
    }

    fun animateDeathFromPet(pet: PetEntity) {
        // Set image, always set image to be sad
        gameOverPetImage.setImageResource(pet.sad_image_id)

        // Play death animation
        gameOverPetImage.animate()
            .translationXBy(0f) // Wait before playing animation
            .setDuration(700)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    gameOverPetImage.animate() // Play actual animation
                        .rotationBy(1080f)
                        .alpha(0f)
                        .scaleX(0.01f)
                        .scaleY(0.01f)
                        .setDuration(2000)
                        .setListener(null)
                }
            })
    }

    fun onHomeBtnPress(view: View) = startActivity(Intent(this, MainActivity::class.java))
}