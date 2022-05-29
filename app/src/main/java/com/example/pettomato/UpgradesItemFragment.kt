package com.example.pettomato

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A fragment representing a list of Upgrades.
 */
class UpgradesItemFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upgrades_item_list, container, false)
        val valArr = arrayOf<String>("A", "B", "C") // Test values for list

        if (view is RecyclerView) {
            view.layoutManager = LinearLayoutManager(context)
            view.adapter = UpgradesCustomAdapter(valArr)
        }
        return view
    }
}