package com.example.navigationandbottom.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobifillatask.R
import com.example.mobifillatask.databinding.FragmentScannerBinding

class ScannerFragment : Fragment() {

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onResume() {
        super.onResume()

        // Set the Toolbar title
        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.menu.setGroupVisible(R.id.toolbar, false)

        toolbar?.title = "Scanner"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
