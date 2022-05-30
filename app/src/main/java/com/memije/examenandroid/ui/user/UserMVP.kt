package com.memije.examenandroid.ui.user

import android.content.Context
import com.memije.examenandroid.room.entity.UserEntity

interface UserMVP {
    interface View {
        fun showResultListView(result: List<UserEntity?>?, size: Int)
        fun showResultInsertView(result: String?)
        fun showErrorView(result: String?)
    }

    interface Presenter {
        fun showResultListPresenter(result: List<UserEntity?>?, size: Int)
        fun showResultInsertPresenter(result: String?)
        fun getDataUsersPresenter(context: Context?)
        fun setInsertUserPresenter(context: Context?, user: UserEntity?)
        fun showErrorPresenter(result: String?)
    }

    interface Interactor {
        fun getDataUsersInteractor(context: Context?)
        fun setInsertUserInteractor(context: Context?, user: UserEntity?)
    }
}