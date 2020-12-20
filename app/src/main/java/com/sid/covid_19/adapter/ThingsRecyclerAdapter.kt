package com.sid.covid_19.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sid.covid_19.R
import com.sid.covid_19.model.Contact
import com.sid.covid_19.model.Things
import com.squareup.picasso.Picasso

class ThingsRecyclerAdapter(val context: Context, private val thingsTodo : ArrayList<Things>) : RecyclerView.Adapter<ThingsRecyclerAdapter.ThingsViewHolder>(){

   private var pos : Int = -1

    class ThingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtThing: TextView = view.findViewById(R.id.txtThings)
        val imgThing: ImageView = view.findViewById(R.id.imgThings)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThingsViewHolder {
        println("Response is --> $pos")
        val view : View = when {
            pos == 0 -> {
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_things1_single_row, parent, false)
            }
            pos%2 != 0 -> {
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_things_single_row, parent, false)
            }
            else -> {
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_things1_single_row, parent, false)
            }
        }
        return ThingsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return thingsTodo.size
    }

    override fun onBindViewHolder(holder: ThingsViewHolder, position: Int) {

        pos = position
        val things = thingsTodo[position]
        holder.txtThing.text = things.title
        Picasso.get().load(things.imgUrl).error(R.drawable.ic_launcher_foreground).into(holder.imgThing)
    }

}