package com.memije.examenandroid.ui.user

import android.content.Context
import com.memije.examenandroid.room.entity.UserEntity

class UserPresenter(view: UserMVP.View) : UserMVP.Presenter {

    private var mView: UserMVP.View = view
    private var mInteractor: UserMVP.Interactor = UserInteractor(this)

    override fun showResultPresenter(result: List<UserEntity?>?, size: Int) {
        mView.showResultView(result, size)
    }

    override fun showErrorPresenter(result: String?) {
        mView.showErrorView(result)
    }

    override fun getDataUsersPresenter(context: Context?) {
        mInteractor.getDataUsersInteractor(context)
    }
}