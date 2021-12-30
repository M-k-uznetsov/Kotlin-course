package com.example.hw9

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.example.hw9.permission.DefaultSnackBar
import com.example.hw9.permission.EnablePermissionUI
import com.example.hw9.permission.PermissionViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*


@Composable fun UserRegionScreen(navController: NavController,view: MyViewModel) {

    val context = LocalContext.current
    var currency = remember{mutableStateOf(myFun(context))}
    val jsonData =view.movieList.observeAsState()

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5C5CFF))

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "This will be user region", fontSize = 60.sp, color = Color.White)
            val result = jsonData.value?.get(0)?.currency?.get(currency.value.toString())
            result?.name?.let { Text("name: $it",fontSize = 30.sp,color=Color.White) }
            result?.charCode?.let { Text("charCode: $it",fontSize = 25.sp,color=Color.White) }
            result?.id?.let { Text("id: $it",fontSize = 25.sp,color=Color.White) }
            result?.nominal?.let { Text("nominal: $it",fontSize = 25.sp,color=Color.White) }
            result?.numCode?.let { Text("numCode: $it",fontSize = 25.sp,color=Color.White) }
            result?.value?.let { Text("value: $it",fontSize = 25.sp,color=Color.White) }
            Text("latitude: ${currency.value.toString()}", fontSize = 25.sp, color = Color.White)

            Button(onClick = {
                navController.navigate("JsonList")
                currency.value = myFun(context)
            }) {
                Text("see more values", fontSize = 25.sp)

            }
            val scaffoldState = rememberScaffoldState()
            val permissionViewModel = PermissionViewModel()

            EnablePermissionUI(
                scaffoldState = scaffoldState,
                permissionViewModel = permissionViewModel
            )
            DefaultSnackBar(
                snackbarHostState = scaffoldState.snackbarHostState,
                onAction = {

                    scaffoldState.snackbarHostState.currentSnackbarData?.performAction()
                }
            )

        }
    }
}

fun myFun(context: Context): Currency {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ){
        return Currency.getInstance(Locale("", "US"))
    }
    fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(context)
        val list = geocoder.getFromLocation(lat, lng, 1)
        return list[0].countryCode

    }
    var region = "NO"
    fun coordinates(latitude:Double,longitude:Double){
        Log.d("testt", "before update: $region")
        region=getAddress(latitude, longitude)
        Log.d("testt", "after update: $region")
    }

    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
     if(location!=null){

         coordinates(location.latitude,location.longitude)
         //ERROR: coordinates are not saved
     }
    }
    Log.d("testt", "after Task<Location>: $region")
    return Currency.getInstance(Locale("", region))
}