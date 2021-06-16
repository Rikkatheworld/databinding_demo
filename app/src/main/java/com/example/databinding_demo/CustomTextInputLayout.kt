package com.example.databinding_demo

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil


/**
 * TODO: Add Description
 *
 * @author rikkaxie
 * @date 2021/6/11
 */
class CustomTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private var tvHint: AppCompatTextView? = null
    private var editText: MultipleLinesEditText? = null

    fun setHint(hint: String?) {
        editText?.hint = hint
    }

    fun setTitle(text: String?) {
        tvHint!!.text = text
    }

    fun setText(text: String?) {
        if (getText() != text) {
            editText?.setText(text)
        }
    }

    private fun getText(): String {
        return editText?.text.toString()
    }

    private fun addTextWatch(textWatcher: TextWatcher) {
        editText?.addTextChangedListener(textWatcher)
    }

    private fun removeTextWatch(textWatcher: TextWatcher?) {
        editText?.removeTextChangedListener(textWatcher)
    }

    companion object {
        @BindingAdapter("app:text")
        @JvmStatic
        fun setText(customTextInputLayout: CustomTextInputLayout, text: String?) {
            customTextInputLayout.setText(text)
        }

        @InverseBindingAdapter(attribute = "app:text", event = "app:textAttrChanged")
        @JvmStatic
        fun getText(customTextInputLayout: CustomTextInputLayout): String {
            return customTextInputLayout.getText()
        }

        @BindingAdapter(value = ["app:textAttrChanged"], requireAll = false)
        @JvmStatic
        fun setListener(customTextInputLayout: CustomTextInputLayout, listener: InverseBindingListener?) {
            if (listener != null) {
                val newTextWatch: TextWatcher = object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(
                        charSequence: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        listener.onChange()
                    }
                }
                val oldTextWatch: TextWatcher? =
                    ListenerUtil.trackListener(customTextInputLayout, newTextWatch, R.id.textWatcher)
                if (oldTextWatch != null) {
                    customTextInputLayout.removeTextWatch(oldTextWatch)
                }
                customTextInputLayout.addTextWatch(newTextWatch)
            }
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_text_input_layout, this, true)
        tvHint = findViewById(R.id.tv_hint)
        editText = findViewById(R.id.et_content)
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextInputLayout)
        val title = typedArray.getString(R.styleable.CustomTextInputLayout_title)
        val hint = typedArray.getString(R.styleable.CustomTextInputLayout_hint)
        val text = typedArray.getString(R.styleable.CustomTextInputLayout_text)
        val maxLines = typedArray.getIndex(R.styleable.CustomTextInputLayout_maxLines)
        editText?.maxLines = maxLines
        if (!TextUtils.isEmpty(hint)) {
            setHint(hint)
        }
        if (!TextUtils.isEmpty(title)) {
            setTitle(title)
        }
        if (!TextUtils.isEmpty(text)) {
            setText(text)
        }
        typedArray.recycle()
    }
}
