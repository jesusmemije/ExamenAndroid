package com.memije.examenandroid.ui.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.memije.examenandroid.databinding.FragmentMovieBinding
import com.memije.examenandroid.room.entity.MovieEntity
import com.memije.examenandroid.utils.AlertDialog

class MovieFragment : Fragment(), MovieMVP.View {

    private lateinit var _binding: FragmentMovieBinding
    private lateinit var presenter: MovieMVP.Presenter
    private lateinit var adapter: MovieAdapter

    // Instancia de la clase alert
    private val alert = AlertDialog()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.tvSectionTitle.text = "Catálogo de películas"
        binding.tvSectionSubtitle.text = "Películas populares"

        // Creamos la instance del presenter
        presenter = MoviePresenter(this)
        // Inicializamos el método que nos obtendrá la Data de la API Movie
        presenter.getDataMoviesPresenter(root.context)

        return root
    }

    // Método que trae el resultado del response
    override fun showResultView(result: List<MovieEntity?>?, size: Int) {
        activity?.runOnUiThread {

            // Ocultamos el progress y mostramos el RV
            binding.pbLoading.visibility = View.GONE
            binding.nsvMovies.visibility = View.VISIBLE

            adapter = MovieAdapter(result as List<MovieEntity>)
            binding.rvMovies.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            binding.rvMovies.adapter = adapter
        }
    }

    // Método que trae el error del response
    override fun showErrorView(result: String?) {

        // Ocultamos el progress y mostramos el RV
        binding.pbLoading.visibility = View.GONE
        binding.nsvMovies.visibility = View.VISIBLE

        activity?.runOnUiThread {
            alert.showDialog(activity, result.toString())
        }
    }
}