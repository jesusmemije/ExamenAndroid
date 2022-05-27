package com.memije.examenandroid.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.memije.examenandroid.databinding.FragmentMovieBinding

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // CODE

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}