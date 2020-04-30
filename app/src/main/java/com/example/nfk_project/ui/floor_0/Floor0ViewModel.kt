package com.example.nfk_project.ui.floor_0

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Floor0ViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is floor 0"
    }
    val text: LiveData<String> = _text
}