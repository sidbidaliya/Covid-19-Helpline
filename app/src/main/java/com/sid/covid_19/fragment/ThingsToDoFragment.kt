package com.sid.covid_19.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sid.covid_19.R
import com.sid.covid_19.adapter.ThingsRecyclerAdapter
import com.sid.covid_19.model.Things
import com.squareup.picasso.Picasso

class ThingsToDoFragment : Fragment() {

    lateinit var recyclerThings : RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter : ThingsRecyclerAdapter

    lateinit var img1 : ImageView
    lateinit var img2 : ImageView

    val url1 : String? = "https://www.mygov.in/sites/all/themes/mygov/images/covid/symptoms.png"
    val url2 : String? = "https://www.mygov.in/sites/all/themes/mygov/images/covid/block-one.png"

    private val thingsToDo = arrayListOf(
        Things("https://www.amle.org/Portals/0//EasyDNNnews/1084/1084iStock-962787224.jpg","Greet with a Namaste instead of a handshake."),
        Things("https://cdn.dribbble.com/users/1787323/screenshots/11024818/media/b90060bfdbe14ecb5f50cd0236c6f02f.png","Maintain a safe distance from anyone who is coughing or sneezing."),
        Things("https://cdn.dribbble.com/users/1072881/screenshots/7141929/media/a252c89d13da77e73371f1be868c4272.jpg","Avoiding unneeded visits to medical facilities."),
        Things("https://cdn.dribbble.com/users/845836/screenshots/11033723/12_4x.jpg","Wash your hands regularly for 20 seconds, with soap and water or use alcohol based hand rub."),
        Things("https://cdn.dribbble.com/users/1519999/screenshots/10871529/media/2882dad2677d5a62f2377d2f591b0737.png","prefer work from home instead of going office."),
        Things("https://cdn.dribbble.com/users/1787323/screenshots/11024782/media/97b4d47e0b06f3e034b71af15e365c72.png","Cover your nose and mouth with a clean mask and use your bent elbow when you cough or sneeze.")
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_things_to_do, container, false)

        img1 = view.findViewById(R.id.img1)
        img2 = view.findViewById(R.id.img2)

        Picasso.get().load(url1).error(R.drawable.ic_launcher_foreground).into(img1)
        Picasso.get().load(url2).error(R.drawable.ic_launcher_foreground).into(img2)

        recyclerThings = view.findViewById(R.id.recyclerThings)
        recyclerThings.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(context)
        recyclerAdapter = ThingsRecyclerAdapter(activity as Context, thingsToDo)
        recyclerThings.adapter = recyclerAdapter
        recyclerThings.layoutManager = layoutManager
        return view
    }

}
