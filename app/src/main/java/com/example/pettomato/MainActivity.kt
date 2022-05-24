package com.example.pettomato

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentContainerView

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivityTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onUpgradeBtnPress(view: View) {
        var upgradeFrag = findViewById<FragmentContainerView>(R.id.upgrades_fragmentContainerView)

        when (upgradeFrag.visibility) {
            View.INVISIBLE -> {
                upgradeFrag.isClickable = true
                upgradeFrag.visibility = View.VISIBLE
            }
            View.VISIBLE -> {
                upgradeFrag.isClickable = false
                upgradeFrag.visibility = View.INVISIBLE
            }
            else -> Log.e(TAG, "Error: onUpgradeBtnPress unexpected visibility")
        }
    }
}