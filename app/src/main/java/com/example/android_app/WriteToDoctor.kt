package com.example.android_app


import CurrentUser
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject


class WriteToDoctor : AppCompatActivity() {

    var doctorIdList = ArrayList<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_to_doctor)

        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:8080/department"

        var departmentIdsList = ArrayList<Long>()
        var departmentNameList = ArrayList<String>()

        var adapterClinic: ArrayAdapter<String>
        var spinnerC: Spinner

        val context = this

        var doctorList = ArrayList<String>()
        var adapterDoctorCategory : ArrayAdapter<String>
        var spinnerDC: Spinner

        val departmentJsonRequest = object: JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)
                    departmentIdsList.add(jsonObject.get("id").toString().toLong())
                    departmentNameList.add(jsonObject.get("name").toString())
                }
                adapterClinic = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, departmentNameList)
                adapterClinic.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerC = findViewById<Spinner>(R.id.spinnerClinic)
                spinnerC.adapter = adapterClinic
                spinnerC.onItemSelectedListener = object: OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        doctorList = ArrayList<String>()
                        val doctor_url = "http://10.0.2.2:8080/doctor?departmentId=${departmentIdsList[p2]}"
                        val doctorsJsonRequest = object: JsonArrayRequest(Request.Method.GET, doctor_url, null,
                            Response.Listener { response_doctors ->
                                for (i in 0 until response_doctors.length()){
                                    val doctorJsonObject = response_doctors.getJSONObject(i)
                                    doctorIdList.add(doctorJsonObject.get("id").toString().toLong())
                                    doctorList.add("${doctorJsonObject.get("name")} ${doctorJsonObject.get("surname")}, ${doctorJsonObject.get("speciality")}")
                                }
                                adapterDoctorCategory = ArrayAdapter<String>(
                                    context,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    doctorList
                                )
                                adapterDoctorCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                spinnerDC = findViewById<Spinner>(R.id.spinnerDoctorCategory)
                                spinnerDC.adapter = adapterDoctorCategory
                            },
                            Response.ErrorListener { error ->
                                Toast.makeText(context, "Error: ${error}", Toast.LENGTH_SHORT).show()
                            }){
                            override fun getHeaders(): MutableMap<String, String> {
                                val headers = HashMap<String, String>()
                                headers["Authorization"] = "Basic ${Base64.encodeToString("${CurrentUser.username}:${CurrentUser.password}".toByteArray(), Base64.DEFAULT)}"
                                return headers
                            }
                        }
                        queue.add(doctorsJsonRequest)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error: ${error}", Toast.LENGTH_SHORT).show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Basic ${Base64.encodeToString("${CurrentUser.username}:${CurrentUser.password}".toByteArray(), Base64.DEFAULT)}"
                return headers
            }
        }
        queue.add(departmentJsonRequest)

    }

    fun buttonWriteClicked(view: View) {
        val doctorId = doctorIdList[findViewById<Spinner>(R.id.spinnerDoctorCategory).selectedItemPosition]
        val date = findViewById<TextView>(R.id.editTextAppointmentDate).text
        val time = findViewById<TextView>(R.id.editTextAppointmentTime).text
        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:8080/appointment"
        var jsonObject = JSONObject()
        jsonObject.put("patientId", CurrentUser.id)
        jsonObject.put("doctorId", doctorId)
        jsonObject.put("date", date)
        jsonObject.put("time", time)
        jsonObject.put("online", false)
        jsonObject.put("consultationLink", "")
        val jsonStringBody = jsonObject.toString()

        val stringRequest = object: StringRequest(
            Request.Method.POST, url,
            Response.Listener { _ ->
                Toast.makeText(this, "Запись прошла успешно", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error: ${error}", Toast.LENGTH_SHORT).show()
            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return jsonStringBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Basic ${Base64.encodeToString("${CurrentUser.username}:${CurrentUser.password}".toByteArray(), Base64.DEFAULT)}"
                return headers
            }
        }
        queue.add(stringRequest)
    }

}