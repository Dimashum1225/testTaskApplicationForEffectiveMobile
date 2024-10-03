package com.example.mytesttaskapplicationforeffectivemobile.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytesttaskapplicationforeffectivemobile.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.offers.observe(viewLifecycleOwner, Observer {offers ->
            Log.i("Обновление","Обновление данных")
        })
        viewModel.fetchOffers()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}