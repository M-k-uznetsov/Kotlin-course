package com.example.hw_8

import CurrenciesAPI
import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Bundle
import android.os.Handler
import java.lang.UnsupportedOperationException
import java.text.SimpleDateFormat
import java.util.*


class MyViewModel : ViewModel() {
    val jsonFromCenterBank: MutableLiveData<CurrenciesAPI> = MutableLiveData<CurrenciesAPI>()

    init {
        loadJson()
    }

    private fun loadJson() {
        viewModelScope.launch(Dispatchers.Default) {
            jsonFromCenterBank.postValue(
                Gson().fromJson(
                URL("https://www.cbr-xml-daily.ru/daily_json.js").readText(),
                CurrenciesAPI::class.java
            ))
        }
    }
}
