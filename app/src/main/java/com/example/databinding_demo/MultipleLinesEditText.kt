package com.example.databinding_demo

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText

/**
 * TODO: Add Description
 *
 * @author rikkaxie
 * @date 2021/6/11
 */
class MultipleLinesEditText : AppCompatEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (canScrollVertically(-1) || canScrollVertically(0)) {
            parent.requestDisallowInterceptTouchEvent(true)
        }
        return super.onTouchEvent(event)
    }
}
