package com.sid.covid_19.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sid.covid_19.R
import com.sid.covid_19.adapter.StatsRecyclerAdapter
import com.sid.covid_19.model.Stats
import com.sid.covid_19.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CasesFragment : Fragment() {

    lateinit var recyclerStat : RecyclerView
    lateinit var recyclerAdapter : StatsRecyclerAdapter

    lateinit var indConfirmed : TextView
    lateinit var indRecovered : TextView
    lateinit var indDeceased :TextView

    lateinit var stConfirmed : TextView
    lateinit var stRecovered : TextView
    lateinit var stDeceased :TextView

    lateinit var mainState : TextView
    lateinit var confirmed : TextView
    lateinit var recovered : TextView
    lateinit var deceased : TextView

    private var statConfirm = Comparator<Stats>{
            stat1,stat2 ->
        if(stat1.confirmed.compareTo(stat2.confirmed,true) == 0){
            //Sort according to name if rating is same
            stat1.location.compareTo(stat2.location,true)
        }else{
            stat1.confirmed.compareTo(stat2.confirmed,true)
        }
    }
    private var statRecover = Comparator<Stats>{
            stat1,stat2 ->
        if(stat1.recovered.compareTo(stat2.recovered,true) == 0){
            //Sort according to name if rating is same
            stat1.location.compareTo(stat2.location,true)
        }else{
            stat1.recovered.compareTo(stat2.recovered,true)
        }
    }
    private var statDecease = Comparator<Stats>{
            stat1,stat2 ->
        if(stat1.deceased.compareTo(stat2.deceased,true) == 0){
            //Sort according to name if rating is same
            stat1.location.compareTo(stat2.location,true)
        }else{
            stat1.deceased.compareTo(stat2.deceased,true)
        }
    }

    val statsInfoList : ArrayList<Stats> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cases, container, false)

        recyclerStat = view.findViewById(R.id.recyclerStats)
        indConfirmed = view.findViewById(R.id.txtConfirmed)
        indRecovered = view.findViewById(R.id.txtRecovered)
        indDeceased = view.findViewById(R.id.txtDeceased)
        stConfirmed = view.findViewById(R.id.stConfirmed)
        stRecovered = view.findViewById(R.id.stRecovered)
        stDeceased = view.findViewById(R.id.stDeceased)
        mainState = view.findViewById(R.id.mainState)

        confirmed = view.findViewById(R.id.confirmed)
        recovered = view.findViewById(R.id.recovered)
        deceased = view.findViewById(R.id.deceased)

        recyclerStat.isNestedScrollingEnabled = false

        statsInfoList.clear()

        val queue = Volley.newRequestQueue(activity as Context)
        val url = "https://api.rootnet.in/covid19-in/stats/latest"
        if (ConnectionManager().checkConnectivity(activity as Context)){

            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,url,null,Response.Listener {
                try {
                    val success = it.getBoolean("success")
                    if (success){
                        println("Response is $it")
                        val stat = it.getJSONObject("data")
                        val ind = stat.getJSONObject("summary")
                        indConfirmed.text = ind.getString("total")
                        indRecovered.text = ind.getString("discharged")
                        indDeceased.text = ind.getString("deaths")
                        val stats = stat.getJSONArray("regional")
                        for (i in 0 until stats.length()) {
                            val resObject = stats.getJSONObject(i)
                            if (resObject.getString("loc") == "Uttarakhand"){
                                mainState.text = resObject.getString("loc")
                                stConfirmed.text = resObject.getString("confirmedCasesIndian")
                                stRecovered.text = resObject.getString("discharged")
                                stDeceased.text = resObject.getString("deaths")
                            }
                            statsInfoList.add(
                                Stats(
                                    resObject.getString("loc"),
                                    resObject.getString("confirmedCasesIndian"),
                                    resObject.getString("discharged"),
                                    resObject.getString("deaths")
                                )
                            )
                        }
                        if (activity!=null){
                            recyclerAdapter = StatsRecyclerAdapter(context as Context,statsInfoList)
                            recyclerStat.adapter = recyclerAdapter
                            recyclerStat.layoutManager = LinearLayoutManager(context as Context)
                        }
                    }else{
                        Toast.makeText(
                                activity as Context,
                                "Error Occurred!!!",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                }catch (e: JSONException){
                    Toast.makeText(
                        activity as Context,
                        "Some unexpected error occurred! $e",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },Response.ErrorListener {
                //Here we will handle Error
                if (activity != null)
                    Toast.makeText(
                        activity as Context,
                        "Volley error occurred",
                        Toast.LENGTH_SHORT
                    ).show()
            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        }else {
            //Internet is available
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit") { text, listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }

        confirmed.setOnClickListener {
            Collections.sort(statsInfoList,statConfirm)
            recyclerAdapter.notifyDataSetChanged()
        }
        recovered.setOnClickListener {
            Collections.sort(statsInfoList,statRecover)
            recyclerAdapter.notifyDataSetChanged()
        }
        deceased.setOnClickListener {
            Collections.sort(statsInfoList,statDecease)
            recyclerAdapter.notifyDataSetChanged()
        }

        return view
    }

}
