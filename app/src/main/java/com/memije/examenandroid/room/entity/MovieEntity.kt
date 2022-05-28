package com.memije.examenandroid.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
class MovieEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "idServer") val idServer: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: String,
    @ColumnInfo(name = "backdropPath") val backdropPath: String,
)