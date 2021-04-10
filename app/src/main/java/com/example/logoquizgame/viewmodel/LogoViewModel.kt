package com.example.logoquizgame.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.logoquizgame.utils.Utility.readXMLinString
import com.example.logoquizgame.model.LogoData
import com.google.gson.GsonBuilder


class LogoListViewModel(application: Application) : AndroidViewModel(application) {

    var logoDetails: MutableLiveData<Array<LogoData>> = MutableLiveData()
    var progressBarVisibility: MutableLiveData<Boolean> = MutableLiveData()
    var seconds = 0

    init {
        // Calling the fetchLogoDetails
        fetchLogoDetails()
    }

    /**
     * Method to fetch LogoDetails from the Mock Text FILE
     */
    private fun fetchLogoDetails() {
        progressBarVisibility.value = true
        val json = readXMLinString(
            "logo.txt",
            getApplication()
        )

        val gson = GsonBuilder().create()
        val testCase: Array<LogoData> =
            gson.fromJson(json, Array<LogoData>::class.java)
        logoDetails.postValue(testCase)
        progressBarVisibility.value = false

    }
}