package com.example.hw_8

import android.app.PendingIntent
import android.content.Intent
import android.provider.CalendarContract
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.*


@Composable
fun MainScreen(navController: NavController) {

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5C5CFF))

    ) {
        Column {
            Text(text = "Valute from CBR\n", fontSize = 60.sp, color = Color.White)
            val username = remember { mutableStateOf("") }
            InputField(name = "username", inputString = username)
            val password = remember { mutableStateOf("") }
            InputField(name = "password", inputString = password)
            val result: MutableState<Boolean?> = remember { mutableStateOf(null) }
            Button(onClick = {
                result.value = password.value == "admin" && username.value == "admin"
                if (result.value == true) {
                    navController.navigate("JsonList")
                }
            }) {
                Text("confirm", fontSize = 25.sp)
            }
            if (result.value == true) {
                Text("data entry was successful", fontSize = 25.sp, color = Color.Green)
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
fun InputField(name: String, inputString: MutableState<String>) {
    OutlinedTextField(
        inputString.value,
        {
            inputString.value = it
        },
        singleLine= true,
        modifier= Modifier
            //.horizontalScroll(rememberScrollState())
            .fillMaxWidth(),
        placeholder = { Text("Input $name!",fontSize=20.sp,color= Color.White) },
        textStyle = TextStyle(fontSize =  28.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor= Color.White,
            unfocusedBorderColor = Color.Black
        )

    )
}