package com.example.android_app.ui.my_timetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.android_app.R
import com.example.android_app.databinding.FragmentMyTimetableBinding

class MyTimetableFragment : Fragment() {

    private var _binding: FragmentMyTimetableBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMyTimetableBinding.inflate(inflater, container, false)
        var textView = TextView(this.context)
        textView.text = "Hello World!"
        val appointmentView1: View = inflater.inflate(R.layout.doctor_appointment, null)
        appointmentView1.findViewById<TextView>(R.id.appointmentPatientFullName).text = "Dude"
        val appointmentView2: View = inflater.inflate(R.layout.doctor_appointment, null)
        binding.timetableLayout.addView(appointmentView1)
        binding.timetableLayout.addView(appointmentView2)
        // binding.timetableLayout.addView(textView)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}