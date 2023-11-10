package com.example.mobifillatask.roomDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userDetail")
data class UserDetail(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userDetail_id")
    var id: Int,

    @ColumnInfo(name = "userDetail_name")
    var name: String,

    @ColumnInfo(name = "userDetail_password")
    var password: String

)
