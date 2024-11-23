package com.example.timer.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.timer.IMainActivityChangeEnablence
import com.example.timer.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [TimerModification.newInstance] factory method to
 * create an instance of this fragment.
 */
class TimerModification(val mainActivity: IMainActivityChangeEnablence) : Fragment() {
    private lateinit var tensOfMinutes: EditText
    private lateinit var unitsOfMinutes: EditText
    private lateinit var tensOfSeconds: EditText
    private lateinit var unitsOfSeconds: EditText

    //"toString()" getters:
    fun getTensOfMinutes(): String = tensOfMinutes.text.toString()
    fun getUnitsOfMinutes(): String = unitsOfMinutes.text.toString()
    fun getTensOfSeconds(): String = tensOfSeconds.text.toString()
    fun getUnitsOfSeconds(): String = unitsOfSeconds.text.toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //No arguments at this moment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tensOfMinutes = view.findViewById(R.id.tens_of_minutes_edit_text)
        unitsOfMinutes = view.findViewById(R.id.units_of_minutes_edit_text)
        tensOfSeconds = view.findViewById(R.id.tens_of_seconds_edit_text)
        unitsOfSeconds = view.findViewById(R.id.units_of_seconds_edit_text)

        val editTextLostFocusChangeListener = View.OnFocusChangeListener { editText, hasFocus ->
            if (!hasFocus && editText is EditText) {
                if (editText.text.isEmpty()) {
                    editText.setText("0")
                }
            }
        }

        val editTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                editable?.let {
                    val newText = it.toString()
                    if (newText.isEmpty() || !isTimerLongerThan0Seconds()) {
                        mainActivity.changeStartEnablence(false)
                    }
                    else {
                        mainActivity.changeStartEnablence(true )
                    }
                }
            }
        }

        tensOfMinutes.onFocusChangeListener = editTextLostFocusChangeListener
        unitsOfMinutes.onFocusChangeListener = editTextLostFocusChangeListener
        tensOfSeconds.onFocusChangeListener = editTextLostFocusChangeListener
        unitsOfSeconds.onFocusChangeListener = editTextLostFocusChangeListener

        tensOfMinutes.addTextChangedListener(editTextChangedListener)
        unitsOfMinutes.addTextChangedListener(editTextChangedListener)
        tensOfSeconds.addTextChangedListener(editTextChangedListener)
        unitsOfSeconds.addTextChangedListener(editTextChangedListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer_modification, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TimerModification.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(mainActivity: IMainActivityChangeEnablence) =
            TimerModification(mainActivity).apply {
                arguments = Bundle().apply {
                    //No arguments at this moment
                }
            }
    }

    private fun isTimerLongerThan0Seconds(): Boolean = !(getTensOfMinutes() == "0"
            && getUnitsOfMinutes() == "0"
            && getTensOfSeconds() == "0"
            && getUnitsOfSeconds() == "0")
}