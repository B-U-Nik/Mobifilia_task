package com.example.mobifillatask.fragments

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobifillatask.R
import com.example.mobifillatask.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private var binding = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolBarSetUpForOnResume()
        setupBackButtonPressHandling()

    }

    private fun setupBackButtonPressHandling() {
        // Handle the back button press
        binding?.root?.isFocusableInTouchMode = true
        binding?.root?.requestFocus()
        binding?.root?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                requireActivity().finish() // Close the application
                true
            } else {
                false
            }
        }

    }

    private fun toolBarSetUpForOnResume() {
        val toolbar =
            requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.menu.setGroupVisible(R.id.toolbar, false)
        toolbar?.title = "Dashboard"
    }



    override fun onDestroyView() {
        super.onDestroyView()
        // Clear ViewBinding reference to avoid memory leaks
        _binding = null
    }
}