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
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sid.covid_19.R
import com.sid.covid_19.util.ConnectionManager

class DonateActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var clipboard : ClipboardManager

    lateinit var name: EditText
    lateinit var pan: EditText
    lateinit var phone: EditText
    lateinit var mail: EditText
    lateinit var house: EditText
    lateinit var city: EditText
    lateinit var state: EditText
    lateinit var dist: EditText
    lateinit var amount: EditText
    lateinit var ref: EditText
    lateinit var purp: EditText
    lateinit var radioGroup : RadioGroup
    lateinit var radioButton : RadioButton
    lateinit var btnSubmit: Button

//    lateinit var progressLayout: RelativeLayout
//    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = "Donation | Graphic Era"

//        progressLayout = findViewById(R.id.progressLayout)
//        progressBar = findViewById(R.id.progressBar)
//        progressLayout.visibility = View.GONE

        name = findViewById(R.id.etName)
        pan = findViewById(R.id.etPan)
        phone = findViewById(R.id.etContact)
        mail = findViewById(R.id.etMail)
        house = findViewById(R.id.etAddress)
        city = findViewById(R.id.etAddress2)
        state = findViewById(R.id.etAddress3)
        dist = findViewById(R.id.etAddress4)
        amount = findViewById(R.id.etAmount)
        ref = findViewById(R.id.etReference)
        purp = findViewById(R.id.etPurpose)
        radioGroup = findViewById(R.id.radioGroup)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.txtMode1 -> {
                    radioButton = findViewById(R.id.txtMode1)
                }
                R.id.txtMode2 -> {
                    radioButton = findViewById(R.id.txtMode2)
                }
                R.id.txtMode3 -> {
                    radioButton = findViewById(R.id.txtMode3)
                }
            }
        }
        btnSubmit = findViewById(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            if (name.text.isEmpty()){
                name.error = "Name is required"
                name.requestFocus()
                return@setOnClickListener
            }
            if (pan.text.isEmpty()){
                pan.error = "pan is required"
                pan.requestFocus()
                return@setOnClickListener
            }
            if (phone.text.isEmpty()){
                phone.error = "phone no. is required"
                phone.requestFocus()
                return@setOnClickListener
            }
            if (phone.text.length != 10){
                phone.error = "Enter valid phone no."
                phone.requestFocus()
                return@setOnClickListener
            }
            if (mail.text.isEmpty()){
                mail.error = "E-mail is required"
                mail.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(mail.text).matches()){
                mail.error = "Enter valid mail"
                mail.requestFocus()
                return@setOnClickListener
            }
            if (house.text.isEmpty()){
                house.error = "Address is required"
                house.requestFocus()
                return@setOnClickListener
            }
            if (city.text.isEmpty()){
                city.error = "city is required"
                city.requestFocus()
                return@setOnClickListener
            }
            if (state.text.isEmpty()){
                state.error = "state is required"
                state.requestFocus()
                return@setOnClickListener
            }
            if (dist.text.isEmpty()){
                dist.error = "district is required"
                dist.requestFocus()
                return@setOnClickListener
            }
            if (amount.text.isEmpty()){
                amount.error = "Amount is required"
                amount.requestFocus()
                return@setOnClickListener
            }
            if (ref.text.isEmpty()){
                ref.error = "This field is required"
                ref.requestFocus()
                return@setOnClickListener
            }
//            progressLayout.visibility = View.VISIBLE
            btnSubmit.isEnabled = false
            addItemToSheet()
        }
    }

    private fun addItemToSheet(){
        val mode : String = radioButton.text.toString()
        val url = "https://script.google.com/macros/s/AKfycby6vpmaKO8GWTos_h8rzoDB-fHgXELgM1J-EbKsF2U0-nGzYHdN/exec"
        val stringRequest: StringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener<String?> { response ->
//                    progressLayout.visibility = View.GONE
                    onBackPressed()
                    finish()
                }, Response.ErrorListener {
//                progressLayout.visibility = View.GONE
                btnSubmit.isEnabled = true
                    Toast.makeText(this, "Volley Error $it", Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    //here we pass params
                    params["action"] = "addItem"
                    params["Name"] = name.text.toString()
                    params["Pan"] = pan.text.toString()
                    params["Mobile"] = phone.text.toString()
                    params["Mail"] = mail.text.toString()
                    params["House"] = house.text.toString()
                    params["City"] = city.text.toString()
                    params["State"] = state.text.toString()
                    params["District"] = dist.text.toString()
                    params["Amount"] = amount.text.toString()
                    params["Reference"] = ref.text.toString()
                    params["Purpose"] = purp.text.toString()
                    return params
                }
            }
        val socketTimeOut = 50000 // u can change this .. here it is 50 seconds


        val retryPolicy: RetryPolicy =
            DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        stringRequest.retryPolicy = retryPolicy

        val queue = Volley.newRequestQueue(this)

        queue.add(stringRequest)
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
                    if (ConnectionManager().checkConnectivity(this)) {
                        val url = "https://www.geu.ac.in/content/geu/en.html"
                        val uri = Uri.parse(url)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    } else {
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
                    val clip: ClipData = ClipData.newPlainText("mail", "${mail.text}")
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this, "E-mail copied to clipboard", Toast.LENGTH_SHORT).show()
                }
                address.setOnClickListener {
                    clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData.newPlainText("address", "${address.text}"
                    )
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this, "address copied to clipboard", Toast.LENGTH_SHORT).show()
                }
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window?.setLayout(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT
                )
                dialog.show()
            }
            R.id.about -> {
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
                    val clip: ClipData = ClipData.newPlainText("mail", "${sidd.text}")
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this, "E-mail copied to clipboard", Toast.LENGTH_SHORT).show()
                }
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window?.setLayout(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT
                )
                dialog.show()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}
