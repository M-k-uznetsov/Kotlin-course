package com.example.hw9

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.hw9.permission.DefaultSnackBar
import com.example.hw9.permission.EnablePermissionUI
import com.example.hw9.permission.PermissionViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*


@Composable fun UserRegionScreen(navController: NavController,view: MyViewModel) {

    val context = LocalContext.current
    var currency = remember{mutableStateOf(updateLocation(context))}
    val jsonData =view.movieList.observeAsState()
    val result = remember{ mutableStateOf( jsonData.value?.currency?.get(currency.value.toString()))}

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5C5CFF))

    ) {
        currency.value = updateLocation(context)
        result.value = jsonData.value?.currency?.get(currency.value.toString())
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Region screen\n", fontSize = 60.sp, color = Color.White)
            if(result.value?.name ==null){
                CustomText("error, the central bank does not provide data on the currency of your region","")
            }
            result.value?.name?.let { CustomText("name:", it) }
            result.value?.charCode?.let { CustomText("charCode:", it) }
            result.value?.id?.let { CustomText("id:", it) }
            result.value?.nominal?.let { CustomText("nominal:", it) }
            result.value?.numCode?.let { CustomText("numCode:", it) }
            result.value?.value?.let { CustomText("value:", it) }
            CustomText("latitude:\n", currency.value.toString())
            val scaffoldState = rememberScaffoldState()
            val permissionViewModel = PermissionViewModel()
            Row(horizontalArrangement= Arrangement.spacedBy(5.dp)) {
                Button(onClick = {
                    navController.navigate("JsonList")
                    currency.value = updateLocation(context)
                },
                    border = BorderStroke(1.dp, Color.White),
                    modifier = Modifier.weight(1f)) {
                        Text("see more values", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    Box(modifier= Modifier.weight(1f)) {
                        EnablePermissionUI(
                            scaffoldState = scaffoldState,
                            permissionViewModel = permissionViewModel
                        )
                    }
                }
            DefaultSnackBar(
                snackbarHostState = scaffoldState.snackbarHostState,
                onAction = {

                    scaffoldState.snackbarHostState.currentSnackbarData?.performAction()
                }
            )

        }
    }
}
@Composable fun CustomText(parameter:String, value: String){
    Row(){
        Text(modifier = Modifier
            .weight(1f)
            .offset(x = 50.dp),
            text = parameter,fontSize = 30.sp,color=Color.White)
        Text(modifier = Modifier.weight(1f), text = value,fontSize = 30.sp,color=Color.White)
    }
}
fun updateLocation(context: Context): Currency {
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
    fun coordinates(latitude:Double,longitude:Double){
        Log.d("testt", "before update: ${RegionData.region}")
        RegionData.region=getAddress(latitude, longitude)
        Log.d("testt", "after update: ${RegionData.region}")
    }

    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
        Log.d("testt", "before if: ${RegionData.region}")
     if(location!=null){

         coordinates(location.latitude,location.longitude)

     }
    }
    Log.d("testt", "after Task<Location>: ${RegionData.region}")
    return Currency.getInstance(Locale("", RegionData.region))
}

object RegionData{
    var region: String ="US"
}