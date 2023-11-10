package com.example.mobifillatask.models
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: Login,
    @SerializedName("message") val message: String
)

data class Login(

    @SerializedName("token") val token: String?
)

data class LoginRequest(

    @SerializedName("email") val username: String,
    @SerializedName("password") val password: String

)