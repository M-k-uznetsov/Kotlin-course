package com.example.hw_6

import CurrenciesAPI
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val TAG = "MyApp"
        val filename = "JsonFile.json"
        val jsonFileString = applicationContext.assets.open(filename).bufferedReader().use {
            it.readText()
        }
        val jsonFromCenterBank = Gson().fromJson(jsonFileString, CurrenciesAPI::class.java)
        if (jsonFromCenterBank?.currency == null){
            Log.e(TAG,"file error")
            return
        }
        val myStrings = mutableListOf<String>()
        jsonFromCenterBank.currency.values.forEach{ element ->
            element.name?.let { myStrings.add(it) }
        }

        var mListView = findViewById<ListView>(R.id.userlist)
        val arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, myStrings)
        mListView.adapter = arrayAdapter
    }
}

