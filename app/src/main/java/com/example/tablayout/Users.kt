package com.example.tablayout

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Users(

                    val email:String="",
                    val name:String="",
                    @Exclude val uid:String="")
