package com.selftutor.besafetogether.screens.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.DangerCommentItemBinding
import com.selftutor.besafetogether.data.model.map.DangerComment

class DangerCommentsAdapter(
    private val dangerComments: List<DangerComment>
) : RecyclerView.Adapter<DangerCommentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DangerCommentItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dangerComment = dangerComments[position]
        val content = dangerComment.content
        val date = dangerComment.date
        val rating = dangerComment.rating

        val user = dangerComment.user
        val username = user.username

        with(holder.binding) {
            userNameTextView.text = username
            averRatingTextView.text = rating.toString()
            commentTextView.text = content
            dateTextView.text = date
            if (user.profileLink.isNotBlank()) {
                Glide.with(profileImageView.context)
                    .load(user.profileLink)
                    .circleCrop()
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(profileImageView)
            } else {
                Glide.with(profileImageView.context).clear(profileImageView)
                profileImageView.setImageResource(R.drawable.ic_person)
            }
        }
    }

    override fun getItemCount(): Int = dangerComments.size

    class ViewHolder(val binding: DangerCommentItemBinding) : RecyclerView.ViewHolder(binding.root)
}