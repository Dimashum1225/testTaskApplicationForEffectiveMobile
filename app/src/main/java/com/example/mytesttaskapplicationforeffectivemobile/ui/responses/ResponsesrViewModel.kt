package com.example.mytesttaskapplicationforeffectivemobile.ui.responses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResponsesrViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is responses Fragment"
    }
    val text: LiveData<String> = _text
}