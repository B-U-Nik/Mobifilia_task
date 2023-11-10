package com.example.mobifillatask.viewmodels

import com.example.mobifillatask.api.RetrofitInstance
import com.example.mobifillatask.models.LoginRequest
import com.example.mobifillatask.utils.SessionManager


class MobifiliaRepository {


    suspend fun userLogin(loginRequest: LoginRequest) = RetrofitInstance().api.userLogin(loginRequest)

}