package com.example.hw9.permission

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.hw9.permission.PermissionAction
import com.example.hw9.permission.PermissionUI
import com.example.hw9.permission.PermissionViewModel
import kotlinx.coroutines.launch

@Composable
fun EnablePermissionUI(
    scaffoldState: ScaffoldState,
    permissionViewModel: PermissionViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val performLocationAction by permissionViewModel.performLocationAction.collectAsState()

    if (performLocationAction) {
        PermissionUI(
            context = context,
            permission = Manifest.permission.ACCESS_FINE_LOCATION,
            permissionRationale = "In order to get the current location, we require the location permission to be granted.",
            scaffoldState = scaffoldState,
        ) { permissionAction ->
            when (permissionAction) {
                is PermissionAction.OnPermissionGranted -> {
                    permissionViewModel.setPerformLocationAction(false)
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Location Permission Granted!")

                    }
                }
                is PermissionAction.OnPermissionDenied -> {
                    permissionViewModel.setPerformLocationAction(false)
                    scope.launch {

                        scaffoldState.snackbarHostState.showSnackbar("Location Permission Denied!")
                    }
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                permissionViewModel.setPerformLocationAction(true)
            }
        ) {
            Text(text = "Enable Location")
        }
    }
}
