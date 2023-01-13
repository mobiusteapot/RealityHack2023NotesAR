package com.qualcomm.snapdragon.spaces.spacescontroller.util

enum class DisplayMode {
    DARK, LIGHT, AUTO;

    companion object {
        fun fromInt(mode: Int): DisplayMode = when(mode) {
            0 -> DARK
            1 -> LIGHT
            else -> AUTO
        }

        fun toInt(mode: DisplayMode): Int = when(mode) {
            DARK -> 0
            LIGHT -> 1
            AUTO -> 2
        }
    }
}