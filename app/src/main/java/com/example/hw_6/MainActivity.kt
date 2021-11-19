package com.example.hw_6

import CurrenciesAPI
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import java.net.URL
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch(Dispatchers.Default) {
            val jsonFromCenterBank = Gson().fromJson(
                URL("https://www.cbr-xml-daily.ru/daily_json.js").readText(),
                CurrenciesAPI::class.java
            )
            if (jsonFromCenterBank.currency == null) return@launch
            withContext(Dispatchers.Main) {
                val myStrings = mutableListOf<String>()
                jsonFromCenterBank.currency.values.forEach { element ->
                    element.name?.let { myStrings.add(it) }
                }

                userlist.adapter = ArrayAdapter(this@MainActivity,
                    android.R.layout.simple_list_item_1, myStrings
                )
            }
        }
    }
}

