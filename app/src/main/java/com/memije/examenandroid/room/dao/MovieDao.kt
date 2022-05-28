package com.memije.examenandroid.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.memije.examenandroid.room.entity.MovieEntity

@Dao
interface MovieDao {
    @Insert
    fun insert(movieEntity: MovieEntity)

    @Update
    fun update(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie_table")
    fun getAll(): List<MovieEntity>

    @Query("DELETE FROM movie_table WHERE id = :id")
    fun deleteById(id: Int)
}