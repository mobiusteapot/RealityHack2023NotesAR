package com.qualcomm.snapdragon.spaces.spacescontroller.util

import android.app.Activity
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator

class VibratorManager(
    activity: Activity
) {
    private val vibrator: Vibrator

    init {
        vibrator = activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    fun performHapticFeedback(clicked: Boolean, strength: Int, duration: Long) {
        vibrator.cancel()

        var hapticDuration = duration
        if (!clicked) {
            hapticDuration /= 2
        }

        val effect = VibrationEffect.createOneShot(
            hapticDuration, strength
        )
        vibrator.vibrate(effect)
    }

    fun hasAmplitudeControl(): Boolean {
        return vibrator.hasAmplitudeControl()
    }
}