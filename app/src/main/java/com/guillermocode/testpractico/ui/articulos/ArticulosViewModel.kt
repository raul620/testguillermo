package com.guillermocode.testpractico.ui.articulos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticulosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Este es Articulos Fragment"
    }
    val text: LiveData<String> = _text
}