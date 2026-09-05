package com.mehedivai115.taskbar.util

import android.content.Context
import android.content.SharedPreferences
import com.mehedivai115.taskbar.model.AppInfo
import org.json.JSONArray
import org.json.JSONObject

class PreferenceUtil(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "mehedivai115_prefs",
        Context.MODE_PRIVATE
    )
    
    fun saveWindowPosition(packageName: String, x: Int, y: Int, width: Int, height: Int) {
        val json = JSONObject().apply {
            put("x", x)
            put("y", y)
            put("width", width)
            put("height", height)
        }
        prefs.edit().putString("window_$packageName", json.toString()).apply()
    }
    
    fun getWindowPosition(packageName: String): Map<String, Int>? {
        val json = prefs.getString("window_$packageName", null) ?: return null
        return try {
            val obj = JSONObject(json)
            mapOf(
                "x" to obj.getInt("x"),
                "y" to obj.getInt("y"),
                "width" to obj.getInt("width"),
                "height" to obj.getInt("height")
            )
        } catch (e: Exception) {
            null
        }
    }
    
    fun saveFavouriteApps(apps: List<String>) {
        val json = JSONArray(apps)
        prefs.edit().putString("favourite_apps", json.toString()).apply()
    }
    
    fun getFavouriteApps(): List<String> {
        val json = prefs.getString("favourite_apps", "[]") ?: "[]"
        return try {
            val array = JSONArray(json)
            (0 until array.length()).map { array.getString(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    fun setTheme(isDark: Boolean) {
        prefs.edit().putBoolean("dark_theme", isDark).apply()
    }
    
    fun isDarkTheme(): Boolean {
        return prefs.getBoolean("dark_theme", true)
    }
}