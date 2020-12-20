package com.sid.covid_19.fragment

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.sid.covid_19.R
import com.sid.covid_19.activity.DonateActivity
import com.sid.covid_19.adapter.SliderAdapter
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.properties.Delegates

class HomeFragment : Fragment() {

    private val slideImages = arrayListOf(
        "https://www.geu.ac.in/content/dam/geu/Image-Galleries/HeaderFiles/build.jpg",
        "https://donation.geu.ac.in/wp-content/uploads/2020/03/slider_bg2.jpg",
//        "https://donation.geu.ac.in/wp-content/uploads/2020/03/slider_bg4.jpg",
        "https://donation.geu.ac.in/wp-content/uploads/2020/03/slider_bg1.jpg",
//        "https://donation.geu.ac.in/wp-content/uploads/2020/03/slider_bg3.jpg"
        "https://www.geu.ac.in/content/dam/geu/Image-Galleries/media/2018/oct/bd/Blood_Donation_Graphic_Era_2018_03.jpg"
    )

    private lateinit var btnDonate: Button
    lateinit var viewPager: ViewPager
    lateinit var dotLayout: LinearLayout
    lateinit var sliderAdapter: SliderAdapter
    private val dots = arrayOfNulls<TextView>(4)
    lateinit var btnForward: ImageButton
    lateinit var btnBack: ImageButton
    var currentPage = 0
    private val slideTime: Long = 4500 //1.5 sec
    private val delay: Long = 1000
    lateinit var timer: Timer
    lateinit var qr: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        btnDonate = view.findViewById(R.id.btnDonate)
        viewPager = view.findViewById(R.id.viewPagerHome)
        dotLayout = view.findViewById(R.id.dotLayout)
        btnForward = view.findViewById(R.id.btnForward)
        qr = view.findViewById(R.id.txtQR)

        qr.setOnClickListener {
            val dialog = Dialog(context as Context)
            val window: Window? = dialog.window
            dialog.setContentView(R.layout.qr_code)
            val close = dialog.findViewById<ImageButton>(R.id.close)
            val qr = dialog.findViewById<ImageView>(R.id.imgQR)
            val url = "https://donation.geu.ac.in/wp-content/uploads/2020/03/payment-qr-1.jpg"
            Picasso.get().load(url).error(R.drawable.ic_launcher_foreground).into(qr)
            close.setOnClickListener {
                dialog.dismiss()
            }
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            dialog.show()
        }

        btnBack = view.findViewById(R.id.btnBack)

        sliderAdapter = SliderAdapter(context as Context, slideImages)
        viewPager.adapter = sliderAdapter

        btnForward.setOnClickListener {
            if (currentPage == dots.lastIndex) {
                currentPage = 0
                viewPager.setCurrentItem(currentPage, true)
            } else {
                viewPager.setCurrentItem(currentPage++, true)
            }
        }
        btnBack.setOnClickListener {
            if (currentPage == 0) {
                currentPage = dots.lastIndex
                viewPager.setCurrentItem(currentPage, true)
            } else {
                viewPager.setCurrentItem(currentPage - 1, true)
            }
        }

        //----------AUTO SCROLL VIEW PAGER----------
        val handler = Handler()
        val runnable = Runnable {
            println("Response is --> $currentPage")
            if (currentPage == dots.lastIndex) {
                viewPager.setCurrentItem(currentPage, true)
                currentPage = 0
            } else {
                viewPager.setCurrentItem(currentPage++, true)
            }
        }

        timer = Timer()
        timer.schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(runnable)
            }
        }, delay, slideTime)
        //----------AUTO SCROLL VIEW PAGER----------

        addDotIndicator(currentPage)

        btnDonate.setOnClickListener {
            val intent = Intent(context, DonateActivity::class.java)
            startActivity(intent)
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                addDotIndicator(position)
                currentPage = position
                println("Response is current page = $currentPage")
            }

        })

        return view
    }

    private fun addDotIndicator(pos: Int) {

        dotLayout.removeAllViews()

        for (i in 0..dots.lastIndex) {
            dots[i] = TextView(context);
            dots[i]?.text = Html.fromHtml("&#8226;")
            dots[i]?.textSize = 35F
            dots[i]?.setTextColor(resources.getColor(R.color.colorTransparentWhite))
            dotLayout.addView(dots[i])
        }

//        if (dots.indices != 0..0){
        dots[pos]?.setTextColor(resources.getColor(R.color.colorPrimary))
//        }

    }

    override fun onDestroyView() {
        timer.cancel()
        super.onDestroyView()
    }

}
