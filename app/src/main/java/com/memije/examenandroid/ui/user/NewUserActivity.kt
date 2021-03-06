package com.memije.examenandroid.ui.user

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.memije.examenandroid.databinding.ActivityNewUserBinding
import com.memije.examenandroid.room.entity.UserEntity
import com.memije.examenandroid.utils.AlertDialog

class NewUserActivity : AppCompatActivity(), UserMVP.View {

    private lateinit var binding: ActivityNewUserBinding
    private lateinit var presenter: UserMVP.Presenter

    // Instancia de la clase alert
    private val alert = AlertDialog()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Creamos la instance del presenter
        presenter = UserPresenter(this)

        binding.tvSectionTitle.text = "Nuevo usuario"

        fullNameFocusListener()
        phoneFocusListener()
        emailFocusListener()
        addressFocusListener()

        binding.submitButton.setOnClickListener { submitForm() }
        binding.closeButton.setOnClickListener{
            finish()
        }
    }

    private fun submitForm() {
        binding.fullNameContainer.helperText = validFullName()
        binding.phoneContainer.helperText = validPhone()
        binding.emailContainer.helperText = validEmail()
        binding.addressContainer.helperText = validAddress()

        val validFullName = binding.fullNameContainer.helperText == null
        val validPhone = binding.phoneContainer.helperText == null
        val validEmail = binding.emailContainer.helperText == null
        val validAddress = binding.addressContainer.helperText == null

        if (validFullName && validPhone && validEmail && validAddress)
            saveData()
        else
            invalidForm()
    }

    private fun saveData() {
        val name = binding.fullNameEditText.text.toString()
        val phone = binding.phoneEditText.text.toString().toLong()
        val email: String = binding.emailEditText.text.toString()
        val address = binding.addressEditText.text.toString()

        val user = UserEntity(0, name, phone, email, address)

        // Inicializamos el m??todo que nos obtendr?? los usuarios
        presenter.setInsertUserPresenter(this, user)
    }

    private fun invalidForm() {
        var message = ""
        if (binding.fullNameContainer.helperText != null)
            message += "\n\nNombre: " + binding.fullNameContainer.helperText
        if (binding.phoneContainer.helperText != null)
            message += "\n\nTel??fono: " + binding.phoneContainer.helperText
        if (binding.emailContainer.helperText != null)
            message += "\n\nCorreo: " + binding.emailContainer.helperText
        if (binding.addressContainer.helperText != null)
            message += "\n\nDirecci??n: " + binding.addressContainer.helperText

        // Notificar al usuario
        alert.showDialog(this, message)
    }

    private fun fullNameFocusListener() {
        binding.fullNameEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.fullNameContainer.helperText = validFullName()
            }
        }
    }

    private fun validFullName(): String? {
        val fullNameText = binding.fullNameEditText.text.toString()
        if (fullNameText.isEmpty()) {
            return "El nombre completo es requerido"
        }
        return null
    }

    private fun phoneFocusListener() {
        binding.phoneEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.phoneContainer.helperText = validPhone()
            }
        }
    }

    private fun validPhone(): String? {
        val phoneText = binding.phoneEditText.text.toString()
        if (!phoneText.matches(".*[0-9].*".toRegex())) {
            return "Deben de ser solo n??meros"
        }
        if (phoneText.length != 10) {
            return "S??lo deben ser 10 d??gitos"
        }
        return null
    }

    private fun emailFocusListener() {
        binding.emailEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.emailContainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.emailEditText.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "El correo tiene un formato inv??lido"
        }
        return null
    }

    private fun addressFocusListener() {
        binding.addressEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.addressContainer.helperText = validAddress()
            }
        }
    }

    private fun validAddress(): String? {
        val addressText = binding.addressEditText.text.toString()
        if (addressText.isEmpty()) {
            return "La direcci??n es requerida"
        }
        return null
    }

    override fun showResultListView(result: List<UserEntity?>?, size: Int) {
        TODO("Not yet implemented")
    }

    override fun showResultInsertView(result: String?) {
        this.runOnUiThread {
            alert.showDialog(this, result.toString())
        }
    }

    override fun showErrorView(result: String?) {
        this.runOnUiThread {
            alert.showDialog(this, result.toString())
        }
    }
}