package com.qualcomm.snapdragon.spaces.spacescontroller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.qualcomm.snapdragon.spaces.spacescontroller.databinding.ActivityControllerBinding
import com.qualcomm.snapdragon.spaces.spacescontroller.util.DisplayMode
import com.qualcomm.snapdragon.spaces.spacescontroller.util.Permission
import com.qualcomm.snapdragon.spaces.spacescontroller.util.SharedPreferenceManager

class ControllerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityControllerBinding

    private lateinit var permission: Permission
    private lateinit var sharedPreferenceManager: SharedPreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permission = Permission()
        permission.askMicrophonePermission(this@ControllerActivity)
        permission.askCameraPermission(this@ControllerActivity)

        sharedPreferenceManager = SharedPreferenceManager(this)
        val defaultDisplayMode = resources.getInteger(R.integer.preference_display_mode)
        when(DisplayMode.fromInt(sharedPreferenceManager.getValue(getString(R.string.preference_display_mode), defaultDisplayMode))) {
            DisplayMode.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            DisplayMode.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DisplayMode.AUTO -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

        binding = ActivityControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarController.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_controller)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun super.onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray?) {
        permission.handlePermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_controller)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}