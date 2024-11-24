package com.example.timer

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.timer.data.TimerData
import com.example.timer.fragments.ITimerRun
import com.example.timer.fragments.TimerModification
import com.example.timer.fragments.TimerRun

class MainActivity : AppCompatActivity(), IMainActivityOnTimerFinish, IMainActivityChangeEnablement {
    private lateinit var countdownTimer: CountdownTimer
    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var cancelButton: Button
    private lateinit var currentFragment: Fragment
    private val timerModificationFragment: TimerModification = TimerModification.newInstance(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startButton = findViewById(R.id.start_button)
        pauseButton = findViewById(R.id.pause_button)
        cancelButton = findViewById(R.id.cancel_button)

        if (savedInstanceState == null) {
            setCurrentFragment(timerModificationFragment)
        }

        //Button click listeners:
        startButton.setOnClickListener {
            if (currentFragment == timerModificationFragment) {
                val newTimerRunFragment = createTimerRunFragment()
                setCurrentFragment(newTimerRunFragment)
            }

            countdownTimer.startTimer()
            pauseButton.isEnabled = true
            cancelButton.isEnabled = true
            startButton.isEnabled = false
        }

        pauseButton.setOnClickListener {
            countdownTimer.pauseTimer()
            startButton.isEnabled = true
            pauseButton.isEnabled = false
        }

        cancelButton.setOnClickListener {
            countdownTimer.cancelTimer()
            startButton.isEnabled = true
            pauseButton.isEnabled = false
            cancelButton.isEnabled = false

            if (currentFragment != timerModificationFragment) {
                setCurrentFragment(timerModificationFragment)
            }
        }
    }


    //Creating and setting methods:
    private fun createTimerRunFragment(): TimerRun {
        val timerData = TimerData(timerModificationFragment.getTensOfMinutes(),
            timerModificationFragment.getUnitsOfMinutes(),
            timerModificationFragment.getTensOfSeconds(),
            timerModificationFragment.getUnitsOfSeconds())

        val newTimerRunFragment = TimerRun.newInstance(timerData.tensOfMinutes,
            timerData.unitsOfMinutes,
            timerData.tensOfSeconds,
            timerData.unitsOfSeconds)

        createCountDownTimer(newTimerRunFragment, timerData)

        return newTimerRunFragment
    }

    private fun createCountDownTimer(timerRunFragment: ITimerRun, timerData: TimerData) {
        countdownTimer = CountdownTimer(this, timerRunFragment, timerData)
    }

    private fun setCurrentFragment(fragment: Fragment) {
        currentFragment = fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


    //Main activity override methods:
    override fun onDestroy() {
        super.onDestroy()
        countdownTimer.cancelTimer()
    }


    //Interfaces members:
    override fun onFinish() {
        Toast.makeText(this, "Timer finished!", Toast.LENGTH_LONG).show()
        cancelButton.performClick()
    }

    override fun changeStartEnablement(enablement: Boolean) {
        startButton.isEnabled = enablement
    }
}