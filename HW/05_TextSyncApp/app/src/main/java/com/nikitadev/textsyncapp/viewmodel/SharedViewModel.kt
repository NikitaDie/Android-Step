package com.nikitadev.textsyncapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _text = MutableLiveData("Ще немає тексту")
    val text: LiveData<String> = _text

    fun updateText(newText: String) {
        _text.value = newText
    }
}