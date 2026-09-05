package com.mehedivai115.taskbar.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

object AppLauncherUtil {
    
    fun launchApp(context: Context, packageName: String): Boolean {
        return try {
            val intent = context.packageManager.getLaunchIntentForPackage(packageName)
            if (intent != null) {
                context.startActivity(intent)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getApplicationInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}