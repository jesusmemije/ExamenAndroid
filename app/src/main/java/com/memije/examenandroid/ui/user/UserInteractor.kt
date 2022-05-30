package com.memije.examenandroid.ui.user

import android.content.Context
import com.memije.examenandroid.room.ExamenRoomDatabase
import com.memije.examenandroid.room.dao.UserDao
import com.memije.examenandroid.room.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserInteractor(presenter: UserMVP.Presenter) : UserMVP.Interactor {

    private var mPresenter: UserMVP.Presenter = presenter
    private lateinit var userDao: UserDao

    override fun getDataUsersInteractor(context: Context?) {
        val db: ExamenRoomDatabase = ExamenRoomDatabase.getDatabase(context!!)
        userDao = db.userDao()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Obtenemos todos los usuarios de room
                val userAll: List<UserEntity> = userDao.getAll()
                if (userAll.isEmpty()) {
                    mPresenter.showErrorPresenter("Aún no hay usuarios registrados")
                } else {
                    mPresenter.showResultListPresenter(userAll, userAll.size)
                }
            } catch (e: Exception) {
                mPresenter.showErrorPresenter("Estamos experimentando problemas al obtener los usuarios")
            }
        }
    }

    override fun setInsertUserInteractor(context: Context?, user: UserEntity?) {
        val db: ExamenRoomDatabase = ExamenRoomDatabase.getDatabase(context!!)
        userDao = db.userDao()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Insertamos el user
                if (user != null) {
                    userDao.insert(user)
                    mPresenter.showResultInsertPresenter("La inserción se ha realizado con éxito")
                }
            } catch (e: Exception) {
                mPresenter.showErrorPresenter("Estamos experimentando problemas al insertar el usuario")
            }
        }
    }

}