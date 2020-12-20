package com.sid.covid_19.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sid.covid_19.R
import com.sid.covid_19.model.Contact

class HelplineRecyclerAdapter(val context: Context, private val contactList : ArrayList<Contact>) : RecyclerView.Adapter<HelplineRecyclerAdapter.HelplineViewHolder>(){
    class HelplineViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtCity : TextView = view.findViewById(R.id.txtCity)
        val txtCmoNo : TextView = view.findViewById(R.id.txtCmoNo)
        val txtControlNo : TextView = view.findViewById(R.id.txtControlNo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelplineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_helpline_single_row,parent,false)
        return HelplineViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
    override fun onBindViewHolder(holder: HelplineViewHolder, position: Int) {
        val contact = contactList[position]
        holder.txtCity.text = contact.contactName
        holder.txtCmoNo.text = contact.contactCmoNo
        holder.txtControlNo.text = contact.contactControlNo
        holder.txtControlNo.setOnClickListener {
            val no : String = holder.txtControlNo.text.toString()
            val u: Uri = Uri.parse("tel:$no")
            val i = Intent(Intent.ACTION_DIAL, u)
            context.startActivity(i)
        }
        holder.txtCmoNo.setOnClickListener {
            val no : String = holder.txtCmoNo.text.toString()
            val u: Uri = Uri.parse("tel:$no")
            val i = Intent(Intent.ACTION_DIAL, u)
            context.startActivity(i)
        }
    }
}