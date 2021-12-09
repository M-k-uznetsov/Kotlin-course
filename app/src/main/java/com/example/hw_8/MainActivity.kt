package com.example.hw_8
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewJsonFromCenterBank:MyViewModel = viewModel<MyViewModel>()
            val navController = rememberNavController()
            NavHost(navController= navController, startDestination = "MainScreen"){
                composable("MainScreen"){ MainScreen(navController) }
                composable("JsonList"){JsonList(navController,viewJsonFromCenterBank)}
                composable("fullData/{data}",  arguments = listOf(navArgument("data") { type = NavType.StringType })){
                        backStackEntry ->
                    backStackEntry.arguments!!.getString("data")?.let {
                        FullData(
                            it,viewJsonFromCenterBank
                        )
                    }
                }
            }
        }
    }
}
