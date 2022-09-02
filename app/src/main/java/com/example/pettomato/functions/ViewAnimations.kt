package com.example.pettomato.functions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView

// This file contains top level functions relating to the animation of view objects.

// Animates the given view to fade in.
fun fadeInView(view: View, duration: Long) {
    // Preconditions
    view.clearAnimation()
    view.visibility = View.VISIBLE
    view.alpha = 0f

    // Animation
    view.animate()
        .alpha(1f)
        .setDuration(duration)
        .setListener(null)
}

// Animates the given view to fade out.
fun fadeOutView(view: View, duration: Long) {
    view.clearAnimation()
    view.visibility = View.VISIBLE
    view.alpha = 1f

    view.animate()
        .alpha(0f)
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter() {
            var isCancelled: Boolean = false

            override fun onAnimationCancel(animation: Animator?) {
                isCancelled = true
            }

            override fun onAnimationEnd(animation: Animator) {
                if(!isCancelled) view.visibility = View.INVISIBLE
            }
        })
}

// Animates the given view to fade in and out.
fun fadeInOutView(view: View, duration: Long) {
    view.clearAnimation()
    view.visibility = View.VISIBLE
    view.alpha = 0f

    view.animate()
        .alpha(1f)
        .setDuration(duration/2)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                fadeOutView(view, duration/2)
            }
        })
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

// Animates the given view to spin, become smaller, and fade out at the same time.
// Waits 700 milliseconds before playing death animation for 2 seconds.
fun animatePetDeath(view: View) {
    view.animate()
        .translationXBy(0f) // Wait before playing animation
        .setDuration(700)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                view.animate() // Play actual animation
                    .rotationBy(1080f)
                    .alpha(0f)
                    .scaleX(0.01f)
                    .scaleY(0.01f)
                    .setDuration(2000)
                    .setListener(null)
            }
        })
}