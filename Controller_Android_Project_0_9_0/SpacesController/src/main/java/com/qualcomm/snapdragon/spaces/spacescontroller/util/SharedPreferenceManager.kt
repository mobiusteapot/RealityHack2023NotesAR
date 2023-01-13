package com.qualcomm.snapdragon.spaces.spacescontroller.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(
    activity: Activity
) {
    private val sharedPref: SharedPreferences

    init {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE)!!
    }

    fun <T> saveValue(id: String, value: T) {
        with (sharedPref.edit()) {
            when(value) {
                is Int -> putInt(id, value as Int)
                is Boolean -> putBoolean(id, value as Boolean)
                is String -> putString(id, value as String)
            }
            apply()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getValue(id: String, defaultValue: T): T {
        return when(defaultValue) {
            is Int -> sharedPref.getInt(id, defaultValue as Int) as T
            is Boolean -> sharedPref.getBoolean(id, defaultValue as Boolean) as T
            is String -> sharedPref.getString(id, defaultValue as String) as T
            else -> defaultValue
        }
    }
}