package com.nalc.android.nalc.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.nalc.android.nalc.R
import com.nalc.android.nalc.databinding.ViewEmojiButtonBinding

class EmojiButton(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var binding: ViewEmojiButtonBinding = ViewEmojiButtonBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        // attrs.xml에서 View의 속성 목록을 가져온다.
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmojiButton)

        val emoji = typedArray.getString(R.styleable.EmojiButton_emoji)
        binding.tvEmojiButton.text = emoji

        val title = typedArray.getString(R.styleable.EmojiButton_title)
        binding.tvTitleButton.text = title

        val count = typedArray.getInt(R.styleable.EmojiButton_count, 0)
        binding.tvCountButton.text = count.toString()

        // 데이터를 캐싱해두어 가비지컬렉션에서 제외시키도록 하는 함수
        // typedArray 사용 후 호출해야하나, 커스텀뷰가 반복 사용되는 것이 아니라면 호출하지 않아도 무방하다.
        typedArray.recycle()
    }
}