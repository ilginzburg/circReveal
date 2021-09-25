package com.ginzburgworks.circularreveal

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.hypot
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private var isRevealed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener {
            if (!isRevealed) {
                reveal()
            } else {
                hide()
            }
        }
    }

    private fun reveal() {
        val x: Int = fab.x.roundToInt() + fab.width / 2
        val y: Int = fab.y.roundToInt() + fab.height / 2
        val startRadius = 0
        val endRadius = hypot(buttons_container.width.toDouble(), buttons_container.height.toDouble())

        val anim = ViewAnimationUtils.createCircularReveal(buttons_container, x, y, startRadius.toFloat(), endRadius.toFloat())
        anim.duration = 1500

        buttons_container.visibility = View.VISIBLE
        anim.start()
        isRevealed = true
    }

    private fun hide() {
        val x: Int = fab.x.roundToInt() + fab.width / 2
        val y: Int = fab.y.roundToInt() + fab.height / 2
        val startRadius: Int = main_container.width.coerceAtLeast(buttons_container.height)
        val endRadius = 0

        val anim = ViewAnimationUtils.createCircularReveal(buttons_container, x, y, startRadius.toFloat(), endRadius.toFloat())
        anim.duration = 1500

        //Слушатель конца анимации, когда анимация закончится, мы скроем View
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animator: Animator) {
                buttons_container.visibility = View.GONE
            }
        })
        anim.start()
        isRevealed = false
    }
}