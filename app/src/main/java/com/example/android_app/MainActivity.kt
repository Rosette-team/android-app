package com.example.android_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View;
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun entryButtonClicked(view: View) {
        var textViewLogin: TextView = findViewById<TextView>(R.id.editTextLogin)
        login = textViewLogin.text.toString()
        var textViewPassword: TextView = findViewById<TextView>(R.id.editTextPassword)
        password = textViewPassword.text.toString()

        if (login == "doctor")
        {
            setContentView(R.layout.activity_doctor)
        }
        else
        {
            if (login == "patient")
            {
                setContentView(R.layout.activity_patient)
            }
            else
            {
                val myToast = Toast.makeText(this, "Пользователь не существует", Toast.LENGTH_SHORT).show()
            }
        }
    }

    public lateinit var login: String
    public lateinit var password: String
}