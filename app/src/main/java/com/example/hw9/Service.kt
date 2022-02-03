package com.example.hw9

import CurrenciesAPI
import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.gson.Gson
import java.net.URL

private const val TAG = "MyIntentService"
class UpdateJsonService : IntentService("MyIntentService") {

    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onHandleIntent(intent: Intent?) {
        val jsonFromActivity:CurrenciesAPI?= intent?.getParcelableExtra("jsonData")
        if (jsonFromActivity != null) {
            Log.d(TAG,"from Activity ${jsonFromActivity.date}")
        }
        Log.d(TAG,"service started")
        val data: CurrenciesAPI? = Gson().fromJson(
            URL("https://www.cbr-xml-daily.ru/daily_json.js").readText(),
            CurrenciesAPI::class.java)
        if(jsonFromActivity?.date != data?.date ) {
            val channel = NotificationChannel("123445596", "123445596", NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            val builder = NotificationCompat.Builder(this, "123445596")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Обновление данных")
                .setContentText("Произошло изменение данных, проведите обновление")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            with(NotificationManagerCompat.from(this)) {
                notify(123445596, builder.build())
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"service destroy")
    }
}