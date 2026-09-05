package com.mehedivai115.taskbar.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.mehedivai115.taskbar.model.AppInfo
import kotlin.math.abs

class FloatingWindow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var lastX = 0f
    private var lastY = 0f
    private var isDragging = false
    private var isResizing = false
    private var resizeStartX = 0f
    private var resizeStartY = 0f
    private var startWidth = 0
    private var startHeight = 0
    private var onCloseClick: ((AppInfo) -> Unit)? = null
    private var onMinimizeClick: ((AppInfo) -> Unit)? = null
    var appInfo: AppInfo? = null
    
    private lateinit var titleBar: LinearLayout
    private lateinit var appLabel: TextView
    private lateinit var btnClose: ImageButton
    private lateinit var btnMinimize: ImageButton
    private lateinit var contentContainer: FrameLayout
    private lateinit var resizeHandle: View

    init {
        setupUI()
    }

    private fun setupUI() {
        // Title bar
        titleBar = LinearLayout(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 40)
            setBackgroundColor(0xFF0066cc.toInt())
            orientation = LinearLayout.HORIZONTAL
            setPadding(10, 5, 10, 5)
        }

        // App label
        appLabel = TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
            setTextColor(0xFFFFFFFF.toInt())
            textSize = 14f
        }

        // Minimize button
        btnMinimize = ImageButton(context).apply {
            layoutParams = LinearLayout.LayoutParams(40, 40)
            setBackgroundColor(0xFF0066cc.toInt())
        }

        // Close button
        btnClose = ImageButton(context).apply {
            layoutParams = LinearLayout.LayoutParams(40, 40)
            setBackgroundColor(0xFFcc0000.toInt())
        }

        titleBar.apply {
            addView(appLabel)
            addView(btnMinimize)
            addView(btnClose)
        }

        // Content container
        contentContainer = FrameLayout(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }

        // Resize handle
        resizeHandle = View(context).apply {
            layoutParams = LayoutParams(40, 40)
            setBackgroundColor(0xFF666666.toInt())
        }

        addView(titleBar)
        addView(contentContainer)
        addView(resizeHandle)

        setBackgroundColor(0xFF2d2d2d.toInt())
        elevation = 8f

        setOnTouchListener { v, event ->
            handleTouch(event)
        }
    }

    private fun handleTouch(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX
                lastY = event.rawY
                
                // Check if touching title bar
                if (event.y < 50) {
                    isDragging = true
                }
                // Check if touching resize handle
                if (event.x > width - 50 && event.y > height - 50) {
                    isResizing = true
                    resizeStartX = event.rawX
                    resizeStartY = event.rawY
                    startWidth = width
                    startHeight = height
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = event.rawX - lastX
                val deltaY = event.rawY - lastY

                if (isDragging) {
                    val newX = x + deltaX
                    val newY = y + deltaY
                    animate().x(newX).y(newY).setDuration(0).start()
                    appInfo?.windowX = newX.toInt()
                    appInfo?.windowY = newY.toInt()
                }

                if (isResizing) {
                    val newWidth = (startWidth + (event.rawX - resizeStartX)).toInt()
                    val newHeight = (startHeight + (event.rawY - resizeStartY)).toInt()
                    
                    if (newWidth > 200 && newHeight > 150) {
                        layoutParams.apply {
                            width = newWidth
                            height = newHeight
                        }
                        requestLayout()
                        appInfo?.apply {
                            windowWidth = newWidth
                            windowHeight = newHeight
                        }
                    }
                }

                lastX = event.rawX
                lastY = event.rawY
            }
            MotionEvent.ACTION_UP -> {
                isDragging = false
                isResizing = false
            }
        }
        return true
    }

    fun setAppInfo(app: AppInfo) {
        appInfo = app
        appLabel.text = app.label
    }

    fun setOnCloseClickListener(listener: (AppInfo) -> Unit) {
        onCloseClick = listener
        btnClose.setOnClickListener {
            appInfo?.let { listener(it) }
        }
    }

    fun setOnMinimizeClickListener(listener: (AppInfo) -> Unit) {
        onMinimizeClick = listener
        btnMinimize.setOnClickListener {
            appInfo?.let { listener(it) }
        }
    }

    fun getContentContainer(): ViewGroup = contentContainer
}