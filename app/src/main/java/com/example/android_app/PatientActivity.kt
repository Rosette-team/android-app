package com.example.android_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class PatientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)
    }
    fun writeToDoctorClicked(view: View) {
        val writeToDoctorIntent = Intent(this, WriteToDoctor::class.java)
        startActivity(writeToDoctorIntent)
    }
}