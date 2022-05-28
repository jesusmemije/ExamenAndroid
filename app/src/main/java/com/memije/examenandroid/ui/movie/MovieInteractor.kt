package com.memije.examenandroid.ui.movie

import android.content.Context
import com.memije.examenandroid.common.Constants
import com.memije.examenandroid.retrofit.APIService
import com.memije.examenandroid.retrofit.APIServiceAdapter
import com.memije.examenandroid.retrofit.response.MovieResponse
import com.memije.examenandroid.room.ExamenRoomDatabase
import com.memije.examenandroid.room.dao.MovieDao
import com.memije.examenandroid.room.entity.MovieEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieInteractor(presenter: MovieMVP.Presenter) : MovieMVP.Interactor {

    private var mPresenter: MovieMVP.Presenter = presenter
    private lateinit var apiService: APIService
    private lateinit var movieDao: MovieDao

    override fun getDataMoviesInteractor(context: Context?) {
        val db: ExamenRoomDatabase = ExamenRoomDatabase.getDatabase(context!!)
        movieDao = db.movieDao()

        CoroutineScope(Dispatchers.IO).launch {

            try {
                apiService = APIServiceAdapter.getService().create(APIService::class.java)
                val call = apiService.getPopularMovies(
                    Constants.API_KEY,
                    Constants.LANGUAGE,
                    Constants.PAGE
                )

                val mMovies = call.body()

                if (call.isSuccessful) {

                    // Obtenemos todos las películas de room
                    var movieAll: List<MovieEntity> = movieDao.getAll()

                    // Insertamos los datos en Room
                    val movieList: List<MovieResponse> = mMovies!!.results

                    if (movieAll.isNotEmpty()) {

                        // Actualizamos las películas
                        var count = 0
                        for (i in movieList) {

                            val movie = MovieEntity(
                                0,
                                i.id,
                                i.title,
                                i.overview,
                                i.releaseDate,
                                i.backdropPath
                            )

                            var statusDeleteMovie = false

                            for (a in movieAll) {
                                if (movie.idServer != a.idServer) {
                                    statusDeleteMovie = true
                                }
                            }

                            if (statusDeleteMovie) {
                                // Eliminamos
                                movieDao.deleteById(movieAll[count].id)
                            }

                            count++
                        }

                        // Actualizamos
                        movieAll = movieDao.getAll()
                        count = 0
                        for (i in movieList) {
                            val movie = MovieEntity(
                                0,
                                i.id,
                                i.title,
                                i.overview,
                                i.releaseDate,
                                i.backdropPath
                            )
                            var statusUpdateMovie = false

                            for (a in movieAll) {

                                if (movie.idServer == a.idServer) {
                                    statusUpdateMovie = true
                                }

                            }

                            if (statusUpdateMovie) {
                                movieDao.update(movie)
                            } else {
                                movieDao.insert(movie)
                            }

                            count++
                        }

                    } else {
                        // Registramos las películas por primera vez
                        for (i in movieList) {
                            val movie = MovieEntity(
                                0,
                                i.id,
                                i.title,
                                i.overview,
                                i.releaseDate,
                                i.backdropPath
                            )
                            movieDao.insert(movie)
                        }
                    }

                    movieAll = movieDao.getAll()
                    mPresenter.showResultPresenter(movieAll, movieAll.size)

                } else {
                    //show error
                    mPresenter.showErrorPresenter("Error al actualizar las nuevas películas del servidor")
                    // Obtenemos todos las películas de room
                    val movieAll: List<MovieEntity> = movieDao.getAll()
                    mPresenter.showResultPresenter(movieAll, movieAll.size)
                }
            } catch (e: Exception) {
                //show error
                mPresenter.showErrorPresenter("Sin conexión a internet")
                // Obtenemos todos las películas de room
                val movieAll: List<MovieEntity> = movieDao.getAll()
                mPresenter.showResultPresenter(movieAll, movieAll.size)
            }
        }
    }
}