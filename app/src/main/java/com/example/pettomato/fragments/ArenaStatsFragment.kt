package com.example.pettomato.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.pettomato.R
import com.example.pettomato.roomentities.PlayerEntity
import com.example.pettomato.viewadapters.ArenaStatsListViewAdapter
import com.example.pettomato.viewmodels.PetArenaViewModel

class ArenaStatsFragment : Fragment() {
    private val petArenaViewModel: PetArenaViewModel by viewModels()

    // View variables
    private lateinit var arenaStatsListView: ListView

    // Adapter variables
    private lateinit var arenaStatsViewAdapter: ArenaStatsListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_arena_stats, container, false)

        // Set up view variables
        arenaStatsListView = view.findViewById<ListView>(R.id.arena_stats_listView)

        // Set up observer(s)
        petArenaViewModel.playerLive.observe(viewLifecycleOwner, Observer<PlayerEntity> { currentPlayer ->
            updateUIFromPlayer(currentPlayer)
        })

        // Set up view(s)
        arenaStatsViewAdapter = ArenaStatsListViewAdapter(view.context, PlayerEntity())
        arenaStatsListView.adapter = arenaStatsViewAdapter

        return view
    }

    private fun updateUIFromPlayer(player: PlayerEntity) {
        arenaStatsViewAdapter.updateStats(player)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArenaStatsFragment()
    }
}