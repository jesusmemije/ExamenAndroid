package com.memije.examenandroid.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.memije.examenandroid.databinding.FragmentMovieBinding
import com.memije.examenandroid.room.entity.MovieEntity

class MovieFragment : Fragment(), MovieMVP.View {

    private lateinit var _binding: FragmentMovieBinding
    private lateinit var presenter: MovieMVP.Presenter
    private lateinit var adapter: MovieAdapter

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Creamos la instance del presenter
        presenter = MoviePresenter(this)
        // Inicializamos el método que nos obtendrá la Data de la API Movie
        presenter.getDataMoviesPresenter(root.context)

        return root
    }

    // Método que trae el resultado de la respuesta
    override fun showResultView(result: List<MovieEntity?>?, size: Int) {
        activity?.runOnUiThread {

            // Ocultamos el progress y mostramos el RV
            _binding.pbLoading.visibility = View.GONE
            _binding.containerMovies.visibility = View.VISIBLE

            adapter = MovieAdapter(result as List<MovieEntity>)
            _binding.rvMovies.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            _binding.rvMovies.adapter = adapter
        }
    }

    // Método que trae el error de la respuesta
    override fun showErrorView(result: String?) {
        activity?.runOnUiThread {
            Toast.makeText(activity, result.toString(), Toast.LENGTH_LONG).show()
        }
    }

}