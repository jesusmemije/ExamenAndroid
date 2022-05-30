package com.memije.examenandroid.ui.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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

        binding.fabAddUser.setOnClickListener {
            val intent = Intent(activity, NewUserActivity::class.java)
            activity?.startActivity(intent)
        }

        return root
    }

    // Método que trae el resultado del response
    override fun showResultListView(result: List<UserEntity?>?, size: Int) {
        activity?.runOnUiThread {

            // Ocultamos el progress y mostramos el RV
            binding.pbLoading.visibility = View.GONE
            binding.nsvUsers.visibility = View.VISIBLE

            adapter = UserAdapter(result as List<UserEntity>)
            binding.rvUsers.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            binding.rvUsers.adapter = adapter
        }
    }

    override fun showResultInsertView(result: String?) {
        TODO("Not yet implemented")
    }

    // Método que trae el error del response
    override fun showErrorView(result: String?) {
        activity?.runOnUiThread {
            // Ocultamos el progress y mostramos el RV
            binding.pbLoading.visibility = View.GONE
            binding.nsvUsers.visibility = View.VISIBLE
            // Show error
            alert.showDialog(activity, result.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        // Inicializamos el método que nos obtendrá los usuarios
        presenter.getDataUsersPresenter(activity)
    }
}