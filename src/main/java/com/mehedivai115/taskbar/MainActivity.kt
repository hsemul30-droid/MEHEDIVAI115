package com.mehedivai115.taskbar

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mehedivai115.taskbar.manager.WindowManager
import com.mehedivai115.taskbar.ui.dialog.AppDrawerDialog
import com.mehedivai115.taskbar.ui.widget.TaskbarButton
import com.mehedivai115.taskbar.util.PreferenceUtil
import com.mehedivai115.taskbar.viewmodel.TaskbarViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: TaskbarViewModel
    private lateinit var windowManager: WindowManager
    private lateinit var windowContainer: FrameLayout
    private lateinit var taskbar: LinearLayout
    private lateinit var openAppsContainer: LinearLayout
    private lateinit var preferenceUtil: PreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize
        viewModel = ViewModelProvider(this).get(TaskbarViewModel::class.java)
        preferenceUtil = PreferenceUtil(this)
        
        // Setup views
        windowContainer = findViewById(R.id.windowContainer)
        taskbar = findViewById(R.id.taskbar)
        openAppsContainer = findViewById(R.id.openAppsContainer)
        windowManager = WindowManager(this, windowContainer)
        
        // Set immersive mode
        setImmersiveMode()
        
        // Keep screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        
        setupUI()
    }

    private fun setImmersiveMode() {
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )
    }

    private fun setupUI() {
        val btnShowApps: Button = findViewById(R.id.btnShowApps)
        btnShowApps.setOnClickListener {
            showAppDrawer()
        }
        
        // Load installed apps
        viewModel.loadInstalledApps(this)
        
        // Observe opened apps
        viewModel.openedApps.observe(this) { apps ->
            updateTaskbar(apps)
        }
    }

    private fun showAppDrawer() {
        viewModel.installedApps.value?.let { apps ->
            val dialog = AppDrawerDialog(this, apps) { selectedApp ->
                windowManager.openWindow(selectedApp)
                viewModel.openApp(selectedApp)
            }
            dialog.show()
        }
    }

    private fun updateTaskbar(apps: List<Any>) {
        openAppsContainer.removeAllViews()
        
        apps.forEach { app ->
            if (app is com.mehedivai115.taskbar.model.AppInfo) {
                val button = TaskbarButton(this).apply {
                    setAppInfo(app)
                    setOnAppClickListener { clickedApp ->
                        windowManager.bringToFront(clickedApp)
                    }
                    layoutParams = LinearLayout.LayoutParams(
                        70,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    ).apply {
                        marginEnd = 4
                    }
                }
                openAppsContainer.addView(button)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setImmersiveMode()
    }

    override fun onDestroy() {
        windowManager.closeAllWindows()
        super.onDestroy()
    }
}