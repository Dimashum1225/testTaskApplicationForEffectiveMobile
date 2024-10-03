package com.example.mytesttaskapplicationforeffectivemobile.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytesttaskapplicationforeffectivemobile.RetrofitInstance
import kotlinx.coroutines.launch
import models.Offer
import models.OfferResponse
import models.Vacancy
import models.VacancyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> get() = _offers

    private val _vacancies = MutableLiveData<List<Vacancy>>()
    val vacancies: LiveData<List<Vacancy>> get() = _vacancies

    fun fetchOffers() {
        fetchData(
            call = RetrofitInstance.apiService.getOffers("1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r", "download"),
            onSuccess = { response -> _offers.value = response.offers }
        )
    }

    fun fetchVacancies() {
        fetchData(
            call = RetrofitInstance.apiService.getVacancies("1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r", "download"),
            onSuccess = { response -> _vacancies.value = response.vacancies }
        )
    }

    private fun <T> fetchData(call: Call<T>, onSuccess: (T) -> Unit) {
        viewModelScope.launch {
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        onSuccess(response.body()!!)
                        Log.d("API Response", "Response: ${response.body()}")
                    } else {
                        Log.e("API Error", "Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.e("API Failure", "Error: $t")
                }
            })
        }
    }
}
