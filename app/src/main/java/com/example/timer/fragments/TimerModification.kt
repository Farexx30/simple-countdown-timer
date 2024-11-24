package com.example.timer.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.timer.IMainActivityChangeEnablement
import com.example.timer.R
import com.example.timer.utils.toInt


class TimerModification(val mainActivity: IMainActivityChangeEnablement) : Fragment() {
    private lateinit var tensOfMinutes: EditText
    private lateinit var unitsOfMinutes: EditText
    private lateinit var tensOfSeconds: EditText
    private lateinit var unitsOfSeconds: EditText
    private lateinit var plusTensOfMinutes: Button
    private lateinit var plusUnitsOfMinutes: Button
    private lateinit var plusTensOfSeconds: Button
    private lateinit var plusUnitsOfSeconds: Button
    private lateinit var minusTensOfMinutes: Button
    private lateinit var minusUnitsOfMinutes: Button
    private lateinit var minusTensOfSeconds: Button
    private lateinit var minusUnitsOfSeconds: Button

    inner class CustomTextWatcher(private val changedEditText: EditText) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            editable?.let {
                val newText = it.toString()

                if (newText.isEmpty() || !isTimerLongerThan0Seconds()) {
                    mainActivity.changeStartEnablement(false)
                } else {
                    mainActivity.changeStartEnablement(true)
                }

                val newNumber = newText.toIntOrNull()
                when(changedEditText) {
                    tensOfMinutes -> changeTensOfMinutesButtons(newNumber)
                    unitsOfMinutes -> changeUnitsOfMinutesButtons(newNumber)
                    tensOfSeconds-> changeTensOfSecondsButtons(newNumber)
                    unitsOfSeconds -> changeUnitsOfSecondsButtons(newNumber)
                    else -> throw Exception("Invalid input (not even possible)")
                }
            }
        }
    }


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
        plusTensOfMinutes = view.findViewById(R.id.plus_tens_of_minutes_button)
        plusUnitsOfMinutes = view.findViewById(R.id.plus_units_of_minutes_button)
        plusTensOfSeconds = view.findViewById(R.id.plus_tens_of_seconds_button)
        plusUnitsOfSeconds = view.findViewById(R.id.plus_units_of_seconds_button)
        minusTensOfMinutes = view.findViewById(R.id.minus_tens_of_minutes_button)
        minusUnitsOfMinutes = view.findViewById(R.id.minus_units_of_minutes_button)
        minusTensOfSeconds = view.findViewById(R.id.minus_tens_of_seconds_button)
        minusUnitsOfSeconds = view.findViewById(R.id.minus_units_of_seconds_button)

        //Listeners:
        val editTextLostFocusChangeListener = View.OnFocusChangeListener { editText, hasFocus ->
            if (!hasFocus && editText is EditText) {
                if (editText.text.isEmpty()) {
                    editText.setText("0")
                }
            }
        }

        tensOfMinutes.onFocusChangeListener = editTextLostFocusChangeListener
        unitsOfMinutes.onFocusChangeListener = editTextLostFocusChangeListener
        tensOfSeconds.onFocusChangeListener = editTextLostFocusChangeListener
        unitsOfSeconds.onFocusChangeListener = editTextLostFocusChangeListener

        tensOfMinutes.addTextChangedListener(CustomTextWatcher(tensOfMinutes))
        unitsOfMinutes.addTextChangedListener(CustomTextWatcher(unitsOfMinutes))
        tensOfSeconds.addTextChangedListener(CustomTextWatcher(tensOfSeconds))
        unitsOfSeconds.addTextChangedListener(CustomTextWatcher(unitsOfSeconds))


        plusTensOfMinutes.setOnClickListener {
            clearAllEditTextsFocuses()

            var currentText = tensOfMinutes.text.toInt()

            if (currentText < 9) {
                val newText = (++currentText).toString()
                tensOfMinutes.setText(newText)
            }
        }

        plusUnitsOfMinutes.setOnClickListener {
            clearAllEditTextsFocuses()

            var currentText = unitsOfMinutes.text.toInt()

            if (currentText < 9) {
                val newText = (++currentText).toString()
                unitsOfMinutes.setText(newText)
            }
        }

        plusTensOfSeconds.setOnClickListener {
            clearAllEditTextsFocuses()

            var currentText = tensOfSeconds.text.toInt()

            if (currentText < 5) {
                val newText = (++currentText).toString()
                tensOfSeconds.setText(newText)
            }
        }

        plusUnitsOfSeconds.setOnClickListener {
            clearAllEditTextsFocuses()

            var currentText = unitsOfSeconds.text.toInt()

            if (currentText < 9) {
                val newText = (++currentText).toString()
                unitsOfSeconds.setText(newText)
            }
        }

        minusTensOfMinutes.setOnClickListener {
            clearAllEditTextsFocuses()

            var currentText = tensOfMinutes.text.toInt()

            if (currentText > 0) {
                val newText = (--currentText).toString()
                tensOfMinutes.setText(newText)
            }
        }

        minusUnitsOfMinutes.setOnClickListener {
            clearAllEditTextsFocuses()

            var currentText = unitsOfMinutes.text.toInt()

            if (currentText > 0) {
                val newText = (--currentText).toString()
                unitsOfMinutes.setText(newText)
            }
        }

        minusTensOfSeconds.setOnClickListener {
            clearAllEditTextsFocuses()

            var currentText = tensOfSeconds.text.toInt()

            if (currentText > 0) {
                val newText = (--currentText).toString()
                tensOfSeconds.setText(newText)
            }
        }

        minusUnitsOfSeconds.setOnClickListener {
            clearAllEditTextsFocuses()

            var currentText = unitsOfSeconds.text.toInt()

            if (currentText > 0) {
                val newText = (--currentText).toString()
                unitsOfSeconds.setText(newText)
            }
        }
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
        fun newInstance(mainActivity: IMainActivityChangeEnablement) =
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

    private fun clearAllEditTextsFocuses() {
        tensOfMinutes.clearFocus()
        unitsOfMinutes.clearFocus()
        tensOfSeconds.clearFocus()
        unitsOfSeconds.clearFocus()
    }

    private fun changeTensOfMinutesButtons(newNumber: Int?) {
        when(newNumber) {
            0 -> {
                minusTensOfMinutes.isEnabled = false
                plusTensOfMinutes.isEnabled = true
            }
            9 -> {
                minusTensOfMinutes.isEnabled = true
                plusTensOfMinutes.isEnabled = false
            }
            null -> {
                minusTensOfMinutes.isEnabled = false
                plusTensOfMinutes.isEnabled = false
            }
            else -> {
                minusTensOfMinutes.isEnabled = true
                plusTensOfMinutes.isEnabled = true
            }
        }
    }

    private fun changeUnitsOfMinutesButtons(newNumber: Int?) {
        when(newNumber) {
            0 -> {
                minusUnitsOfMinutes.isEnabled = false
                plusUnitsOfMinutes.isEnabled = true
            }
            9 -> {
                minusUnitsOfMinutes.isEnabled = true
                plusUnitsOfMinutes.isEnabled = false
            }
            null -> {
                minusUnitsOfMinutes.isEnabled = false
                plusUnitsOfMinutes.isEnabled = false
            }
            else -> {
                minusUnitsOfMinutes.isEnabled = true
                plusUnitsOfMinutes.isEnabled = true
            }
        }
    }

    private fun changeTensOfSecondsButtons(newNumber: Int?) {
        when(newNumber) {
            0 -> {
                minusTensOfSeconds.isEnabled = false
                plusTensOfSeconds.isEnabled = true
            }
            5 -> {
                minusTensOfSeconds.isEnabled = true
                plusTensOfSeconds.isEnabled = false
            }
            null -> {
                minusTensOfSeconds.isEnabled = false
                plusTensOfSeconds.isEnabled = false
            }
            else -> {
                minusTensOfSeconds.isEnabled = true
                plusTensOfSeconds.isEnabled = true
            }
        }
    }

    private fun changeUnitsOfSecondsButtons(newNumber: Int?) {
        when(newNumber) {
            0 -> {
                minusUnitsOfSeconds.isEnabled = false
                plusUnitsOfSeconds.isEnabled = true
            }
            9 -> {
                minusUnitsOfSeconds.isEnabled = true
                plusUnitsOfSeconds.isEnabled = false
            }
            null -> {
                minusUnitsOfSeconds.isEnabled = false
                plusUnitsOfSeconds.isEnabled = false
            }
            else -> {
                minusUnitsOfSeconds.isEnabled = true
                plusUnitsOfSeconds.isEnabled = true
            }
        }
    }
}