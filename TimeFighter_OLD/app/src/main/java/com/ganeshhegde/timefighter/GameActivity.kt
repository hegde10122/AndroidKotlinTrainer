package com.ganeshhegde.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

//1 --- GameActivity is declared as extending AppCompatActivity. Subclasssing it is required to deal with the content onscreen
class GameActivity : AppCompatActivity() {

    internal lateinit var gameScoreTextView : TextView
    internal lateinit var timeLeftTextView : TextView
    internal lateinit var tapMeButton : Button
    internal var score = 0


    internal var gameStarted = false //Boolean to indicate whether the game has started
    internal lateinit var countDownTimer: CountDownTimer //countdown object
    internal var initialCountDown: Long = 60000 //total time interval
    internal var countDownInterval: Long = 1000 //rate at which the countdown will decrement
    internal var timeLeft = 60 //To be displayed to user

    companion object { //We create companion object that contains two String constants to track the variables you want to save when the orientation changes
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }


    //Name of the class assigned to the TAG variable
    //The convention is to use the class name in log messages, which makes it easier to see what class message is coming from
    internal val TAG = GameActivity::class.java.simpleName


    //2 onCreate -- It is the entrypoint to this activity. It starts with the keyword override meaning you will have to provide a custom implementation from the base AppCompatActivity class
    override fun onCreate(savedInstanceState: Bundle?) {
        //3 Calling the base implementation of onCreate is required.
        super.onCreate(savedInstanceState)
        //4 it takes the layout created and puts it on the device screen by passing in the identifier for the layout.
        setContentView(R.layout.activity_game)

        //connect views to the variables

        //findViewById searches through your activity_game layout file to find the view that has the corresponding ID and provides a reference to it that you can store as a
        //variable
        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        tapMeButton = findViewById(R.id.tap_me_button)

        //setOnClickListener attaches a click listener to the Button which calls incrementScore method.
        tapMeButton.setOnClickListener {v -> incrementScore()}

        Log.d(TAG,"onCreate called.Score is: $score")


        //reset the game

        if(savedInstanceState == null){
            resetGame()
        }
         else {
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeft = savedInstanceState.getInt(TIME_LEFT_KEY)
            restoreGame()
        }

    }

    private fun incrementScore(){//increment score logic

        if(!gameStarted){
            startGame()
        }
        score++

        //getString is an Activity-provided method that lets you grab strings from the R file name or ID.
        val newScore = getString(R.string.your_score, score.toString())
        gameScoreTextView.text = newScore

    }

    private fun restoreGame(){
        val restoredScore = getString(R.string.your_score,score.toString())
        gameScoreTextView.text = restoredScore

        val restoredTime = getString(R.string.time_left,timeLeft.toString())
        timeLeftTextView.text= restoredTime

        countDownTimer = object:CountDownTimer((timeLeft*1000).toLong(),countDownInterval){
            override fun onFinish() {
                endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000
                val timeLeftString = getString(R.string.time_left,timeLeft.toString())
                timeLeftTextView.text = timeLeftString
            }

        }

        countDownTimer.start()
        gameStarted = true
    }

    private fun resetGame() {//reset game logic

        //1 set the score to 0 and convert it into string
        score = 0
        val initialScore = getString(R.string.your_score, score.toString())

        //2 The string converted is used to initialise the TextView
        gameScoreTextView.text = initialScore

        //3 initial time text is also reset to 60 seconds
        val initialTimeLeft = getString(R.string.time_left, 60.toString())
        timeLeftTextView.text = initialTimeLeft

        //4 create a new CountDownTimer object with 1 second increments until it hits zero
        countDownTimer = object : CountDownTimer(initialCountDown,countDownInterval) {

            //5 The CountDownTimer has two overridden methods: onTick and onFinish

            override fun onFinish() {
                endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                //onTick is called at every interval you passed into the Timer, in this case after every second.
                //timeLeft property is updated with the time remaining by converting the milliseconds representation into seconds
                timeLeft = millisUntilFinished.toInt()  / 1000

                //timeLeftTextView is updated with this value calculated
                val timeLeftString = getString(R.string.time_left,timeLeft.toString())
                timeLeftTextView.text = timeLeftString


              }
        } //total of 60000 millis using 1000 millis interval
        //reset the game; hence false
        gameStarted = false
    }

    private fun startGame(){ //start game logic
        countDownTimer.start() //activate the timer
        gameStarted = true //game started!!
    }

    private fun endGame(){ //end game logic
        //A toast is a small alert that pops up briefly to inform you of some event that's occurred - in this case, the end of the game
        Toast.makeText(this,getString(R.string.game_over_message,score.toString()),Toast.LENGTH_LONG).show()
        resetGame()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        //bundle is a dictionary which android uses to pass values across different screens.
        outState.putInt(SCORE_KEY,score)
        outState.putInt(TIME_LEFT_KEY,timeLeft)
        countDownTimer.cancel()

        Log.d(TAG,"onSaveInstanceState:Saving score :$score and Time left: $timeLeft")
    }

    override fun onDestroy() {
        super.onDestroy() //call super implementation so your Activity can perform any essential cleanup
        Log.d(TAG,"onDestroy called")
    }

}
