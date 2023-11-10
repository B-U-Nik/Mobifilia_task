package com.example.mobifillatask.roomDb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDetailDao {

    @Insert
    suspend fun insertUser(userDetail: UserDetail)

    @Query("SELECT * FROM userDetail")
    fun getUSer(): LiveData<List<UserDetail>>

    @Query("DELETE FROM userDetail")
    fun deleteAllUsers()
}