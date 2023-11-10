package com.example.mobifillatask.roomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [UserDetail::class], version = 1,exportSchema = false)
abstract class UserDetailDatabase : RoomDatabase() {
   abstract val userDetailDao: UserDetailDao

   companion object {
      @Volatile
      private var INSTANCE: UserDetailDatabase? = null
      fun getInstance(context: Context): UserDetailDatabase {
         synchronized(this) {
            var instance = INSTANCE
            if (instance == null) {
               instance = Room.databaseBuilder(
                  context.applicationContext,
                  UserDetailDatabase::class.java,
                  "userDetail"
               ).build()
            }
            return instance
         }
      }
   }
}

