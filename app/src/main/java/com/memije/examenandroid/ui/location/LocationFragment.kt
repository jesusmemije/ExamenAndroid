package com.memije.examenandroid.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.memije.examenandroid.databinding.FragmentLocationBinding

class LocationFragment : Fragment() {

    private var _binding: FragmentLocationBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // CODE

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}