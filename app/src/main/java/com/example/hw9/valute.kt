package com.example.hw9


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun JsonList(navController:NavController,view: MyViewModel) {
    val jsonData = view.movieList.observeAsState()
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5C5CFF))
            .verticalScroll(rememberScrollState()),

    ) {
        Column(){
            jsonData.value?.currency?.values?.forEach()
            {
                it.name?.let { it1 ->
                    Text(it1, fontSize = 25.sp,color=Color.White,
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
    val jsonData = view.movieList.observeAsState()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5C5CFF))
            .horizontalScroll(rememberScrollState())
            .verticalScroll(rememberScrollState())


    ) {
        Column {
            val result = jsonData.value?.currency?.get(key)
            result?.name?.let { Text("name: $it",fontSize = 25.sp,color=Color.White) }
            result?.charCode?.let { Text("charCode: $it",fontSize = 25.sp,color=Color.White) }
            result?.id?.let { Text("id: $it",fontSize = 25.sp,color=Color.White) }
            result?.nominal?.let { Text("nominal: $it",fontSize = 25.sp,color=Color.White) }
            result?.numCode?.let { Text("numCode: $it",fontSize = 25.sp,color=Color.White) }
            result?.value?.let { Text("value: $it",fontSize = 25.sp,color=Color.White) }
        }
    }
}


