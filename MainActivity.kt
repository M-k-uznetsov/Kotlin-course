package com.example.hw_5

import CurrenciesAPI
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast


import com.google.gson.Gson
import java.io.File


class MainActivity : AppCompatActivity() {
    val TAG = "MyApp"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(applicationContext, "onCreate() now", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(applicationContext, "onResume() now", Toast.LENGTH_SHORT).show()
        //var file = File("JJsonFile.json").writeText ("woooooooow")

    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(applicationContext, "onPause() now", Toast.LENGTH_SHORT).show()
    }
    override fun onRestart() {
        super.onRestart()
        Toast.makeText(applicationContext, "onRestart() now", Toast.LENGTH_SHORT).show()
        val jsonFileString = applicationContext.assets.open("JsonFile.json").bufferedReader().use {
            it.readText()
        }
        val jsonFromCenterBank = Gson().fromJson(jsonFileString, CurrenciesAPI::class.java)
        if (jsonFromCenterBank.currency == null) return
        val res= jsonFromCenterBank.currency.entries
            .filter { (key,value)->value.value?.toFloat()!!>value.previous?.toFloat()!! }
            .sortedBy{ it.value.value?.toFloat()!!-it.value.previous?.toFloat()!!}
        var averagePercentage =jsonFromCenterBank.currency.entries.sumOf { (it.value.value?.toDouble()!! - it.value.previous?.toDouble()!!) / 100 }
        averagePercentage/=jsonFromCenterBank.currency.entries.size
        Log.i(TAG,"валюты, которые повысились в стоимости и их повышения:")
        res.forEach() {
            Log.i(TAG,"${it.key} ${it.value.value?.toFloat()!! - it.value.previous?.toFloat()!!}")
        }
        Log.i(TAG,"валюта с наибольшим процентом: ${res.last().key}")
        Log.i(TAG,"валюта с наименьшим процентом: ${res.first().key}")
        Log.i(TAG,"средний процент повышения: $averagePercentage")
        Toast.makeText(applicationContext, "file 'JsonFile.json' processed", Toast.LENGTH_SHORT).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(applicationContext, "onDestroy() now", Toast.LENGTH_SHORT).show()

    }
}



