package com.memije.examenandroid.ui.user

import android.content.Context
import com.memije.examenandroid.room.entity.UserEntity

class UserPresenter(view: UserMVP.View) : UserMVP.Presenter {

    private var mView: UserMVP.View = view
    private var mInteractor: UserMVP.Interactor = UserInteractor(this)

    override fun showResultListPresenter(result: List<UserEntity?>?, size: Int) {
        mView.showResultListView(result, size)
    }

    override fun showResultInsertPresenter(result: String?) {
        mView.showResultInsertView(result)
    }

    override fun showErrorPresenter(result: String?) {
        mView.showErrorView(result)
    }

    override fun getDataUsersPresenter(context: Context?) {
        mInteractor.getDataUsersInteractor(context)
    }

    override fun setInsertUserPresenter(context: Context?, user: UserEntity?) {
        mInteractor.setInsertUserInteractor(context, user)
    }
}