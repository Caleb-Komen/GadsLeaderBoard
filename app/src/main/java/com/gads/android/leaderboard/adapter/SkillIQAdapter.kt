package com.gads.android.leaderboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gads.android.leaderboard.R
import com.gads.android.leaderboard.model.Learner
import kotlinx.android.synthetic.main.skill_iq_item.view.*

class SkillIQAdapter(val context: Context, val learners: List<Learner>): RecyclerView.Adapter<SkillIQAdapter.SkillIQViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillIQViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.skill_iq_item, parent, false)
        return SkillIQViewHolder(itemView)
    }

    override fun getItemCount() = learners.size

    override fun onBindViewHolder(holder: SkillIQViewHolder, position: Int) {
        val learner = learners[position]
        holder.itemView.learner_name.text = learner.name
        holder.itemView.learner_skill_iq.text = learner.score?.toString()
        Glide.with(context)
            .load(learner.badgeUrl)
            .into(holder.itemView.skill_iq_badge)
    }

    inner class SkillIQViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}