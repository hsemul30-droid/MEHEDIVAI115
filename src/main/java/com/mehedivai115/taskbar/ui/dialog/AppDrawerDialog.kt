package com.mehedivai115.taskbar.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.GridView
import com.mehedivai115.taskbar.R
import com.mehedivai115.taskbar.model.AppInfo
import com.mehedivai115.taskbar.ui.adapter.AppAdapter

class AppDrawerDialog(
    context: Context,
    private val apps: List<AppInfo>,
    private val onAppSelected: (AppInfo) -> Unit
) : Dialog(context, R.style.Theme_MEHEDIVAI115) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_app_drawer)
        
        window?.setLayout(
            (context.resources.displayMetrics.widthPixels * 0.9).toInt(),
            (context.resources.displayMetrics.heightPixels * 0.8).toInt()
        )

        val gridView = findViewById<GridView>(R.id.appsGrid)
        gridView?.adapter = AppAdapter(apps) { app ->
            onAppSelected(app)
            dismiss()
        }
    }
}