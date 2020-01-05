package com.example.tablayout

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Users(   val name:String="",
                    val email:String="",
                    val password:String="",
                    @Exclude val uid:String="")
