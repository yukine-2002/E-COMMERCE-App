package com.app.figure.Model

data class userModel(
    val authToken: Any,
    val id_ac: Int,
    val name: Any,
    val password: String,
    val provider: Any,
    val provider_id: Any,
    val quyen: Int,
    val username: String
)

data class person(
    var adress: String,
    val authToken: String,
    var cmnd: String,
    val created_at: String,
    var dates: String,
    var email: String,
    var img : String,
    var fullname: String,
    val id_ac: Int,
    val id_per: Int,
    val name: String,
    val password: String,
    val provider: Any,
    val provider_id: Any,
    val quyen: Int,
    var sdt: Int,
    val updated_at: String,
    val username: String,
    val quantity: Int,
    val price :  Int
)

data class responeLogin(
   var status : Int,
   var user : userModel,
   var token : String
)

data class googleData(var displayName : String,var givenNane : String, var email : String, val id : String)