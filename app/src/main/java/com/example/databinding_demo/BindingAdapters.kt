package com.example.databinding_demo

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener

object BindingAdapters {

    @BindingAdapter(value = ["myTextView:color"])
    @JvmStatic
    fun color(view: TextView, color: Int) {
        view.setTextColor(color)
    }

    @BindingAdapter(value = ["myTextView:textClicked", "android:textAttrChanged"], requireAll = false)
    @JvmStatic
    fun setTextClicked(
        view: TextView,
        listener: OnMyTextClickListener?,
        attrChange: InverseBindingListener?
    ) {
        view.setOnClickListener {
            listener?.onTextClick(view, view.text.toString())
            attrChange?.onChange()
        }
    }

    interface OnMyTextClickListener {
        fun onTextClick(v: TextView, s: String)
    }
}

