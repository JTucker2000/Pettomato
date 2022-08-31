package com.example.pettomato.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.pettomato.PREVIOUS_VAL_UNINITIALIZED
import com.example.pettomato.R
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.viewmodels.MainViewModel
import com.example.pettomato.viewmodels.PetArenaViewModel

class LevelUpFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()

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
        mainViewModel.petListLive.observe(viewLifecycleOwner, Observer<List<PetEntity>>{ currentPetList ->
            updateUIFromPet(currentPetList[0])
        })

        return view
    }

    private fun updateUIFromPet(pet: PetEntity) {
        // Set pet image
        pet.setImageFromPet(levelUpPetImage)

        // Set textviews
        levelUpPetNameText.text = pet.pet_name
        levelUpPetLevelText.text = "Level ${pet.pet_level}"
        levelUpCostText.text = "Cost: ${pet.levelUpCost}"
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LevelUpFragment()
    }
}