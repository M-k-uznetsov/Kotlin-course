package com.example.hw9.permission

sealed class PermissionAction {
    object OnPermissionGranted: PermissionAction()
    object OnPermissionDenied: PermissionAction()
}