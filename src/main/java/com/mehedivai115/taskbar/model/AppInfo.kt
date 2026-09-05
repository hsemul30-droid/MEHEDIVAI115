package com.mehedivai115.taskbar.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val packageName: String,
    val label: String,
    val icon: Drawable,
    var isOpened: Boolean = false,
    var isMinimized: Boolean = false,
    var windowWidth: Int = 800,
    var windowHeight: Int = 600,
    var windowX: Int = 100,
    var windowY: Int = 100,
    var zIndex: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AppInfo) return false
        return packageName == other.packageName
    }

    override fun hashCode(): Int {
        return packageName.hashCode()
    }
}