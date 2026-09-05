package com.mehedivai115.taskbar.manager

import android.content.Context
import android.view.ViewGroup
import com.mehedivai115.taskbar.model.AppInfo
import com.mehedivai115.taskbar.ui.widget.FloatingWindow
import com.mehedivai115.taskbar.util.AppLauncherUtil
import com.mehedivai115.taskbar.util.AnimationUtil

class WindowManager(private val context: Context, private val container: ViewGroup) {
    
    private val windows = mutableMapOf<String, FloatingWindow>()
    private var zIndex = 1
    
    fun openWindow(app: AppInfo): FloatingWindow {
        // Check if window already exists
        if (windows.containsKey(app.packageName)) {
            return windows[app.packageName]!!
        }
        
        // Create new window
        val window = FloatingWindow(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                app.windowWidth,
                app.windowHeight
            )
            x = app.windowX.toFloat()
            y = app.windowY.toFloat()
            elevation = zIndex.toFloat()
            setAppInfo(app)
            startAnimation(AnimationUtil.getWindowOpenAnimation())
        }
        
        // Set close listener
        window.setOnCloseClickListener { closedApp ->
            closeWindow(closedApp)
        }
        
        // Set minimize listener
        window.setOnMinimizeClickListener { minimizedApp ->
            minimizeWindow(minimizedApp)
        }
        
        container.addView(window)
        windows[app.packageName] = window
        zIndex++
        
        // Try to launch the actual app
        AppLauncherUtil.launchApp(context, app.packageName)
        
        return window
    }
    
    fun closeWindow(app: AppInfo) {
        val window = windows[app.packageName]
        if (window != null) {
            window.startAnimation(AnimationUtil.getWindowCloseAnimation())
            container.removeView(window)
            windows.remove(app.packageName)
            app.isOpened = false
        }
    }
    
    fun minimizeWindow(app: AppInfo) {
        val window = windows[app.packageName]
        if (window != null) {
            window.startAnimation(AnimationUtil.getMinimizeAnimation())
            app.isMinimized = true
        }
    }
    
    fun bringToFront(app: AppInfo) {
        val window = windows[app.packageName]
        if (window != null) {
            window.elevation = zIndex.toFloat()
            zIndex++
            container.bringChildToFront(window)
        }
    }
    
    fun closeAllWindows() {
        windows.values.forEach { window ->
            container.removeView(window)
        }
        windows.clear()
    }
}