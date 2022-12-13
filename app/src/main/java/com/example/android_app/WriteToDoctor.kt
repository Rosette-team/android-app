package com.example.android_app


import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class WriteToDoctor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_to_doctor)

        val clinicList = arrayOf("Выбрать больницу", "Поликлиника №1", "Поликлиника №2", "Поликлиника №3")
        val adapterClinic = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, clinicList)
        adapterClinic.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var spinnerC: Spinner = findViewById<Spinner>(R.id.spinnerClinic)
        spinnerC.adapter = adapterClinic

        val doctorCategoryList = arrayOf("Выбрать врача", "Терапевт", "Лор", "Хирург")
        val adapterDoctorCategory = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, doctorCategoryList)
        adapterDoctorCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var spinnerDC: Spinner = findViewById<Spinner>(R.id.spinnerDoctorCategory)
        spinnerDC.adapter = adapterDoctorCategory

        val doctorFIOList = arrayOf("Ф.И.О. врача", "Иванов И.И.", "Попов П.П.")
        val adapterDoctorFIO = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, doctorFIOList)
        adapterDoctorFIO.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var spinnerDoctorFIO: Spinner = findViewById<Spinner>(R.id.spinnerDoctorFIO)
        spinnerDoctorFIO.adapter = adapterDoctorFIO

    }

    fun buttonWriteClicked(view: View) {
        Toast.makeText(this, "Успешно записано", Toast.LENGTH_SHORT).show()
    }

}