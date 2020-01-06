package com.example.tablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.exercise.view.*
import kotlinx.android.synthetic.main.list_layout.view.*

class WorkoutContent : AppCompatActivity() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_content)

        mDatabase = FirebaseDatabase.getInstance().getReference("Workout")
        mRecyclerView = findViewById(R.id.listWorkout)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))

        logRecyclerView()
    }

    private fun logRecyclerView(){



        var FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Exercise,ExerciseViewHolder>(

            Exercise::class.java,
            R.layout.exercise,
            ExerciseViewHolder::class.java,
            mDatabase


        ){
            override fun populateViewHolder(viewHolder: ExerciseViewHolder?, model: Exercise?, position: Int) {

                viewHolder?.itemView?.exercise_id?.text=model?.eID
                viewHolder?.itemView?.exerciseDesc?.text=model?.wDesc


            }

        }
        mRecyclerView.adapter = FirebaseRecyclerAdapter

    }

    class ExerciseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        }

}
