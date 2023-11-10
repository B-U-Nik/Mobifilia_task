package com.example.mobifillatask.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.mobifillatask.R
import com.example.mobifillatask.databinding.FragmentVisitorsExitBinding

class VisitorsExitFragment : Fragment() {
    private var _binding: FragmentVisitorsExitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentVisitorsExitBinding.inflate(inflater, container, false)
        return binding.root

     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateToolbar()


    }
    override fun onResume() {
        super.onResume()


    }

    private fun updateToolbar() {

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.menu.setGroupVisible(R.id.toolbar, true)
        toolbar?.title = "Exit"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
