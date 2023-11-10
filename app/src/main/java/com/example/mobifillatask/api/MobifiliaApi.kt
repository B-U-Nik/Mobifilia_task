package com.example.mobifillatask.api



import com.example.mobifillatask.models.LoginRequest
import com.example.mobifillatask.models.LoginResponse
import retrofit2.Response
import retrofit2.http.*


interface MobifiliaApiApi {


    @POST("login")
    suspend fun userLogin(@Body loginRequest: LoginRequest): Response<LoginResponse>



}




