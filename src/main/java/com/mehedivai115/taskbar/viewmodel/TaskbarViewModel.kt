package com.mehedivai115.taskbar.viewmodel

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mehedivai115.taskbar.model.AppInfo

class TaskbarViewModel : ViewModel() {
    val installedApps = MutableLiveData<List<AppInfo>>()
    val openedApps = MutableLiveData<MutableList<AppInfo>>(mutableListOf())
    
    fun loadInstalledApps(context: Context) {
        val pm = context.packageManager
        val apps = mutableListOf<AppInfo>()
        
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        
        for (appInfo in packages) {
            // Skip system apps
            if ((appInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0 || 
                (appInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                try {
                    val label = pm.getApplicationLabel(appInfo).toString()
                    val icon = pm.getApplicationIcon(appInfo)
                    apps.add(
                        AppInfo(
                            packageName = appInfo.packageName,
                            label = label,
                            icon = icon,
                            isOpened = false
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        
        installedApps.postValue(apps.sortedBy { it.label })
    }
    
    fun openApp(app: AppInfo) {
        val currentList = openedApps.value?.toMutableList() ?: mutableListOf()
        if (!currentList.contains(app)) {
            app.isOpened = true
            currentList.add(app)
            openedApps.postValue(currentList)
        }
    }
    
    fun closeApp(app: AppInfo) {
        val currentList = openedApps.value?.toMutableList() ?: mutableListOf()
        currentList.remove(app)
        app.isOpened = false
        openedApps.postValue(currentList)
    }
    
    fun minimizeApp(app: AppInfo) {
        app.isMinimized = !app.isMinimized
        openedApps.postValue(openedApps.value)
    }
}