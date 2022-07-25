package com.example.pettomato.activities

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.pettomato.R
import com.example.pettomato.dataclasses.Enemy
import com.example.pettomato.roomentities.PetEntity

class PetArenaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_arena)
    }

    private fun updateFromPet(pet: PetEntity) {
        // Update arena UI from given pet
    }

    private fun updateFromEnemy(enemy: Enemy) {
        // Update arena UI from given enemy
    }

    fun onQuitBtnPress(view: View) = startActivity(Intent(this, MainActivity::class.java))
}