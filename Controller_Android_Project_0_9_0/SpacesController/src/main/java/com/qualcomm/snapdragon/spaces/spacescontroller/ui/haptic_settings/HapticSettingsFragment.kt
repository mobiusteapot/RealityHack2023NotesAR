package com.qualcomm.snapdragon.spaces.spacescontroller.ui.haptic_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import com.qualcomm.snapdragon.spaces.spacescontroller.R
import com.qualcomm.snapdragon.spaces.spacescontroller.databinding.FragmentHapticSettingsBinding
import com.qualcomm.snapdragon.spaces.spacescontroller.util.Constants
import com.qualcomm.snapdragon.spaces.spacescontroller.util.SharedPreferenceManager
import com.qualcomm.snapdragon.spaces.spacescontroller.util.VibratorManager


class HapticSettingsFragment : Fragment() {

    private var _binding: FragmentHapticSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var vibratorManager: VibratorManager
    private var hasAmplitudeControl: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHapticSettingsBinding.inflate(inflater, container, false)

        sharedPreferenceManager = SharedPreferenceManager(requireActivity())
        vibratorManager = VibratorManager(requireActivity())
        hasAmplitudeControl = vibratorManager.hasAmplitudeControl()

        val defaultHapticEnabled = resources.getBoolean(R.bool.preference_haptic_enabled)
        val defaultHapticStrength = resources.getInteger(R.integer.preference_haptic_strength)

        val prefHapticEnabled: Boolean = sharedPreferenceManager.getValue(getString(R.string.preference_haptic_enabled), defaultHapticEnabled)
        _binding!!.hapticFeedback.isChecked = prefHapticEnabled
        _binding!!.hapticFeedback.setOnCheckedChangeListener { _, isChecked ->
            if (hasAmplitudeControl) {
                animateHapticStrength(isChecked, Constants.HAPTIC_ANIMATION_DURATION_MS)
            }
            sharedPreferenceManager.saveValue(getString(R.string.preference_haptic_enabled), isChecked)
        }

        val prefHapticStrength: Int = sharedPreferenceManager.getValue(getString(R.string.preference_haptic_strength), defaultHapticStrength)
        _binding!!.hapticStrengthSeekbar.progress = prefHapticStrength
        _binding!!.hapticStrengthSeekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean)
            { /* Intentionally left blank */ }
            override fun onStartTrackingTouch(seekBar: SeekBar)
            { /* Intentionally left blank */ }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val strength = seekBar.progress
                sharedPreferenceManager.saveValue(getString(R.string.preference_haptic_strength), strength)
                vibratorManager.performHapticFeedback(true, strength, Constants.HAPTIC_PRESS_DURATION_MS)
            }
        })

        if (hasAmplitudeControl) {
            animateHapticStrength(prefHapticEnabled, 0)
        } else {
            _binding?.hapticStrength!!.visibility = GONE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun animateHapticStrength (animateIn: Boolean, duration: Long) {
        if (animateIn) {
            scaleView(_binding?.hapticStrength!!, 0f, 1f, duration)
        } else {
            scaleView(_binding?.hapticStrength!!, 1f, 0f, duration)
        }
    }

    private fun scaleView(v: View, startScale: Float, endScale: Float, duration: Long) {
        val anim: Animation = ScaleAnimation(
            1f, 1f,
            startScale, endScale,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f
        )
        anim.fillAfter = true
        anim.duration = duration
        v.startAnimation(anim)
    }
}