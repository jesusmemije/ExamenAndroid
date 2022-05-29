package com.memije.examenandroid.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class UserEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone") val phone: Long,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "address") val address: String,
)