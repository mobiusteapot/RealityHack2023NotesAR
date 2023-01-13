package com.qualcomm.snapdragon.spaces.spacescontroller.ui.recenter_controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qualcomm.snapdragon.spaces.spacescontroller.databinding.FragmentRecenterControllerBinding

class RecenterControllerFragment : Fragment() {

    private var _binding: FragmentRecenterControllerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecenterControllerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}