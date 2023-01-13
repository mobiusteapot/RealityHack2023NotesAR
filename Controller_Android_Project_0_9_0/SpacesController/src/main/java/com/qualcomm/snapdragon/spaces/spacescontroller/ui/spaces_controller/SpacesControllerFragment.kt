package com.qualcomm.snapdragon.spaces.spacescontroller.ui.spaces_controller

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.qualcomm.qti.openxr.input.data.*
import com.qualcomm.qti.openxr.input.data.fusion.PoseProducer
import com.qualcomm.qti.openxr.input.data.fusion.SimplePoseProducer
import com.qualcomm.qti.openxr.input.spaces.client.*
import com.qualcomm.snapdragon.spaces.spacescontroller.R
import com.qualcomm.snapdragon.spaces.spacescontroller.databinding.FragmentSpacesControllerBinding
import com.qualcomm.snapdragon.spaces.spacescontroller.util.Constants
import com.qualcomm.snapdragon.spaces.spacescontroller.util.SharedPreferenceManager
import com.qualcomm.snapdragon.spaces.spacescontroller.util.VibratorManager
import kotlin.math.abs


class SpacesControllerFragment : Fragment(), SpacesInputViewsFactory {

    private var _binding: FragmentSpacesControllerBinding? = null

    private var inputClient: SpacesInputClient? = null
    private lateinit var poseProducer: PoseProducer
    private lateinit var systemUiController: SystemUiController
    private lateinit var headPose: XrDevicePosef

    private var xyStartingPointSet = false
    private var onTouched = false
    private var prevX = 0f
    private var prevY = 0f
    private var resetDoubleTap = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var vibratorManager: VibratorManager
    private var hapticEnabled: Boolean = true
    private var hapticStrength: Int = 255

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpacesControllerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedPreferenceManager = SharedPreferenceManager(requireActivity())
        vibratorManager = VibratorManager(requireActivity())

        val defaultHapticEnabled = resources.getBoolean(R.bool.preference_haptic_enabled)
        val defaultHapticStrength = resources.getInteger(R.integer.preference_haptic_strength)
        hapticEnabled = sharedPreferenceManager.getValue(getString(R.string.preference_haptic_enabled), defaultHapticEnabled)
        hapticStrength = sharedPreferenceManager.getValue(getString(R.string.preference_haptic_strength), defaultHapticStrength)

        inputClient = SpacesInputClient(requireContext())
        inputClient?.blockingBind()

        val bindings = ProfileBindingUtils.MICROSOFT_MIXED_REALITY_PROFILE.mapValues {
            InputViewHolder(it.value.associateWith { false } as MutableMap<Int, Boolean>)
        }
        assignViewBindings(bindings)
        if (!ProfileBindingUtils.isValid(bindings)) {
            throw IllegalArgumentException("Invalid bindings")
        }

        // Setup handlers
        for (binding in bindings) {
            val holder = binding.value
            if (holder.view == null) {
                continue
            }
            val components = holder.components
            val touchListener = InputViewTouchListener(binding.key)
            holder.view?.setOnTouchListener(touchListener)
            for (component in components) {
                if (component.value) {
                    when (component.key) {
                        Component.CLICK -> touchListener.setClickHandler { timestamp, identifier, clicked ->
                            sendClickEvent(timestamp, identifier, clicked)
                            if (hapticEnabled) {
                                vibratorManager.performHapticFeedback(clicked, hapticStrength, Constants.HAPTIC_PRESS_DURATION_MS)
                            }
                            holder.view?.isPressed = clicked
                            true
                        }
                        Component.TOUCH -> touchListener.setTouchHandler { timestamp, identifier, touched ->
                            sendTouchEvent(timestamp, identifier, touched)
                            onTouched = touched
                            true
                        }
                        Component.XY -> touchListener.setXYScalarHandler { timestamp, identifier, x, y ->
                            sendXYScalarEvent(timestamp, identifier, x, y)
                            if (onTouched) {
                                if (!xyStartingPointSet) {
                                    prevX = x
                                    prevY = y
                                    xyStartingPointSet = true
                                } else {
                                    if (hapticEnabled && canPerformXYHaptic(x, y)) {
                                        vibratorManager.performHapticFeedback(true, hapticStrength, Constants.HAPTIC_MOVE_DURATION_MS)
                                    }
                                }
                            } else {
                                xyStartingPointSet = false
                            }
                            true
                        }
                        Component.VALUE -> touchListener.setValueHandler { timestamp, identifier, value ->
                            sendValueEvent(timestamp, identifier, value)
                            true
                        }
                        else -> {
                        }
                    }
                }
            }
        }

        _binding?.spacesReCenter?.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    if (hapticEnabled) {
                        vibratorManager.performHapticFeedback(true, hapticStrength, Constants.HAPTIC_PRESS_DURATION_MS)
                    }
                    view.isPressed = true
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (hapticEnabled) {
                        vibratorManager.performHapticFeedback(false, hapticStrength, Constants.HAPTIC_PRESS_DURATION_MS)
                    }
                    if (resetDoubleTap) {
                        startPoseProducer()
                    }
                    resetDoubleTap = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        resetDoubleTap = false
                    }, 1500)

                    view.isPressed = false
                    true
                }
                else -> {
                    false
                }
            }
        };

        systemUiController = SystemUiController(requireActivity().window.decorView)
        setupPoseProducer()

        removeCroppedTilesFromTrackpadBackground()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun assignViewBindings(bindings: Map<Int, InputViewHolder>) {
        val trackpadViewHolder = bindings[Identifier.TRACKPAD]
        trackpadViewHolder?.view = _binding?.spacesTrackpad
        trackpadViewHolder?.components?.set(Component.XY, true)
        trackpadViewHolder?.components?.set(Component.CLICK, true)
        trackpadViewHolder?.components?.set(Component.TOUCH, true)

        val selectViewHolder = bindings[Identifier.MENU]
        selectViewHolder?.view = _binding?.spacesMenu
        selectViewHolder?.components?.set(Component.CLICK, true)
    }

    override fun inflate(context: Context): View {
        return _binding!!.root
    }

    override fun power(): SwitchCompat? {
        return null
    }

    override fun requestFullscreen(): Boolean = true

    private fun sendClickEvent(timestamp: Long, @Identifier identifier: Int, clicked: Boolean) {
        val builder = XrDeviceEvent.Builder()
        builder.setTimestamp(timestamp)
            .setIdentifier(identifier)
            .setComponent(Component.CLICK)
            .setValue(clicked)
        inputClient?.updateEvent(builder.build())
    }

    private fun sendTouchEvent(timestamp: Long, @Identifier identifier: Int, touched: Boolean) {
        val builder = XrDeviceEvent.Builder()
        builder.setTimestamp(timestamp)
            .setIdentifier(identifier)
            .setComponent(Component.TOUCH)
            .setValue(touched)
        inputClient?.updateEvent(builder.build())
    }

    private fun sendXYScalarEvent(
        timestamp: Long,
        @Identifier identifier: Int,
        x: Float,
        y: Float
    ) {
        val builder = XrDeviceEvent.Builder()
        builder.setTimestamp(timestamp)
            .setIdentifier(identifier)
            .setComponent(Component.XY)
            .setValue(XrVector2f(x, y))
        inputClient?.updateEvent(builder.build())
    }

    private fun sendValueEvent(timestamp: Long, @Identifier identifier: Int, value: Float) {
        val builder = XrDeviceEvent.Builder()
        builder.setTimestamp(timestamp)
            .setIdentifier(identifier)
            .setComponent(Component.VALUE)
            .setValue(value)
        inputClient?.updateEvent(builder.build())
    }

    private fun setupPoseProducer() {
        poseProducer = SimplePoseProducer(
            activity?.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager, 90
        )

        if (!poseProducer.initialize()) {
            Log.e(SpacesControllerFragment.TAG, "Failed to initialize pose producer")
        }

        poseProducer.addObserver { _, pose ->
            val p = pose as XrDevicePosef
            p.orientation = XrQuaternionf.multiply(headPose.orientation, p.orientation)
            inputClient?.updatePose(p)
        }
    }

    override fun onResume() {
        super.onResume()

        startPoseProducer()
        if (requestFullscreen()) {
            systemUiController.hideAutomatically()
        }
    }

    override fun onPause() {
        super.onPause()

        stopPoseProducer()
        if (requestFullscreen()) {
            systemUiController.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        inputClient?.unbind()
    }

    private fun startPoseProducer() {
        inputClient?.setConnected(true)
        if (inputClient != null) {
            headPose = inputClient!!.getHeadPose()
        }
        poseProducer.start()
    }

    private fun stopPoseProducer() {
        inputClient?.setConnected(false)
        poseProducer.stop()
    }


    private fun canPerformXYHaptic(x: Float, y: Float) : Boolean {
        if (abs(prevX - x) > 0.1f || abs(prevY - y) > 0.1f) {
            prevX = x
            prevY = y
            return true
        }
        return false
    }

    private fun removeCroppedTilesFromTrackpadBackground() {
        val dotTileDrawable = ResourcesCompat.getDrawable(resources, R.drawable.trackpad_dot_tile, null)!!
        val dotTileHeight = dotTileDrawable.intrinsicHeight
        val dotTileWidth = dotTileDrawable.intrinsicWidth

        binding.spacesTrackpadTiles.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val trackpadTileBackgroundHeight: Int = binding.spacesTrackpadTiles.height
                val trackpadTileBackgroundWidth: Int = binding.spacesTrackpadTiles.width

                val numberOfVerticalDots: Int = trackpadTileBackgroundHeight.div(dotTileHeight)
                val numberOfHorizontalDots: Int = trackpadTileBackgroundWidth.div(dotTileWidth)

                val layoutParams = LinearLayoutCompat.LayoutParams(numberOfHorizontalDots * dotTileWidth, numberOfVerticalDots * dotTileHeight)
                binding.spacesTrackpadTiles.layoutParams = layoutParams
                binding.spacesTrackpadTiles.invalidate()
                binding.spacesTrackpadTiles.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    companion object {
        const val TAG = "SpacesControllerFragment"
    }
}