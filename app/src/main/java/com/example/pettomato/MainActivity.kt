package com.example.pettomato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivityTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up upgrades list view
        val upgradesListView = findViewById<ListView>(R.id.upgrades_listView)
        upgradesListView.adapter = UpgradesListViewAdapter(this)
        upgradesListView.visibility = View.INVISIBLE
    }

    fun onUpgradeBtnPress(view: View) {
        val upgradesListView = findViewById<ListView>(R.id.upgrades_listView)
        val petImage = findViewById<ImageView>(R.id.pet_image)

        when (upgradesListView.visibility){
            View.INVISIBLE -> {
                upgradesListView.isClickable = true
                upgradesListView.visibility = View.VISIBLE
                petImage.visibility = View.INVISIBLE
            }
            View.VISIBLE -> {
                upgradesListView.isClickable = false
                upgradesListView.visibility = View.INVISIBLE
                petImage.visibility = View.VISIBLE
            }
            else -> Log.e(TAG, "Error: onUpgradeBtnPress encountered unexpected visibility")
        }
    }
}