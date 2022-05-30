package com.memije.examenandroid.ui.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.memije.examenandroid.databinding.FragmentUserBinding
import com.memije.examenandroid.room.entity.UserEntity
import com.memije.examenandroid.utils.AlertDialog

class UserFragment : Fragment(), UserMVP.View {

    private lateinit var _binding: FragmentUserBinding
    private lateinit var presenter: UserMVP.Presenter
    private lateinit var adapter: UserAdapter

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

        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.tvSectionTitle.text = "Personas"
        binding.tvSectionSubtitle.text = "Listado de usuarios registrados"

        // Creamos la instance del presenter
        presenter = UserPresenter(this)
        // Inicializamos el método que nos obtendrá los usuarios
        presenter.getDataUsersPresenter(root.context)

        return root
    }

    // Método que trae el resultado del response
    override fun showResultView(result: List<UserEntity?>?, size: Int) {
        activity?.runOnUiThread {

            // Ocultamos el progress y mostramos el RV
            binding.pbLoading.visibility = View.GONE
            binding.nsvUsers.visibility = View.VISIBLE

            Toast.makeText(activity, "Obtiene resultados", Toast.LENGTH_LONG).show()

            /* adapter = UserAdapter(result as List<UserEntity>)
            binding.rvUsers.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            binding.rvUsers.adapter = adapter */
        }
    }

    // Método que trae el error del response
    override fun showErrorView(result: String?) {

        // Ocultamos el progress y mostramos el RV
        binding.pbLoading.visibility = View.GONE
        binding.nsvUsers.visibility = View.VISIBLE

        activity?.runOnUiThread {
            alert.showDialog(activity, result.toString())
        }
    }
}