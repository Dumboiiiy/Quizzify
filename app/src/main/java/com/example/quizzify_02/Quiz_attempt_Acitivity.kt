package com.example.quizzify_02

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.quizzify_02.databinding.ActivityQuizAttemptAcitivityBinding
import com.example.quizzify_02.databinding.ScoreDialogBinding
import com.google.android.material.progressindicator.LinearProgressIndicator

class Quiz_attempt_Acitivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        var questionModelList: List<QuestionModel> = listOf()
        var time: String = ""
    }

    lateinit var binding: ActivityQuizAttemptAcitivityBinding
    var currentQuestionIndex = 0;
    var selectedoption = ""
    var score = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizAttemptAcitivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btn0.setOnClickListener(this@Quiz_attempt_Acitivity)
            btn1.setOnClickListener(this@Quiz_attempt_Acitivity)
            btn2.setOnClickListener(this@Quiz_attempt_Acitivity)
            btn3.setOnClickListener(this@Quiz_attempt_Acitivity)
            nxtBtn.setOnClickListener(this@Quiz_attempt_Acitivity)

        }
        loadQuestions()
        startTimer()
    }

    private fun startTimer(){
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis,1000L){
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished /1000
                val minutes = seconds/60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text = String.format("%02d:%02d", minutes,remainingSeconds)

            }

            override fun onFinish() {
                //Finish the quiz
            }

        }.start()
    }

    private fun loadQuestions() {

        selectedoption = ""
        if(currentQuestionIndex == questionModelList.size){
            finishQuiz()
            return
        }

        binding.apply {
            val progress =
                ((currentQuestionIndex + 1).toFloat() / questionModelList.size.toFloat() * 100).toInt()
            questionIndicatorTextview.text =
                "Question ${currentQuestionIndex + 1}/ ${questionModelList.size}"
            questionProgressIndicator.progress =
                (currentQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100).toInt()
            questionTextview.text = questionModelList[currentQuestionIndex].question
            btn0.text = questionModelList[currentQuestionIndex].options[0]
            btn1.text = questionModelList[currentQuestionIndex].options[1]
            btn2.text = questionModelList[currentQuestionIndex].options[2]
            btn3.text = questionModelList[currentQuestionIndex].options[3]

        }
    }

    override fun onClick(view: View?) {

        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.color3))
            btn1.setBackgroundColor(getColor(R.color.color3))
            btn2.setBackgroundColor(getColor(R.color.color3))
            btn3.setBackgroundColor(getColor(R.color.color3))
            btn0.setTextColor(getColor(R.color.color2))
            btn1.setTextColor(getColor(R.color.color2))
            btn2.setTextColor(getColor(R.color.color2))
            btn3.setTextColor(getColor(R.color.color2))


        }

        val clickedBtn = view as Button
        if(clickedBtn.id==R.id.nxt_btn){
            //next button is clicked
            if(selectedoption.isEmpty()){
                Toast.makeText(applicationContext,"Please select answer to continue",Toast.LENGTH_SHORT).show()
                return;
            }
            if (selectedoption == questionModelList[currentQuestionIndex].correct){
                score++
                Log.i("Score of Quiz", score.toString())
            }
            currentQuestionIndex++
            loadQuestions()
        }
        else{
            //options button is clicked
            selectedoption = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.color1))
            clickedBtn.setTextColor(getColor(R.color.color3))
        }
    }

    private fun finishQuiz() {
        val totalQuestions = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat()) * 100).toInt()

        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage %"
            if (percentage > 60) {
                scoreTitle.text = "Congrats! You have passed"
                scoreTitle.setTextColor(Color.BLUE)
            } else {
                scoreTitle.text = "Oops! You have failed"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "$score out of $totalQuestions are correct"
            finishBtn.setOnClickListener {
                finish()
            }

            AlertDialog.Builder(this@Quiz_attempt_Acitivity)
                .setView(dialogBinding.root)
                .setCancelable(false)
                .show()
        }
    }
    }
