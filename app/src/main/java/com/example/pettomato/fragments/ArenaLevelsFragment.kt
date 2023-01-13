package com.example.pettomato.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.pettomato.R
import com.example.pettomato.roomentities.PlayerEntity
import com.example.pettomato.viewmodels.PetArenaViewModel

class ArenaLevelsFragment : Fragment() {
    private val petArenaViewModel: PetArenaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_arena_levels, container, false)

        // Set up observer(s)
        petArenaViewModel.playerLive.observe(viewLifecycleOwner, Observer<PlayerEntity> { currentPlayer ->
            updateUIFromPlayer(currentPlayer)
        })

        return view
    }

    private fun updateUIFromPlayer(player: PlayerEntity) {
        // placeholder
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArenaLevelsFragment()
    }
}