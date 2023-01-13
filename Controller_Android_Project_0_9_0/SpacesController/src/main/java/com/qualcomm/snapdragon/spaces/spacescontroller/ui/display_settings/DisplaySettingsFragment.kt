package com.qualcomm.snapdragon.spaces.spacescontroller.ui.display_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.qualcomm.snapdragon.spaces.spacescontroller.R
import com.qualcomm.snapdragon.spaces.spacescontroller.databinding.FragmentDisplaySettingsBinding
import com.qualcomm.snapdragon.spaces.spacescontroller.util.DisplayMode
import com.qualcomm.snapdragon.spaces.spacescontroller.util.SharedPreferenceManager

class DisplaySettingsFragment : Fragment() {

    private var _binding: FragmentDisplaySettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDisplaySettingsBinding.inflate(inflater, container, false)

        sharedPreferenceManager = SharedPreferenceManager(requireActivity())

        val defaultDisplayMode = resources.getInteger(R.integer.preference_display_mode)
        when(DisplayMode.fromInt(sharedPreferenceManager.getValue(getString(R.string.preference_display_mode), defaultDisplayMode))) {
            DisplayMode.DARK -> _binding!!.radioDarkMode.isChecked = true
            DisplayMode.LIGHT -> _binding!!.radioLightMode.isChecked = true
            DisplayMode.AUTO -> _binding!!.radioAutoMode.isChecked = true
        }

        _binding!!.radioGroupMode.setOnCheckedChangeListener { _, i ->
            when(i) {
                R.id.radio_dark_mode -> {
                    sharedPreferenceManager.saveValue(
                        getString(R.string.preference_display_mode),
                        DisplayMode.toInt(DisplayMode.DARK)
                    )
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                R.id.radio_light_mode -> {
                    sharedPreferenceManager.saveValue(
                        getString(R.string.preference_display_mode),
                        DisplayMode.toInt(DisplayMode.LIGHT)
                    )
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                R.id.radio_auto_mode -> {
                    sharedPreferenceManager.saveValue(
                        getString(R.string.preference_display_mode),
                        DisplayMode.toInt(DisplayMode.AUTO)
                    )
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}