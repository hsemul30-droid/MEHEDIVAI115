package com.mehedivai115.taskbar.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.mehedivai115.taskbar.R
import com.mehedivai115.taskbar.model.AppInfo

class TaskbarButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val iconView: ImageView
    private val labelView: TextView
    private var appInfo: AppInfo? = null
    private var onClickListener: ((AppInfo) -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.item_taskbar_button, this, true)
        iconView = findViewById(R.id.icon)
        labelView = findViewById(R.id.label)

        setOnClickListener {
            appInfo?.let { onClickListener?.invoke(it) }
        }
    }

    fun setAppInfo(app: AppInfo) {
        appInfo = app
        iconView.setImageDrawable(app.icon)
        labelView.text = app.label
    }

    fun setOnAppClickListener(listener: (AppInfo) -> Unit) {
        onClickListener = listener
    }
}