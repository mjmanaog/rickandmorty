package com.mjmanaog.rickandmorty.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mjmanaog.rickandmorty.helper.FailCallbackThrowable
import com.mjmanaog.rickandmorty.helper.SuccessCallback
import com.mjmanaog.rickandmorty.network.APIHandler
import com.mjmanaog.rickandmorty.network.APIManager
import com.mjmanaog.rickandmorty.network.model.Characters

/**
 * MainViewModel - responsible for all the data manipulation
 */
class MainViewModel : ViewModel() {

    var characterList: MutableLiveData<List<Characters.Character>> = MutableLiveData()

    /**
     * getCharacters method is used to call the API to get the character list
     * @param success - what should be done when the call was successful
     * @param fail - what should be done when the call was failed
     */
    fun getCharacters(
        success: SuccessCallback,
        fail: FailCallbackThrowable
    ) {
        val getCharacterList = APIManager().getCharacters().map {
            characterList.postValue(it.result)
        }
        APIHandler.observeAPI(getCharacterList, {
            success.invoke()
        }, {
            fail.invoke(it)
        })
    }
}