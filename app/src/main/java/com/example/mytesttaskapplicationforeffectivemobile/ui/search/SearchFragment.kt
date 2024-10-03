package com.example.mytesttaskapplicationforeffectivemobile.ui.search

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytesttaskapplicationforeffectivemobile.databinding.FragmentSearchBinding
import models.Offer
import models.OfferResponse
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

        checkView() // Проверка соединения при создании фрагмента
        var offersList: List<Offer>? = null // Объявляем переменную для хранения списка предложений

        // Наблюдение за предложениями
        viewModel.offers.observe(viewLifecycleOwner) { offers ->
            Log.i("Обновление", "Обновление данных")
            offersList = offers
        }

        viewModel.fetchOffers()

        // Наблюдение за вакансиями
        viewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            Log.i("Обновление", "Обновление данных")
            // Обновите UI, например, с помощью адаптера RecyclerView
            // adapter.submitList(vacancies)
        }
        viewModel.fetchVacancies()

        binding.ChechInternet.setOnClickListener {
            checkView()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } else {
            @Suppress("DEPRECATION") val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun checkView() {
        if (checkForInternet(requireContext())) {
            binding.ChechInternet.visibility = View.INVISIBLE
            binding.textSearch.visibility = View.INVISIBLE
            Toast.makeText(requireContext(), "Connected", Toast.LENGTH_SHORT).show()
        } else {
            binding.textSearch.visibility = View.VISIBLE
            binding.ChechInternet.visibility = View.VISIBLE
        }
    }
}
