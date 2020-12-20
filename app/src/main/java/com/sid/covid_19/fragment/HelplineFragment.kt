package com.sid.covid_19.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sid.covid_19.R
import com.sid.covid_19.adapter.HelplineRecyclerAdapter
import com.sid.covid_19.adapter.ThingsRecyclerAdapter
import com.sid.covid_19.model.Contact

class HelplineFragment : Fragment() {

    lateinit var recyclerHelpLine : RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: HelplineRecyclerAdapter
    lateinit var txtGeuNo : TextView

    private val helpLineNumbers = arrayListOf<Contact>(
        Contact("Almora", "9411757084", "05962237874"),
        Contact("Bageshwar", "9412052145", "05963220197"),
        Contact("Chamoli", "9411525278", "01372251437"),
        Contact("Champawat", "9412975537", "05965230919"),
        Contact("Dehradun", "9997908733", "01352626066"),
        Contact("Haridwar", "7895280323", "01334223999"),
        Contact("Nainital", "9412344733", "N/A"),
        Contact("Pauri Garhwal", "97583992151", "01368221840"),
        Contact("Pithoragarh", "Rs. 9837972600", "0964226326"),
        Contact("Tehri Garhwal", "9411362810", "10376232831"),
        Contact("Udham Singh Nagar", "8958788763", "05944250250"),
        Contact("Rudraprayag", "9412325798", "01364233727"),
        Contact("Uttarkashi", "9837020379", "N/A"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_helpline, container, false)

        txtGeuNo = view.findViewById(R.id.txtGeuNo)
        val no : String = txtGeuNo.text.toString()
        txtGeuNo.setOnClickListener {
            val u: Uri = Uri.parse("tel:$no")
            val i = Intent(Intent.ACTION_DIAL, u)
            startActivity(i)
        }
        recyclerHelpLine = view.findViewById(R.id.recyclerHelpline)
        recyclerHelpLine.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(activity)
        recyclerAdapter = HelplineRecyclerAdapter(activity as Context,helpLineNumbers)
        recyclerHelpLine.adapter = recyclerAdapter
        recyclerHelpLine.layoutManager = LinearLayoutManager(context as Context)

        return view
    }

}
