package com.example.lessstress

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_REGISTR = 10
    private var user = Users()
    lateinit var buttonLog: Button
    lateinit var buttonInf: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonLog = findViewById(R.id.buttonLogin)
        buttonInf = findViewById(R.id.buttonInfo)
        buttonInf.isEnabled = false
    }

    fun onLogin(view: View) {
        val loginDialog = Login()
        val file = "myFile.json"
        val bufferedReader = BufferedReader(InputStreamReader(openFileInput(file)))
        val readText = bufferedReader.readLine()
        val jsonObject = JSONObject(readText)
        loginDialog.users = jsonObject.getString("user")
        loginDialog.password = jsonObject.getString("password")
        bufferedReader.close()
        loginDialog.mainActivity = this
        loginDialog.show(supportFragmentManager, "Dialog_Tag")
    }

    fun onRegister(view: View) {
        val myIntent = Intent(this, Register::class.java)
        myIntent.putExtra("register", true)
        startActivityForResult(myIntent, REQUEST_CODE_REGISTR)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_REGISTR) {
                val bundleData = data!!.getBundleExtra("user")
                user.setBundle(bundleData!!)
                buttonLog.isEnabled = true
            }
        }
    }

    fun onInformation(view: View) {
        val myIntent = Intent(this, Register::class.java)
        myIntent.putExtra("register", false)
        startActivity(myIntent)
    }

    fun onActivateInformation() {
        buttonInf.isEnabled = true
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to come out?")
        builder.setCancelable(false).setPositiveButton("SI") { dialog, id -> this@MainActivity.finish()}
        builder.setNegativeButton("NO") { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }
}