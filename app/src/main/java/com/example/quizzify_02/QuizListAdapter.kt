package com.example.quizzify_02

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzify_02.databinding.GlobalQuizItemRecyclerRowBinding

class QuizListAdapter(private val quizmodellist : List<QuizModel>) :
    RecyclerView.Adapter<QuizListAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: GlobalQuizItemRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: QuizModel) {
            binding.apply {
                quizTitle.text = model.title
                quizSubtitle.text = model.description
                quizTime.text = model.time + " min"
                root.setOnClickListener {
                    val intent = Intent(root.context, Quiz_attempt_Acitivity::class.java)
                    Quiz_attempt_Acitivity.questionModelList = model.questionlist
                    Quiz_attempt_Acitivity.time = model.time
                    root.context.startActivity(intent)
                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GlobalQuizItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return quizmodellist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(quizmodellist[position])
    }
}