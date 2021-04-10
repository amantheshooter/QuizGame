package com.example.logoquizgame.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide.with
import com.example.logoquizgame.R
import com.example.logoquizgame.model.LogoData
import com.example.logoquizgame.viewmodel.LogoListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.String
import java.util.*


class MainActivity : AppCompatActivity() {

    private var myList: Array<LogoData>? = null

    // Is the stopwatch running?
    private var running = false

    private var wasRunning = false
    private lateinit var logoListViewModel: LogoListViewModel
    var seconds = 0
    var correctAnswer: kotlin.String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logoListViewModel = ViewModelProviders.of(this).get(
            LogoListViewModel::class.java
        )

        seconds = logoListViewModel.seconds
        logoListViewModel.logoDetails.observe(this, Observer {
            if (it!!.isNotEmpty()) {
                myList = it
                setUpUiData(getRandomElement(it))
            }
        })

        done.setOnClickListener {
            if (text_guess != null && text_guess.text.isNotEmpty()) {
                if (validateAnswer(text_guess)) {
                    Toast.makeText(this@MainActivity, "Correct Answer", Toast.LENGTH_LONG)
                        .show()
                    running = false
                    score.text = "Your Score: $seconds"
                    score.visibility = View.VISIBLE
                    rest.visibility = View.VISIBLE
                } else
                    Toast.makeText(this@MainActivity, "Wrong Answer", Toast.LENGTH_LONG).show()
            }
        }

        rest.setOnClickListener {
            running = true;
            seconds = 0;
            text_guess.setText("")
            score.visibility = View.GONE
            rest.visibility = View.GONE
            setUpUiDataRefresh(getRandomElement(myList!!))
        }
    }

    private fun setUpUiDataRefresh(randomElement: LogoData) {
        with(this)
            .load(randomElement.imgUrl)
            .into(logo);
        correctAnswer = randomElement.name!!
    }

    private fun validateAnswer(textGuess: EditText?): Boolean {
        return correctAnswer.equals(textGuess!!.text.trim().toString(), true)
    }

    private fun setUpUiData(randomLogo: LogoData) {
        with(this)
            .load(randomLogo.imgUrl)
            .into(logo);
        correctAnswer = randomLogo.name!!
        runTimer()
    }

    private fun getRandomElement(list: Array<LogoData>): LogoData {
        val rand = Random()
        return list[rand.nextInt(list.size)]
    }


    private fun runTimer() {

        // Get the text view.
        val timeView = findViewById<TextView>(
            R.id.timer
        )
        // Creates a new Handler
        val handler = Handler()

        handler.post(object : Runnable {
            override fun run() {
                val minutes: Int = seconds % 3600 / 60
                val secs: Int = seconds % 60

                // Format the seconds into hours, minutes,
                // and seconds.
                val time = String
                    .format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        minutes, secs
                    )

                // Set the text view text.
                timeView.text = time

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        running = true
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }
}