package com.example.shopbee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.shopbee.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private lateinit var toolbar: Toolbar
    private lateinit var navController : NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragHost) as
                NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.cartFragment,
                R.id.settingsFragment
            )
        )

        toolbar = findViewById(R.id.toolbar)

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration )

        findViewById<BottomNavigationView>(R.id.btm_menuu).setupWithNavController(navController)



    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragHost)
        return navController.navigateUp(appBarConfiguration)
                ||onSupportNavigateUp()
    }
}