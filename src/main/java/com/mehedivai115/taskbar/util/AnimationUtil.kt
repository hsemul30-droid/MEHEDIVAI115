package com.mehedivai115.taskbar.util

import android.content.Context
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation

object AnimationUtil {
    
    fun getWindowOpenAnimation(): AnimationSet {
        return AnimationSet(true).apply {
            addAnimation(ScaleAnimation(
                0.5f, 1f, 0.5f, 1f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f
            ).apply { duration = 300 })
            addAnimation(AlphaAnimation(0f, 1f).apply { duration = 300 })
        }
    }
    
    fun getWindowCloseAnimation(): AnimationSet {
        return AnimationSet(true).apply {
            addAnimation(ScaleAnimation(
                1f, 0.5f, 1f, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f
            ).apply { duration = 300 })
            addAnimation(AlphaAnimation(1f, 0f).apply { duration = 300 })
        }
    }
    
    fun getMinimizeAnimation(): AnimationSet {
        return AnimationSet(true).apply {
            addAnimation(ScaleAnimation(
                1f, 0.3f, 1f, 0.3f,
                ScaleAnimation.RELATIVE_TO_SELF, 1f,
                ScaleAnimation.RELATIVE_TO_SELF, 1f
            ).apply { duration = 500 })
            addAnimation(AlphaAnimation(1f, 0.3f).apply { duration = 500 })
        }
    }
}