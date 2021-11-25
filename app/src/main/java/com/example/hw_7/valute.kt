package com.example.hw_7

import CurrenciesAPI
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import androidx.navigation.NavController
@Composable
fun JsonList(navController:NavController,view: MyViewModel) {
    view.loadJson()
    Column() {
        view.jsonFromCenterBank.observe(this, Observer{
            it.currency?.forEach {
                Text(it.value.name.toString())
            }
        })
        
    }
}

class MyViewModel : ViewModel() {
    val jsonFromCenterBank: MutableLiveData<CurrenciesAPI> by lazy {
        MutableLiveData<CurrenciesAPI>()
    }

    fun loadJson() {
        viewModelScope.launch(Dispatchers.Default) {
            jsonFromCenterBank.value = Gson().fromJson(
                URL("https://www.cbr-xml-daily.ru/daily_json.js").readText(),
                CurrenciesAPI::class.java
            )
        }
    }
}
@Composable
fun MainScreen(navController:NavController) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5C5CFF))
            .horizontalScroll(rememberScrollState())

    ) {
        Column {
            Text(text = "Myface.com\n", fontSize = 60.sp, color = Color.White)
            val username = remember { mutableStateOf("") }
            InputField(name = "username", inputString = username)
            val password = remember { mutableStateOf("") }
            InputField(name = "password", inputString = password)
            val result: MutableState<Boolean?> = remember { mutableStateOf(null) }
            Button(onClick = {
                result.value = password.value == "admin" && username.value == "admin"

            }) {
                Text("confirm", fontSize = 25.sp)
            }
            if (result.value == true) {
                Text("data entry was successful", fontSize = 25.sp, color = Color.Green)
                navController.navigate("JsonList")
            }
            if (result.value == false) {
                Text(
                    "data entry error check username or password",
                    fontSize = 25.sp,
                    color = Color.Red
                )
            }
        }
    }
}
@Composable
fun InputField(name: String, inputString:MutableState<String>) {
    OutlinedTextField(
        inputString.value,
        {
            inputString.value = it
        },
        singleLine= true,
        modifier= Modifier
            //.horizontalScroll(rememberScrollState())
            .fillMaxWidth(),
        placeholder = { Text("Input $name!",fontSize=20.sp,color= Color.White)},
        textStyle = TextStyle(fontSize =  28.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor= Color.White,
            unfocusedBorderColor = Color.Black
        )

    )
}