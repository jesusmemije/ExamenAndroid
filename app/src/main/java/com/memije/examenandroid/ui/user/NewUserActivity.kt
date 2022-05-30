package com.memije.examenandroid.ui.user

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.memije.examenandroid.databinding.ActivityNewUserBinding
import com.memije.examenandroid.utils.AlertDialog

class NewUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewUserBinding
    private lateinit var presenter: UserMVP.Presenter

    // Instancia de la clase alert
    private val alert = AlertDialog()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
        var message = "Email: " + binding.emailEditText.text
        message += "\nPhone: " + binding.phoneEditText.text
        message += "\nFullName: " + binding.fullNameEditText.text
        message += "\nAddress: " + binding.addressEditText.text

        Toast.makeText(this, "SUBMMIT: $message", Toast.LENGTH_LONG).show()

    }

    private fun invalidForm() {
        var message = ""
        if (binding.fullNameContainer.helperText != null)
            message += "\n\nNombre: " + binding.fullNameContainer.helperText
        if (binding.phoneContainer.helperText != null)
            message += "\n\nTeléfono: " + binding.phoneContainer.helperText
        if (binding.emailContainer.helperText != null)
            message += "\n\nCorreo: " + binding.emailContainer.helperText
        if (binding.addressContainer.helperText != null)
            message += "\n\nDirección: " + binding.addressContainer.helperText

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
            return "Deben de ser solo números"
        }
        if (phoneText.length != 10) {
            return "Sólo deben ser 10 dígitos"
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
            return "El correo tiene un formato inválido"
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
            return "La dirección es requerida"
        }
        return null
    }
}