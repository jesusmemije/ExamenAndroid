package com.memije.examenandroid.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.memije.examenandroid.room.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM user_table")
    fun getAll(): List<UserEntity>
}