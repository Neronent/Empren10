package com.example.empren10.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.empren10.R
import com.example.empren10.databinding.ActivityMenuBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.Navigation.findNavController

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val menuEmpren: BottomNavigationView = binding.bottomNavMenu
        setupWithNavController(menuEmpren, findNavController(this, R.id.frag_navgraph))

    }
}