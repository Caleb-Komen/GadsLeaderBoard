package com.gads.android.leaderboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gads.android.leaderboard.model.Learner
import com.gads.android.leaderboard.R
import kotlinx.android.synthetic.main.learning_leader_item.view.*

class LearningLeaderAdapter(val context: Context, val learners: List<Learner>):
    RecyclerView.Adapter<LearningLeaderAdapter.LearningLeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningLeaderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.learning_leader_item, parent, false)
        return LearningLeaderViewHolder(
            view
        )
    }

    override fun getItemCount() = learners.size

    override fun onBindViewHolder(holder: LearningLeaderViewHolder, position: Int) {
        val learner = learners[position]
        holder.itemView.learner_name.text = learner.name
        holder.itemView.learning_hours.text = learner.hours.toString()
        Glide.with(context)
            .load(learner.badgeUrl)
            .into(holder.itemView.learning_leader__badge)
    }

    class LearningLeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}