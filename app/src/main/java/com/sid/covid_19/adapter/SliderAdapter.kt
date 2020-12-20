package com.sid.covid_19.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.sid.covid_19.R
import com.squareup.picasso.Picasso

class SliderAdapter(var context : Context,val images : ArrayList<String>) : PagerAdapter(){

    private lateinit var layoutInflater: LayoutInflater

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slide_layout,container,false)

        val slideImage: ImageView = view.findViewById(R.id.sliderImage)

        Picasso.get().load(images[position]).error(R.drawable.ic_launcher_foreground).into(slideImage)

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeViewInLayout(`object` as View?)
    }

}