package com.example.tablayout.UserActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.tablayout.R
import com.example.tablayout.ui.main.Profile.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {


    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var name: EditText? = null
    private var aName:String?=null

//    companion object{
//        val TAG ="UpdateActivity"
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
            initialise()

        editProfile.setOnClickListener {

//            updateData()
            startActivity(Intent(this, ProfileFragment::class.java))
           // Toast.makeText(this,"Update Successfully", Toast.LENGTH_SHORT).show()
        }

//        ib_profile_pic.setOnClickListener {
//            Log.d(TAG, "Try to show photo selector")
//
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/*"
//            startActivityForResult(intent, 0)
//        }

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
//            // proceed and check what the selected image was....
//            Log.d(TAG, "Photo was selected")
//
//            pictureUrl = data.data
//
//            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, pictureUrl)
//
//            selectphoto_imageview_register.setImageBitmap(bitmap)
//
//            ib_profile_pic.alpha = 0f
//        }
//    }

    private fun initialise(){

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        name = findViewById<View>(R.id.up_username) as EditText


    }

    override fun onStart() {
        super.onStart()

        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)


        aName = up_username.text.toString()

        if (aName=="") {

            mUserReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    name?.setText(snapshot.child("name").value as String)


                    editProfile.setOnClickListener {

                        //uploadImageToFirebaseStorage()

                        var user = Users(
                            mUser.email.toString(),
                            name!!.text.toString()
                        )

                        mUserReference.setValue(user)

                        Toast.makeText(this@UpdateActivity,"Username successfully updated!!",Toast.LENGTH_SHORT).show()

                        finish()
                    }

                }
                override fun onCancelled(databaseError: DatabaseError) {

                    Toast.makeText(this@UpdateActivity,"Database error",Toast.LENGTH_SHORT).show()

                }
            })
        }else{

            Toast.makeText(this,"Please enter name to edit",Toast.LENGTH_SHORT).show()

        }
    }



//    var pictureUrl: Uri? = null
//
//
//    private fun uploadImageToFirebaseStorage() {
//        if (pictureUrl == null) return
//
//        val filename = UUID.randomUUID().toString()
//        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
//
//        ref.putFile(pictureUrl!!)
//            .addOnSuccessListener {
//                Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")
//
//                ref.downloadUrl.addOnSuccessListener {
//                    Log.d(TAG, "File Location: $it")
//
//                }
//            }
//            .addOnFailureListener {
//                Log.d(TAG, "Failed to upload image to storage: ${it.message}")
//            }
//    }





}
