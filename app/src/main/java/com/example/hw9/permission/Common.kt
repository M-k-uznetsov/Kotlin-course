package com.example.hw9.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Common {
    companion object {
        fun checkIfPermissionGranted(context: Context, permission: String): Boolean {
            return (ContextCompat.checkSelfPermission(context, permission)) == PackageManager.PERMISSION_GRANTED
        }

        fun shouldShowPermissionRationale(context: Context, permission: String) : Boolean {
            val activity = context as Activity?
            if (activity == null)
                Log.d("Checking", "shouldShowPermissionRationale: Activity is null")

            return ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)
        }

        fun PackageManager.getMetadataKey(
            packageName: String,
            keyName: String
        ): String {
            val ai = this.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            val bundle = ai.metaData
            val key: String = bundle.getString(keyName)!!
            return key
        }
    }
}


