package com.example.pettomato.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.pettomato.MENU_FADE_ANIMATION_DURATION
import com.example.pettomato.R
import com.example.pettomato.functions.fadeInView
import com.example.pettomato.functions.fadeOutView
import com.example.pettomato.roomentities.PetEntity
import com.example.pettomato.viewadapters.OwnedPetsGridViewAdapter
import com.example.pettomato.viewadapters.PetShopGridViewAdapter
import com.example.pettomato.viewmodels.MainViewModel

class PetDisplayFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()

    // View variables
    private lateinit var currentPetDisplayText: TextView
    private lateinit var ownedPetsBtn: Button
    private lateinit var petShopBtn: Button
    lateinit var ownedPetsGridView: GridView
    lateinit var petShopGridView: GridView

    // Adapter variables
    private lateinit var ownedPetsGridViewAdapter: OwnedPetsGridViewAdapter
    private lateinit var petShopGridViewAdapter: PetShopGridViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pet_display, container, false)

        // Set up view variables
        currentPetDisplayText = view.findViewById<TextView>(R.id.currentPetDisplay_text)
        ownedPetsBtn = view.findViewById<Button>(R.id.ownedPets_btn)
        petShopBtn = view.findViewById<Button>(R.id.petShop_btn)
        ownedPetsGridView = view.findViewById<GridView>(R.id.ownedPets_gridView)
        petShopGridView = view.findViewById<GridView>(R.id.petShop_gridView)

        // Set up observer(s)
        mainViewModel.petListLive.observe(viewLifecycleOwner, Observer<List<PetEntity>> { currentPetList ->
            updateUIFromPetList(currentPetList)
        })

        // Set up view(s)
        ownedPetsGridViewAdapter = OwnedPetsGridViewAdapter(requireContext(), listOf<PetEntity>())
        ownedPetsGridView.adapter = ownedPetsGridViewAdapter
        ownedPetsGridView.visibility = View.VISIBLE
        ownedPetsGridView.isClickable = true

        petShopGridViewAdapter = PetShopGridViewAdapter(requireContext())
        petShopGridView.adapter = petShopGridViewAdapter
        petShopGridView.visibility = View.INVISIBLE
        petShopGridView.isClickable = false

        // Set onClickListener(s)
        ownedPetsBtn.setOnClickListener { openOwnedPetsMenu() }
        petShopBtn.setOnClickListener { openPetShopMenu() }

        return view
    }

    private fun updateUIFromPetList(petList: List<PetEntity>) {
        // Update pets owned list
        ownedPetsGridViewAdapter.updatePetList(petList)
    }

    private fun openOwnedPetsMenu() {
        if(ownedPetsGridView.visibility == View.INVISIBLE) {
            fadeInView(ownedPetsGridView, MENU_FADE_ANIMATION_DURATION)
            fadeOutView(petShopGridView, MENU_FADE_ANIMATION_DURATION)
            ownedPetsGridView.isClickable = true
            petShopGridView.isClickable = false
            currentPetDisplayText.text = "Owned Pets"
        }
    }

    private fun openPetShopMenu() {
        if(petShopGridView.visibility == View.INVISIBLE) {
            fadeInView(petShopGridView, MENU_FADE_ANIMATION_DURATION)
            fadeOutView(ownedPetsGridView, MENU_FADE_ANIMATION_DURATION)
            petShopGridView.isClickable = true
            ownedPetsGridView.isClickable = false
            currentPetDisplayText.text = "Pet Shop"
        }
    }

    fun onSelectOwnedPetBtnPress(pos: Int) = mainViewModel.onSelectOwnedPetBtnPress(pos)

    fun onBuyPetBtnPress(pos: Int) = mainViewModel.onBuyPetBtnPress(pos)

    companion object {
        @JvmStatic
        fun newInstance() = PetDisplayFragment()
    }
}