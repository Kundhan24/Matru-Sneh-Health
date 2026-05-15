package com.matrusneh.health.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.matrusneh.health.R
import com.matrusneh.health.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as androidx.navigation.fragment.NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        // Handle notification deep links
        intent.getStringExtra("nav_target")?.let { target ->
            when (target) {
                "checkup" -> navController.navigate(R.id.checkupFragment)
                "kick" -> navController.navigate(R.id.kickFragment)
            }
        }
    }
}
