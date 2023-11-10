package com.example.mobifillatask.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MobifiliaProviderFactroy(val mobifiliaRepository: MobifiliaRepository):
    ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MobifiliaViewModel(mobifiliaRepository) as T
    }

}