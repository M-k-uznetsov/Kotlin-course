package com.example.hw_7

import CurrenciesAPI
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class MyViewModel : ViewModel() {
    val jsonFromCenterBank: MutableLiveData<CurrenciesAPI> = MutableLiveData<CurrenciesAPI>()

    init {
        loadJson()
    }


    fun loadJson() {
        viewModelScope.launch(Dispatchers.Default) {
            jsonFromCenterBank.postValue(
                Gson().fromJson(
                URL("https://www.cbr-xml-daily.ru/daily_json.js").readText(),
                CurrenciesAPI::class.java
            ))
        }
    }
}