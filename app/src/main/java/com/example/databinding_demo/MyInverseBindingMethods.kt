package com.example.databinding_demo

import android.util.Log
import android.widget.RatingBar
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

@BindingAdapter(value = ["android:rt"], requireAll = true)
fun setRt(view: RatingBar?, value: Float) {
    if (view == null) {
        return
    }
    if (view.rating == value) {
        return
    }
    // m -> v
    view.rating = value
}

@InverseBindingAdapter(attribute = "android:rt", event = "android:ratingAttrChanged")
fun getRt(view: RatingBar?): Float {
    if (view == null) {
        return 0f
    }
    return view.rating
}

@BindingAdapter(value = ["android:rt", "android:onRatingChanged", "android:ratingAttrChanged"], requireAll = true)
fun setListener(
    view: RatingBar,
    rt: Float,
    listener: RatingBar.OnRatingBarChangeListener?,
    attrChangedListener: InverseBindingListener?
) {
    view.rating = rt
    if (attrChangedListener == null) {
        view.onRatingBarChangeListener = listener
    } else {
//         v -> m
        view.setOnRatingBarChangeListener { v, r, b ->
            listener?.onRatingChanged(v, r, b)
            attrChangedListener.onChange()
        }
    }
}


//@BindingAdapter(value = ["android:onRatingChanged", "android:ratingAttrChanged"], requireAll = false)
//fun setListener(
//    view: RatingBar,
//    listener: RatingBar.OnRatingBarChangeListener?,
//    attrListener: InverseBindingListener?
//) {
//    if (attrListener == null) {
//        view.onRatingBarChangeListener = listener
//    } else {
//
//        view->model
//        view.onRaztingBarChangeListener =
//            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
//                listener?.onRatingChanged(ratingBar, rating, fromUser)
//                attrListener.onChange()
//            }
//    }
//}