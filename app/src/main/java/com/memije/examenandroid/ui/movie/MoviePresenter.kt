package com.memije.examenandroid.ui.movie

import android.content.Context
import com.memije.examenandroid.room.entity.MovieEntity

class MoviePresenter(view: MovieMVP.View): MovieMVP.Presenter {

    private var mView: MovieMVP.View = view
    private var mInteractor: MovieMVP.Interactor = MovieInteractor(this)

    override fun showResultPresenter(result: List<MovieEntity?>?, size: Int) {
        mView.showResultView(result, size)
    }

    override fun showErrorPresenter(result: String?) {
        mView.showErrorView(result)
    }

    override fun getDataMoviesPresenter(context: Context?) {
        mInteractor.getDataMoviesInteractor(context)
    }
}