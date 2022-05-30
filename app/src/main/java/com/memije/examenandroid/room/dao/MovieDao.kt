package com.memije.examenandroid.room.dao

import androidx.room.*
import com.memije.examenandroid.room.entity.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movieEntity: MovieEntity)

    @Update
    fun update(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie_table")
    fun getAll(): List<MovieEntity>

    @Query("DELETE FROM movie_table WHERE id = :id")
    fun deleteById(id: Int)
}