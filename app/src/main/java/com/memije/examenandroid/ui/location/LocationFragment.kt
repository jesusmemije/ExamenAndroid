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
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.memije.examenandroid.R
import com.memije.examenandroid.databinding.FragmentLocationBinding
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

        binding.tvSectionTitle.text = "Google Maps"
        binding.tvSectionSubtitle.text = "Marcadores de ubicaciones registradas"

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

                if (!result.isEmpty) {
                    val boundsBuilder = LatLngBounds.Builder()
                    for (document in result) {

                        Log.d("locations", document.data["direction"].toString())

                        val latLng = LatLng(document.data["latitude"].toString().toDouble(), document.data["longitude"].toString().toDouble())
                        boundsBuilder.include(latLng)
                        mMap.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(document.data["direction"].toString())
                                .snippet(document.data["latitude"].toString() + ", " + document.data["longitude"].toString())
                        )
                    }

                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 500, 500,0))

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