package com.example.hw_7
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    private lateinit var viewJsonFromCenterBank:MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewJsonFromCenterBank = ViewModelProvider(this).get(MyViewModel::class.java)
            val navController = rememberNavController()
            NavHost(navController= navController, startDestination = "MainScreen"){
                composable("MainScreen"){ MainScreen(navController) }
                composable("JsonList"){JsonList(navController,viewJsonFromCenterBank)}

            }

        }
    }
}

