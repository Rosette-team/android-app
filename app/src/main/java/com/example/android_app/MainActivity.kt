package com.example.android_app

import DataSingleton
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View;
import android.widget.TextView
import android.widget.Toast
import android.content.Intent;


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun entryButtonClicked(view: View) {
        var textViewLogin: TextView = findViewById<TextView>(R.id.editTextLogin)
        DataSingleton.SetLogin(textViewLogin.text.toString())
        var textViewPassword: TextView = findViewById<TextView>(R.id.editTextPassword)
        DataSingleton.SetPassword(textViewPassword.text.toString())

        if (DataSingleton.GetLogin() == "doctor")
        {
            val doctorIntent = Intent(this, DoctorActivity::class.java)
            startActivity(doctorIntent)
        }
        else
        {
            if (DataSingleton.GetLogin() == "patient")
            {
                val patientIntent = Intent(this, PatientActivity::class.java)
                startActivity(patientIntent)
            }
            else
            {
                val message = Toast.makeText(this, "Пользователь не существует", Toast.LENGTH_SHORT).show()
            }
        }
    }

}