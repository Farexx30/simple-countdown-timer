package com.example.timer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.timer.R
import com.example.timer.data.TimerData

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TENS_OF_MINUTES = "param1"
private const val ARG_UNITS_OF_MINUTES = "param2"
private const val ARG_TENS_OF_SECONDS = "param3"
private const val ARG_UNITS_OF_SECONDS = "param4"

/**
 * A simple [Fragment] subclass.
 * Use the [TimerRun.newInstance] factory method to
 * create an instance of this fragment.
 */
class TimerRun : Fragment(), ITimerRun {
    private var tensOfMinutes: String? = null
    private var unitsOfMinutes: String? = null
    private var tensOfSeconds: String? = null
    private var unitsOfSeconds: String? = null

    private lateinit var tensOfMinutesTextView: TextView
    private lateinit var unitsOfMinutesTextView: TextView
    private lateinit var tensOfSecondsTextView: TextView
    private lateinit var unitsOfSecondsTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tensOfMinutes = it.getString(ARG_TENS_OF_MINUTES)
            unitsOfMinutes = it.getString(ARG_UNITS_OF_MINUTES)
            tensOfSeconds = it.getString(ARG_TENS_OF_SECONDS)
            unitsOfSeconds= it.getString(ARG_UNITS_OF_SECONDS)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tensOfMinutesTextView = view.findViewById(R.id.tens_of_minutes_text_view)
        unitsOfMinutesTextView = view.findViewById(R.id.units_of_minutes_text_view)
        tensOfSecondsTextView = view.findViewById(R.id.tens_of_seconds_text_view)
        unitsOfSecondsTextView = view.findViewById(R.id.units_of_seconds_text_view)

        tensOfMinutesTextView.text = tensOfMinutes
        unitsOfMinutesTextView.text = unitsOfMinutes
        tensOfSecondsTextView.text = tensOfSeconds
        unitsOfSecondsTextView.text = unitsOfSeconds
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer_run, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @param param3 Parameter 3.
         * @param param4 Parameter 4.
         * @return A new instance of fragment TimerRun.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String, param4: String) =
            TimerRun().apply {
                arguments = Bundle().apply {
                    putString(ARG_TENS_OF_MINUTES, param1)
                    putString(ARG_UNITS_OF_MINUTES, param2)
                    putString(ARG_TENS_OF_SECONDS, param3)
                    putString(ARG_UNITS_OF_SECONDS, param4)
                }
            }
    }

    //Interface members:
    override fun updateTimeLeft(currentTime: TimerData) {
        tensOfMinutesTextView.text = currentTime.tensOfMinutes
        unitsOfMinutesTextView.text = currentTime.unitsOfMinutes
        tensOfSecondsTextView.text = currentTime.tensOfSeconds
        unitsOfSecondsTextView.text = currentTime.unitsOfSeconds
    }
}