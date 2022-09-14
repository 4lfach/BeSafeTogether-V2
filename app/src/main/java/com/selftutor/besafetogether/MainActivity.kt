package com.selftutor.besafetogether

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.selftutor.besafetogether.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var binding : ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
		val navController = navHostFragment.navController
		val bottomNavView = binding.bottomNavigationView

		NavigationUI.setupWithNavController(bottomNavView, navController)
	}
}