package com.example.android_app

import CurrentUser
import DataSingleton
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View;
import android.widget.TextView
import android.widget.Toast
import android.content.Intent;
import android.util.Base64
import android.util.Base64.encodeToString
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.StringRequest
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun entryButtonClicked(view: View) {
        val textViewLogin: TextView = findViewById<TextView>(R.id.editTextLogin)
        DataSingleton.SetLogin(textViewLogin.text.toString())
        CurrentUser.username = textViewLogin.text.toString()
        val textViewPassword: TextView = findViewById<TextView>(R.id.editTextPassword)
        DataSingleton.SetPassword(textViewPassword.text.toString())
        CurrentUser.password = textViewPassword.text.toString()

        if (DataSingleton.GetLogin() == "doctor")
        {
            val doctorIntent = Intent(this, DoctorActivity::class.java)
            startActivity(doctorIntent)
        }
        else if (DataSingleton.GetLogin() == "patient")
        {
                val patientIntent = Intent(this, PatientActivity::class.java)
                startActivity(patientIntent)
        }
        else
        {
            val queue = Volley.newRequestQueue(this)
            val url = "http://10.0.2.2:8080/user/login"

            val stringRequest = object: JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    CurrentUser.id = response.get("id").toString().toLong()
                    CurrentUser.name = response.get("name").toString()
                    CurrentUser.surname = response.get("surname").toString()
                    CurrentUser.role = UserRole.valueOf(response.get("role").toString())
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Error: ${error}", Toast.LENGTH_SHORT).show()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "Basic ${Base64.encodeToString("${DataSingleton.GetLogin()}:${DataSingleton.GetPassword()}".toByteArray(), Base64.DEFAULT)}"
                    return headers
                }
            }
            queue.add(stringRequest)
            if (CurrentUser.role == UserRole.ROLE_DOCTOR) {
                val doctorIntent = Intent(this, DoctorActivity::class.java)
                startActivity(doctorIntent)
            }
            if (CurrentUser.role == UserRole.ROLE_PATIENT) {
                val patientIntent = Intent(this, PatientActivity::class.java)
                startActivity(patientIntent)
            }
        }
    }

    fun registerButtonClicked(view: View) {
        val registerIntent = Intent(this, RegisterActivity::class.java)
        startActivity(registerIntent)
    }

}