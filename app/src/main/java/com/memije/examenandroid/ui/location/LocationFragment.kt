package com.memije.examenandroid.ui.location

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.memije.examenandroid.R
import com.memije.examenandroid.databinding.FragmentLocationBinding
import com.memije.examenandroid.databinding.FragmentMovieBinding
import com.memije.examenandroid.utils.AlertDialog

@Suppress("SpellCheckingInspection")
class LocationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var _binding: FragmentLocationBinding

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding

    private lateinit var mMap: GoogleMap

    // Instancia de la clase alert
    private val alert = AlertDialog()

    // Instancia de Firestore
    private val db = FirebaseFirestore.getInstance()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding.tvSectionTitle.text = "Google Maps"
        _binding.tvSectionSubtitle.text = "Marcadores de ubicaciones registradas"

        // Enlazamos el componente del Fragment que contiene el mapa
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Control del mapa
        mMap = googleMap

        // Leer y mostrar las coodenadas guardadas en FireStore
        db.collection("locations")
            .get()
            .addOnSuccessListener { result ->
                lateinit var coordinatesZoom: LatLng
                if (!result.isEmpty) {
                    for (document in result) {
                        mMap.addMarker(
                            MarkerOptions()
                                .position(
                                    LatLng(
                                        document.data["latitude"].toString().toDouble(),
                                        document.data["longitude"].toString().toDouble()
                                    )
                                )
                                .title(document.data["direction"].toString())
                        )

                        // Atrapa última coordenada para el animatedCamera con Zoom
                        coordinatesZoom = LatLng(
                            document.data["latitude"].toString().toDouble(),
                            document.data["longitude"].toString().toDouble()
                        )
                    }

                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(coordinatesZoom, 18f),
                        4000,
                        null
                    )

                } else {
                    alert.showDialog(activity, "Aún no se han registrado ubicaciones en el servidor")
                }

            }
            .addOnFailureListener { exception ->
                // Error
                Log.d("getData", "Error getting documents: ", exception)
                alert.showDialog(activity, "Estamos experimentando problemas con el servidor, intente mas tarde por favor")
            }
    }

}