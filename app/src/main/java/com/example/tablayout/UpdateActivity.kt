package com.example.tablayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.tablayout.ui.main.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_update.editProfile
import java.lang.Exception

class UpdateActivity : AppCompatActivity() {


    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var name: EditText? = null
    private var email: EditText? = null
    private var password: EditText? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
       initialise()

        editProfile.setOnClickListener {

            updateData()
            startActivity(Intent(this,ProfileFragment::class.java))
           // Toast.makeText(this,"Update Successfully", Toast.LENGTH_SHORT).show()
        }

    }

    fun updateData() {

        try{

            val ref = FirebaseDatabase.getInstance().getReference("Users")
            val name = Users().name

            ref.addListenerForSingleValueEvent(object : ValueEventListener{

                override fun onCancelled(data: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }


                override fun onDataChange(snapshot: DataSnapshot) {

                    val id = mAuth!!.currentUser!!.uid

                    val update = Users(name,
                                        email!!.text.toString(),
                                        password!!.text.toString())
                    ref.child(id).setValue(update)

                    Toast.makeText(baseContext,"Update Successfully!!",Toast.LENGTH_SHORT).show()

                }

            })



        }catch (e: Exception){

            Toast.makeText(baseContext,"Update failed...",Toast.LENGTH_SHORT).show()
        }


    }

    private fun initialise(){

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        name = findViewById<EditText>(R.id.up_username) as EditText
        email = findViewById<EditText>(R.id.up_email) as EditText
        password = findViewById<EditText>(R.id.up_password) as EditText


    }

    override fun onStart() {
        super.onStart()

        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)

        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                name?.setText(snapshot.child("name").value as String)
                email?.setText(snapshot.child("email").value as String)
                password?.setText(snapshot.child("password").value as String)

            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }



}