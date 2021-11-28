package com.nalc.android.nalc.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.nalc.android.nalc.databinding.ItemReplyMainBinding
import com.nalc.android.nalc.model.ReplyModel

class ReplyAdapter(options: FirestorePagingOptions<ReplyModel>) : FirestorePagingAdapter<ReplyModel, ReplyAdapter.ReplyViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder {
        return ReplyViewHolder(
            ItemReplyMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int, model: ReplyModel) {
        holder.bind(model)
    }

    inner class ReplyViewHolder(private val binding: ItemReplyMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reply: ReplyModel) {
            binding.reply = reply
        }
    }
}