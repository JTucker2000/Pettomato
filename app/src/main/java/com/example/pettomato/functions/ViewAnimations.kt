package com.example.pettomato.functions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.view.View
import android.widget.TextView

// This file contains top level functions relating to the animation of view objects.

// Animates the given view to fade in.
fun fadeInView(view: View, duration: Long) {
    view.apply {
        alpha = 0f
        visibility = View.VISIBLE

        animate()
            .alpha(1f)
            .setDuration(duration)
            .setListener(null)
    }
}

// Animates the given view to fade out.
fun fadeOutView(view: View, duration: Long) {
    view.apply {
        animate()
            .alpha(0f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.INVISIBLE
                }
            })
    }
}

// Animates the given view to fade in and out.
fun fadeInOutView(view: View, duration: Long) {
    view.apply {
        alpha = 0f
        visibility = View.VISIBLE

        animate()
            .alpha(1f)
            .setDuration(duration/2)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    fadeOutView(view, duration/2)
                }
            })
    }
}

// Animates the given textview to fade in and out while displaying a green or red
// number based on the given change amount.
// For example, if changeAmount = -10, the textview will fade in and out with a red "-10" text.
fun animateStatusUpdateText(curTextView: TextView, changeAmount: Int, duration: Long) {
    if(changeAmount == 0) return

    when {
        changeAmount > 0 -> {
            curTextView.text = "+$changeAmount"
            curTextView.setTextColor(Color.parseColor("#00ff00"))
        }
        changeAmount < 0 -> {
            curTextView.text = "$changeAmount"
            curTextView.setTextColor(Color.parseColor("#ff0000"))
        }
        else -> {
            curTextView.text = "$changeAmount"
            curTextView.setTextColor(Color.parseColor("#ffff00"))
        }
    }

    fadeInOutView(curTextView, duration)
}