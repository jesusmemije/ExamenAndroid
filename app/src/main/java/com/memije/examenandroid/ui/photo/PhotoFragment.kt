package com.memije.examenandroid.ui.photo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.memije.examenandroid.databinding.FragmentPhotoBinding
import com.memije.examenandroid.utils.AlertDialog
import java.util.HashMap

class PhotoFragment : Fragment() {

    private lateinit var _binding: FragmentPhotoBinding

    private val database = Firebase.database
    private val reference = database.getReference("images")

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

        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.tvSectionTitle.text = "Subir imágenes"
        binding.tvSectionSubtitle.text = "Da click en el recuadro de abajo y sube tus fotos"

        // Acción click para abrir galería y posteriormente subir imagen
        binding.ivUplaod.setOnClickListener {
            fileManager()
        }

        return root
    }

    private fun fileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "*/*"

        resultLauncher.launch(intent)

    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data

                val clipData = data?.clipData

                // Show loading - upload files

                if (clipData != null) {
                    // Bucle que manda a subir las imagenes selecionadas
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        uri?.let { fileUpload(it) }
                    }
                    // Notificar al usuario - success
                    alert.showDialog(activity, "Los archivos se han subido con éxito")
                } else {
                    val uri = data?.data
                    uri?.let { fileUpload(it) }
                }

                // Esconder loading

            }
        }

    private fun fileUpload(mUri: Uri) {

        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("Images")
        val path = mUri.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/') + 1))

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->
                val hashMap = HashMap<String, String>()
                hashMap["link"] = java.lang.String.valueOf(uri)

                reference.child(reference.push().key.toString()).setValue(hashMap)

                Log.i("message", "File upload successfully")
            }

        }.addOnFailureListener {
            Log.i("message", "File upload error")
            alert.showDialog(activity, "Estamos experimentando fallas al subir archivos, intente más tarde por favor")
        }
    }

}