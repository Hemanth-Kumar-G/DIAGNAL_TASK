package com.task.diagnal.common


import android.R.attr.name
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter


object AppBindingAdapters {


    @BindingAdapter("onBackClick")
    @JvmStatic
    fun setToolbarListener(view: View, activity: Activity) {
        view.setOnClickListener {
            activity.onBackPressed()
        }
    }
}
