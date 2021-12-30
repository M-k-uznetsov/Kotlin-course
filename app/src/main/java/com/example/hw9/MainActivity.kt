package com.example.hw9
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
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val retrofitService = RetrofitService.getInstance()
            val mainRepository = MainRepository(retrofitService)
            val viewModel =  ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MyViewModel::class.java)
            viewModel.getAllMovies() //ERROR: an error occurs when trying to load data using getAllMovies()
            //old realization
            //val viewJsonFromCenterBank:MyViewModel = viewModel<MyViewModel>()
            val navController = rememberNavController()
            NavHost(navController= navController, startDestination = "UserRegionScreen"){
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
