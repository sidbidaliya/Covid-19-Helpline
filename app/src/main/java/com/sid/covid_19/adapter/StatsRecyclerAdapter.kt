package com.sid.covid_19.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sid.covid_19.R
import com.sid.covid_19.model.Stats

class StatsRecyclerAdapter(val context: Context, private val statsList : ArrayList<Stats>) : RecyclerView.Adapter<StatsRecyclerAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val state : TextView = view.findViewById(R.id.state)
        val confirmed : TextView = view.findViewById(R.id.confirmed)
        val recovered : TextView = view.findViewById(R.id.recovered)
        val deceased : TextView = view.findViewById(R.id.deceased)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stats_layout, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return statsList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val stats = statsList[position]
        holder.state.text = stats.location
        holder.confirmed.text = stats.confirmed
        holder.recovered.text = stats.recovered
        holder.deceased.text = stats.deceased
    }
}
