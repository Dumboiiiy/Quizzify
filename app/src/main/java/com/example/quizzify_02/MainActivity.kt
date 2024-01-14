package com.example.quizzify_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizzify_02.databinding.ActivityMainBinding
import com.example.quizzify_02.databinding.FragmentAddQuizBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var quizmodellist: MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizmodellist = mutableListOf()
        getDataFromFirebase()

        firebaseAuth = FirebaseAuth.getInstance()

    }


    private fun setupRecyclerView() {
        val adapter = QuizListAdapter(quizmodellist)
        binding.globalQuizzes.layoutManager = LinearLayoutManager(this)
        binding.globalQuizzes.adapter = adapter
    }


    private fun getDataFromFirebase() {
        FirebaseDatabase.getInstance().reference.get().addOnSuccessListener { datasnapshot ->
            if (datasnapshot.exists()) {
                for (snapshot in datasnapshot.children) {
                    val quizModel = snapshot.getValue(QuizModel::class.java)
                    if (quizModel != null) {
                        quizmodellist.add(quizModel)
                    }
                }
                setupRecyclerView()
            }
        }
    }
}