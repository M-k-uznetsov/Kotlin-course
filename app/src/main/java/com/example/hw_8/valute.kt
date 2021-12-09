package com.example.hw_8

import CurrenciesAPI
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.*

@Composable
fun JsonList(navController:NavController,view: MyViewModel) {
    val context = LocalContext.current
    val jsonData = view.jsonFromCenterBank.observeAsState()
    val intent =  Intent(context,UpdateJsonService::class.java)
    intent.putExtra("jsonData", jsonData.value);
    val mTimer: Timer = Timer()
    var counter:Int = 0
    mTimer.scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            counter+=1
            //Если программа не работает, то удалите строчку ниже
            //intent.putExtra("jsonData", jsonData.value);
            Log.d("TIMER","repeat: $counter")
            context.startService(intent)
            context.stopService(intent)
        }
    }, 0, 5000)
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5C5CFF))
            .verticalScroll(rememberScrollState())

    ) {
        Column{

            jsonData.value?.currency?.values?.forEach() {
                it.name?.let { it1 ->
                    Text(it1, fontSize = 25.sp,
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate(route ="fullData/${it.charCode}")
                        })
                            .border(1.dp, Color.DarkGray)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun FullData(key:String,view: MyViewModel){
    val jsonData: CurrenciesAPI? by view.jsonFromCenterBank.observeAsState()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5C5CFF))
            .horizontalScroll(rememberScrollState())
            .verticalScroll(rememberScrollState())


    ) {
        Column {
            val result = jsonData?.currency?.get(key)
            result?.name?.let { Text("name: $it",fontSize = 25.sp) }
            result?.charCode?.let { Text("charCode: $it",fontSize = 25.sp) }
            result?.id?.let { Text("id: $it",fontSize = 25.sp) }
            result?.nominal?.let { Text("nominal: $it",fontSize = 25.sp) }
            result?.numCode?.let { Text("numCode: $it",fontSize = 25.sp) }
            result?.value?.let { Text("value: $it",fontSize = 25.sp) }
        }
    }

}


