package com.example.mobifillatask.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager {

    private   var sharedPreferences: SharedPreferences
    private   var editor: SharedPreferences.Editor
    private   var mContext: Context
    private val PRIVATE_MODE = 0

    companion object {
        private const val PREF_NAME = "LOGIN"
        private const val IS_LOGIN = "IS_LOGIN"

        const val TOKEN = "token"
    }

    constructor(context: Context) {
        mContext = context
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = sharedPreferences.edit()
    }




}
