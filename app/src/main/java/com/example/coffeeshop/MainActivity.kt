package com.example.coffeeshop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.coffeeshop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // or findViewById

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
            ?.findNavController()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    navController?.navigate(R.id.homeFragment)
                    true
                }
                R.id.nav_cart -> {
                    navController?.navigate(R.id.cartFragment)
                    true
                }
                R.id.nav_profile -> {
                    navController?.navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }

    }
}
