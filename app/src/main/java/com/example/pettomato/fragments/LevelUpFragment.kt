package com.example.pettomato.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.pettomato.R
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.viewmodels.MainViewModel

class LevelUpFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()

    // View variables
    private lateinit var levelUpPetNameText: TextView
    private lateinit var levelUpPetLevelText: TextView
    private lateinit var levelUpCostText: TextView
    private lateinit var levelUpPetImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_level_up, container, false)

        // Set up view variables
        levelUpPetNameText = view.findViewById<TextView>(R.id.levelUpPetName_text)
        levelUpPetLevelText = view.findViewById<TextView>(R.id.levelUpPetLevel_text)
        levelUpCostText = view.findViewById<TextView>(R.id.levelUpCost_text)
        levelUpPetImage = view.findViewById<ImageView>(R.id.levelUpPet_image)

        // Set up observer(s)
        mainViewModel.petLive.observe(viewLifecycleOwner, Observer<PetEntity> { currentPet ->
            updateUIFromPet(currentPet)
        })

        return view
    }

    // Makes levelUpPetImage rotate 360 degrees.
    private fun levelUpSuccessAnimation() {
        levelUpPetImage.animate()
            .rotation(360f)
            .setDuration(1000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    levelUpPetImage.rotation = 0f
                }
            })
    }

    // Makes levelUpCostText red and bouncy, while also making levelUpPetImage temporarily sad.
    private fun levelUpFailAnimation(pet: PetEntity) {
        // Make text red
        levelUpCostText.setTextColor(Color.parseColor("#FF0000"))

        // Make pet image sad
        levelUpPetImage.setImageResource(pet.sad_image_id)

        // Animate bouncing up and down
        levelUpCostText.animate()
            .translationY(-20f)
            .setDuration(65)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    levelUpCostText.animate()
                        .translationY(20f)
                        .setDuration(130)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                levelUpCostText.animate()
                                    .translationY(0f)
                                    .setDuration(65)
                                    .setListener(object : AnimatorListenerAdapter() {
                                        override fun onAnimationEnd(animation: Animator) {
                                            // When animations are done, reset text and image.
                                            levelUpCostText.setTextColor(Color.GRAY)
                                            levelUpPetImage.setImageResource(pet.happy_image_id)
                                        }
                                    })
                            }
                        })
                }
            })
    }

    private fun updateUIFromPet(pet: PetEntity) {
        // Set pet image, pet is always happy on this screen.
        levelUpPetImage.setImageResource(pet.happy_image_id)

        // Set textviews
        levelUpPetNameText.text = pet.pet_name
        levelUpPetLevelText.text = "Level ${pet.pet_level}"
        levelUpCostText.text = "Cost: ${pet.levelUpCost}"

        // Play animations from viewmodel
        if(mainViewModel.playLevelUpSuccessAnimation) { // Plays when the player levels up.
            levelUpSuccessAnimation()
            mainViewModel.playLevelUpSuccessAnimation = false
        }
        if(mainViewModel.playLevelUpFailAnimation) { // Plays when the player tries to level without enough money.
            levelUpFailAnimation(pet)
            mainViewModel.playLevelUpFailAnimation = false
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LevelUpFragment()
    }
}