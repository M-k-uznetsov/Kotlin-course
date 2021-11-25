package com.example.hw_7

import CurrenciesAPI
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import androidx.navigation.NavController
class MyViewModel : ViewModel() {
    val jsonFromCenterBank: MutableLiveData<CurrenciesAPI> by lazy {
        MutableLiveData<CurrenciesAPI>()
    }
    init {
        loadJson()
    }


    fun loadJson() {
        viewModelScope.launch(Dispatchers.Default) {
            jsonFromCenterBank.postValue(Gson().fromJson(
                URL("https://www.cbr-xml-daily.ru/daily_json.js").readText(),
                CurrenciesAPI::class.java
            ))
        }
    }
}

@Composable
fun JsonList(navController:NavController,view: MyViewModel) {
    val jsonData: CurrenciesAPI? by view.jsonFromCenterBank.observeAsState()
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5C5CFF))
            .horizontalScroll(rememberScrollState())
            .verticalScroll(rememberScrollState())

    ) {
        Column {
            jsonData?.currency?.values?.forEach() {
                it.name?.let { it1 ->
                    Text(it1, fontSize = 25.sp,
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate(route ="fullData/${it.charCode}")
                        })
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