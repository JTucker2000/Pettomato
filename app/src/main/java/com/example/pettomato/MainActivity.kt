package com.example.pettomato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val upgradesListView = findViewById<ListView>(R.id.upgrades_listView)
        upgradesListView.adapter = UpgradesListViewAdapter(this)
    }

    fun onUpgradeBtnPress(view: View) {

    }
}