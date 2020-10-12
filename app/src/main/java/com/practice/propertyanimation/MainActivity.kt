/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.practice.propertyanimation

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rotateButton.setOnClickListener {
            rotater()
        }

        translateButton.setOnClickListener {
            translater()
        }

        scaleButton.setOnClickListener {
            scaler()
        }

        fadeButton.setOnClickListener {
            fader()
        }

        colorizeButton.setOnClickListener {
            colorizer()
        }

        showerButton.setOnClickListener {
            shower()
        }
    }

    //TODO: Uncomment one of the options to try it
    private fun rotater() {

        // Using ObjectAnimator

//        ObjectAnimator.ofFloat(star, View.ROTATION, -360f, 0f).apply {
//            duration = 1000
//            disableViewDuringAnimation(rotateButton)
//            start()
//        }

        // Using Animator with XML

//        AnimatorInflater.loadAnimator(this, R.animator.anim_rotation).apply {
//            setTarget(star)
//            start()
//        }

        // Using ViewPropertyAnimator

//        star.animate()
//            .rotationBy(-360f)
//            .setDuration(1000)
//            .withStartAction { rotateButton.isEnabled = false }
//            .withEndAction { rotateButton.isEnabled = true }
//            .start()
    }

    private fun translater() {
        ObjectAnimator.ofFloat(star, View.TRANSLATION_X, 200f).apply {
            repeat()
            disableViewDuringAnimation(translateButton)
        }.start()
    }

    private fun scaler() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)

        ObjectAnimator.ofPropertyValuesHolder(star, scaleX, scaleY).apply {
            repeat()
            disableViewDuringAnimation(scaleButton)
        }.start()
    }

    private fun fader() {
        ObjectAnimator.ofFloat(star, View.ALPHA, 0f).apply {
            repeat()
            disableViewDuringAnimation(fadeButton)
        }.start()
    }

    private fun colorizer() {
        ObjectAnimator.ofArgb(star.parent, "backgroundColor", Color.BLACK, Color.RED).apply {
            duration = 1000
            repeat()
            disableViewDuringAnimation(colorizeButton)
        }.start()
    }

    private fun shower() {
        val container = star.parent as ViewGroup
        val containerWidth = container.width
        val containerHeight = container.height
        var starWidth = star.width.toFloat()
        var starHeight = star.height.toFloat()

        val newStar = AppCompatImageView(this)
        newStar.setImageResource(R.drawable.ic_star)
        newStar.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        container.addView(newStar)

        newStar.scaleX = Math.random().toFloat() * 1.5f + .1f
        newStar.scaleY = newStar.scaleX

        starWidth *= newStar.scaleX
        starHeight *= newStar.scaleY

        newStar.translationX = Math.random().toFloat() * containerWidth - starWidth / 2

        val mover = ObjectAnimator.ofFloat(
            newStar, View.TRANSLATION_Y, -starHeight, containerHeight + starHeight
        )
        mover.interpolator = AccelerateInterpolator(1f)

        val rotator = ObjectAnimator.ofFloat(
            newStar, View.ROTATION, (Math.random() * 1080).toFloat()
        )
        rotator.interpolator = LinearInterpolator()

        val set = AnimatorSet()
        set.playTogether(rotator, mover)
        set.duration = (Math.random() * 1500 + 500).toLong()
        set.doOnEnd { container.removeView(newStar) }
        set.start()
    }

    private fun ObjectAnimator.repeat() {
        repeatCount = 1
        repeatMode = ObjectAnimator.REVERSE
    }

    private fun ObjectAnimator.disableViewDuringAnimation(button: Button) =
        addListener(onStart = { button.isEnabled = false }, onEnd = { button.isEnabled = true })
}