package com.example.hw9
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        val viewModel =  ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MyViewModel::class.java)
        viewModel.getAllValute()
        setContent {
            val navController = rememberNavController()
            NavHost(navController= navController, startDestination = "MainScreen"){
                composable("MainScreen"){ MainScreen(navController,viewModel) }
                composable("UserRegionScreen"){UserRegionScreen(navController,viewModel)}
                composable("JsonList"){JsonList(navController,viewModel)}
                composable("fullData/{data}",  arguments = listOf(navArgument("data") { type = NavType.StringType })){
                        backStackEntry ->
                    backStackEntry.arguments!!.getString("data")?.let {
                        FullData(
                            it,viewModel
                        )
                    }
                }
            }
        }
    }
}
