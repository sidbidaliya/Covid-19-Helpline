package com.sid.covid_19.activity

import android.app.ActionBar
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.sid.covid_19.R
import com.sid.covid_19.adapter.ViewPagerAdapter
import com.sid.covid_19.fragment.CasesFragment
import com.sid.covid_19.fragment.HelplineFragment
import com.sid.covid_19.fragment.HomeFragment
import com.sid.covid_19.fragment.ThingsToDoFragment
import com.sid.covid_19.util.ConnectionManager

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var tabLayout : TabLayout
    lateinit var viewPager : ViewPager

    lateinit var clipboard : ClipboardManager

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = "GEU Covid-19 Helpline"

//        toolbar.navigationIcon = getDrawable(R.mipmap.ic_toolbar_foreground)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        tabLayout.setupWithViewPager(viewPager)
        val viewPagerAdapter =
            ViewPagerAdapter(supportFragmentManager, 0)
        viewPagerAdapter.addFragment(HomeFragment(),"Home")
        viewPagerAdapter.addFragment(CasesFragment(),"Cases")
        viewPagerAdapter.addFragment(ThingsToDoFragment(),"Overview")
        viewPagerAdapter.addFragment(HelplineFragment(),"Helpline")

        viewPager.adapter = viewPagerAdapter

        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_home_dark)
        tabLayout.getTabAt(0)?.icon?.setTint(resources.getColor(R.color.colorPrimary))
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_cases)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_bulb)
        tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_call)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
                when (p0) {
                    tabLayout.getTabAt(0) -> {
                        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_home_dark)
                        tabLayout.getTabAt(0)?.icon?.setTint(resources.getColor(R.color.colorPrimary))
                    }
                    tabLayout.getTabAt(1) -> {
                        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_cases_filled)
                        tabLayout.getTabAt(1)?.icon?.setTint(resources.getColor(R.color.colorPrimary))
                    }
                    tabLayout.getTabAt(2) -> {
                        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_bulb_dark)
                        tabLayout.getTabAt(2)?.icon?.setTint(resources.getColor(R.color.colorPrimary))
                    }
                    else -> {
                        tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_call_dark)
                        tabLayout.getTabAt(3)?.icon?.setTint(resources.getColor(R.color.colorPrimary))
                    }
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                when (p0) {
                    tabLayout.getTabAt(0) -> {
                        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_home)
                        tabLayout.getTabAt(0)?.icon?.setTint(Color.BLACK)
                    }
                    tabLayout.getTabAt(1) -> {
                        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_cases)
                        tabLayout.getTabAt(1)?.icon?.setTint(Color.BLACK)
                    }
                    tabLayout.getTabAt(2) -> {
                        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_bulb)
                                tabLayout.getTabAt(2)?.icon?.setTint(Color.BLACK)
                    }
                    else -> {
                        tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_call)
                        tabLayout.getTabAt(3)?.icon?.setTint(Color.BLACK)
                    }
                }
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0) {
                    tabLayout.getTabAt(0) -> {
                        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_home_dark)
                        tabLayout.getTabAt(0)?.icon?.setTint(resources.getColor(R.color.colorPrimary))
                    }
                    tabLayout.getTabAt(1) -> {
                        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_cases_filled)
                        tabLayout.getTabAt(1)?.icon?.setTint(resources.getColor(R.color.colorPrimary))
                    }
                    tabLayout.getTabAt(2) -> {
                        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_bulb_dark)
                        tabLayout.getTabAt(2)?.icon?.setTint(resources.getColor(R.color.colorPrimary))
                    }
                    else -> {
                        tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_call_dark)
                        tabLayout.getTabAt(3)?.icon?.setTint(resources.getColor(R.color.colorPrimary))
                    }
                }
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.contactUs -> {
                val dialog = Dialog(this)
                val window: Window? = dialog.window
                dialog.setContentView(R.layout.contact_us_popup)
                val close = dialog.findViewById<ImageButton>(R.id.close)
                val website = dialog.findViewById<TextView>(R.id.website)
                val phone = dialog.findViewById<TextView>(R.id.number)
                val mail = dialog.findViewById<TextView>(R.id.mail)
                val address = dialog.findViewById<TextView>(R.id.address)

                close.setOnClickListener {
                    dialog.dismiss()
                }
                website.setOnClickListener {
                    if (ConnectionManager().checkConnectivity(this)){
                        val url = "https://www.geu.ac.in/content/geu/en.html"
                        val uri = Uri.parse(url)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }else {
                        //Internet is available
                        val alertDialog = AlertDialog.Builder(this)
                        alertDialog.setTitle("Error")
                        alertDialog.setMessage("Internet Connection Not Found")
                        alertDialog.setPositiveButton("Open Settings") { text, listener ->
                            val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                            startActivity(settingsIntent)
                            finish()
                        }
                        alertDialog.setNegativeButton("Exit") { text, listener ->
                        }
                        alertDialog.create()
                        alertDialog.show()
                    }
                }
                phone.setOnClickListener {
                    val u: Uri = Uri.parse("tel:${phone.text}")
                    val i = Intent(Intent.ACTION_DIAL, u)
                    startActivity(i)
                }
                mail.setOnClickListener {
                    clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData.newPlainText("mail","${mail.text}")
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this,"E-mail copied to clipboard",Toast.LENGTH_SHORT).show()
                }
                address.setOnClickListener {
                    clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData.newPlainText("address","${address.text}")
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this,"address copied to clipboard",Toast.LENGTH_SHORT).show()
                }
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT)
                dialog.show()

            }
            R.id.about ->{
                val dialog = Dialog(this)
                val window: Window? = dialog.window
                dialog.setContentView(R.layout.about_popup)
                val close = dialog.findViewById<ImageButton>(R.id.close)
                val abt = dialog.findViewById<TextView>(R.id.abt)
                val cse = dialog.findViewById<TextView>(R.id.cse)
                val geu1 = dialog.findViewById<TextView>(R.id.geu1)
                val dev = dialog.findViewById<TextView>(R.id.dev)
                val sidd = dialog.findViewById<TextView>(R.id.sidd)

                close.setOnClickListener {
                    dialog.dismiss()
                }

                sidd.setOnClickListener {
                    clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData.newPlainText("mail","${sidd.text}")
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this,"E-mail copied to clipboard",Toast.LENGTH_SHORT).show()
                }

                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT)
                dialog.show()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

}
