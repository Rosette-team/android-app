package com.example.android_app

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RegisterActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun regDoneButtonClicked(view: View) {
        val name = findViewById<TextView>(R.id.editTextRegisterName).text.toString()
        val surname = findViewById<TextView>(R.id.editTextRegisterSurname).text.toString()
        val username = findViewById<TextView>(R.id.editTextRegisterLogin).text.toString()
        val password = findViewById<TextView>(R.id.editTextRegisterPassword).text.toString()
        val retypePassword = findViewById<TextView>(R.id.editTextRetypePassword).text.toString()
        if (password != retypePassword) {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
        } else {
            val queue = Volley.newRequestQueue(this)
            val url = "http://10.0.2.2:8080/patient"
            var jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("surname", surname)
            jsonObject.put("username", username)
            jsonObject.put("password", password)
            val jsonStringBody = jsonObject.toString()

            val stringRequest = object: StringRequest(
                Request.Method.POST, url,
                Response.Listener { _ ->
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { error ->
                    findViewById<TextView>(R.id.debugTextView).text = error.toString()
                    Toast.makeText(this, "Error: ${error}", Toast.LENGTH_SHORT).show()
                }) {
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }

                override fun getBody(): ByteArray {
                    return jsonStringBody.toByteArray()
                }
            }
            queue.add(stringRequest)
        }
    }

}