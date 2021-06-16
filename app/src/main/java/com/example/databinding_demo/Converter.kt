package com.example.databinding_demo

import androidx.databinding.InverseMethod

object Converter {

    fun intToString(a: Int): String {
        return a.toString()
    }

    @InverseMethod("intToString")
    fun stringToInt(a: String): Int {
        return a.toInt()
    }
}