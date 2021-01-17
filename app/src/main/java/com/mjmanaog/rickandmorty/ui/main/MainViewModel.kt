package com.mjmanaog.rickandmorty.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mjmanaog.rickandmorty.helper.FailCallbackThrowable
import com.mjmanaog.rickandmorty.helper.SuccessCallback
import com.mjmanaog.rickandmorty.network.APIHandler
import com.mjmanaog.rickandmorty.network.APIManager
import com.mjmanaog.rickandmorty.network.model.Characters

class MainViewModel : ViewModel() {

    var isSuccessful: MutableLiveData<Boolean> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    var characterList: MutableLiveData<List<Characters.Character>> = MutableLiveData()

    fun getCharacters(
        success: SuccessCallback,
        fail: FailCallbackThrowable
    ) {
        isLoading.value = true
        val getCharacterList = APIManager().getCharacters().map {
            characterList.postValue(it.result)
        }
        APIHandler.observeAPI(getCharacterList, {
            isLoading.value = false
            isSuccessful.value = true
            success.invoke()
        }, {
            isLoading.value = false
            isSuccessful.value = false
            fail.invoke(it)
        })
    }
}