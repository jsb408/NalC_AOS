package com.nalc.android.nalc.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.nalc.android.nalc.R
import com.nalc.android.nalc.databinding.ViewBodyTypeButtonBinding

class BodyTypeButton(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val binding = ViewBodyTypeButtonBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BodyTypeButton)

        val emoji = typedArray.getString(R.styleable.BodyTypeButton_emoji)
        binding.tvEmojiBodyTypeButton.text = emoji

        val title = typedArray.getString(R.styleable.BodyTypeButton_title)
        binding.tvTitleBodyTypeButton.text = title

        typedArray.recycle()
    }
}