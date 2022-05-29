package com.memije.examenandroid.ui.user

import android.content.Context
import com.memije.examenandroid.room.entity.UserEntity

interface UserMVP {

    interface View {
        fun showResultView(result: List<UserEntity?>?, size: Int)
        fun showErrorView(result: String?)
    }

    interface Presenter {
        fun showResultPresenter(result: List<UserEntity?>?, size: Int)
        fun showErrorPresenter(result: String?)
        fun getDataUsersPresenter(context: Context?)
    }

    interface Interactor {
        fun getDataUsersInteractor(context: Context?)
    }

}