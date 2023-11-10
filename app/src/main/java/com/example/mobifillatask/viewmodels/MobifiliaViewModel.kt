package com.example.mobifillatask.viewmodels

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobifillatask.models.LoginRequest
import com.example.mobifillatask.models.LoginResponse
import com.example.mobifillatask.utils.ConstantApp
import com.example.mobifillatask.utils.ResourceApp
import kotlinx.coroutines.launch
import retrofit2.Response

class MobifiliaViewModel(
    private val mobifiliaRepository: MobifiliaRepository
) : ViewModel() {


    val loginLive: MutableLiveData<ResourceApp<LoginResponse>> = MutableLiveData()


    //login
    fun userLogin(loginRequest: LoginRequest, context: Context, progressDialog: ProgressDialog) =
        viewModelScope.launch {
            try {
                if (ConstantApp.checkInternetConnection(context)) {
                    loginLive.postValue(ResourceApp.Loading())
                    val login = mobifiliaRepository.userLogin(loginRequest)
                    loginLive.postValue(handleUserLogin(login))
                } else {
                    Toast.makeText(context, "No internet connection!", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                progressDialog.dismiss()
            }
        }

    private fun handleUserLogin(response: Response<LoginResponse>): ResourceApp<LoginResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultSuccess ->
                return ResourceApp.Success(resultSuccess)
            }
        }
        return ResourceApp.Error(response.message())
    }


}
