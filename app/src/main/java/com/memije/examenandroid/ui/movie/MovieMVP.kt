package com.memije.examenandroid.ui.movie

import android.content.Context
import com.memije.examenandroid.room.entity.MovieEntity

interface MovieMVP {

    interface View {
        fun showResultView(result: List<MovieEntity?>?, size: Int)
        fun showErrorView(result: String?)
    }

    interface Presenter {
        fun showResultPresenter(result: List<MovieEntity?>?, size: Int)
        fun showErrorPresenter(result: String?)
        fun getDataMoviesPresenter(context: Context?)
    }

    interface Interactor {
        fun getDataMoviesInteractor(context: Context?)
    }

}