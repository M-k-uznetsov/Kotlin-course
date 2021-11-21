package com.example.hw_62
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF5C5CFF)),

            ) {
                Column {
                    Text(text="Myface.com\n",fontSize=60.sp,color= White)
                    val username = remember{mutableStateOf("")}
                    InputField(name = "username", inputString =username )
                    val password = remember{mutableStateOf("")}
                    InputField(name = "password", inputString =password )
                    val result: MutableState<Boolean?> =remember{mutableStateOf(null)}
                    Button(onClick = {
                        result.value = password.value=="admin"&&username.value=="admin"

                    }){
                        Text("confirm", fontSize = 25.sp)
                    }
                    if(result.value==true){
                        Text("data entry was successful", fontSize = 25.sp,color= Green)
                    }
                    if(result.value==false){
                        Text("data entry error check username or password", fontSize = 25.sp,color= Red)
                    }
                }
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
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth(),
        placeholder = { Text("Input $name!",fontSize=20.sp,color= White)},
        textStyle = TextStyle(fontSize =  28.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor= White,
            unfocusedBorderColor = Black
        )

    )
}
