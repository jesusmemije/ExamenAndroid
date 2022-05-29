package com.memije.examenandroid.ui.user

import android.content.Context
import com.memije.examenandroid.room.ExamenRoomDatabase
import com.memije.examenandroid.room.dao.UserDao
import com.memije.examenandroid.room.entity.UserEntity

class UserInteractor(presenter: UserMVP.Presenter) : UserMVP.Interactor {

    private var mPresenter: UserMVP.Presenter = presenter
    private lateinit var userDao: UserDao

    override fun getDataUsersInteractor(context: Context?) {
        val db: ExamenRoomDatabase = ExamenRoomDatabase.getDatabase(context!!)
        userDao = db.userDao()

        // Obtenemos todos los usuarios de room
        val userAll: List<UserEntity> = userDao.getAll()
        mPresenter.showResultPresenter(userAll, userAll.size)
    }

}