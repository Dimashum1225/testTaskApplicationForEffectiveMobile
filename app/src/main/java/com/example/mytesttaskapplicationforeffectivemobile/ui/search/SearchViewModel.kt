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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>>
        get() = _offers

    fun fetchOffers() {
        viewModelScope.launch {
            try {
                // Выполнение запроса через ApiService
                val call = RetrofitInstance.apiService.getOffers("1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r", "download")
                call.enqueue(object : Callback<OfferResponse> {
                    override fun onResponse(call: Call<OfferResponse>, response: Response<OfferResponse>) {
                        if (response.isSuccessful) {
                            // Успешный ответ, обновляем LiveData
                            _offers.value = response.body()?.offers
                            Log.d("API Response", "Response: ${response.body()?.offers}")
                        } else {
                            Log.e("API Error", "Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<OfferResponse>, t: Throwable) {
                        Log.e("API Failure", "Error: $t")
                    }
                })
            } catch (e: Exception) {
                Log.e("Server question", "Ошибка выполнения запроса - $e")
            }
        }
    }
}
