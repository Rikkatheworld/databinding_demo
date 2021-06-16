package com.example.databinding_demo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView


class MyView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    def: Int
) : LinearLayout(context, attributeSet, def) {

    private var time: String = "1"

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.my_layout, this, false)

        view.findViewById<TextView>(R.id.tv).text = time
        view.setOnClickListener {
            time = time.plus("1")
        }
    }

}