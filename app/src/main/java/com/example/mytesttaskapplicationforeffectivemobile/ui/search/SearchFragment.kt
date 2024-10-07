package com.example.mytesttaskapplicationforeffectivemobile.ui.search

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
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
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytesttaskapplicationforeffectivemobile.OffersAdapter
import com.example.mytesttaskapplicationforeffectivemobile.Vacancy_Adapter
import com.example.mytesttaskapplicationforeffectivemobile.databinding.FragmentSearchBinding
import models.Offer
import models.OfferResponse
import models.Vacancy

class SearchFragment : Fragment() {

    private lateinit var vacancyAdapter: Vacancy_Adapter
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

        // Наблюдение за предложениями
        viewModel.offers.observe(viewLifecycleOwner) { offers ->
            Log.i("Обновление", "Обновление данных")
            upgradeOffersWindow(offers)
        }

        viewModel.fetchOffers()

        // Наблюдение за вакансиями
        viewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            Log.i("Обновление", "Обновление данных")
            upgradeVacanciesWindow(vacancies)
        }
        viewModel.fetchVacancies()

        binding.ChechInternet.setOnClickListener {
            checkView()
        }

        binding.showallvacancies.setOnClickListener{
            vacancyAdapter.showAllItems()
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
    private fun upgradeOffersWindow(offers: List<Offer>) {
        val recyclerView = binding.offersRecycler
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = OffersAdapter(offers){link->
            openUrl(link)
        }
    }
    private fun openUrl(url:String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
    private fun upgradeVacanciesWindow(vacancies:List<Vacancy>){
        val recyclerView = binding.vacancies
        recyclerView.adapter = Vacancy_Adapter(vacancies)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

}
