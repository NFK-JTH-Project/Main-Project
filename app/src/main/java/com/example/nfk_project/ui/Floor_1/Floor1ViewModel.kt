package com.example.nfk_project.ui.Floor_1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Floor1ViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is floor 1"
    }
    val text: LiveData<String> = _text
}