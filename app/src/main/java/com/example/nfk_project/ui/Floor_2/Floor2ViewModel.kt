package com.example.nfk_project.ui.Floor_2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Floor2ViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is floor 2"
    }
    val text: LiveData<String> = _text
}