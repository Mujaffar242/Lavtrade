package com.mujaffar.lavtrade.user_module.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import androidx.room.Index
import com.mujaffar.currencyconverter.network.sharedPrefHelper
import com.mujaffar.lavtrade.repository.UserHomeRepository
import com.mujaffar.lavtrade.utils.Appconstants
import com.mujaffar.medremind.database.DatabaseBuySellModel
import com.mujaffar.medremind.database.getDatabase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserSummeryviewModel(application: Application) : AndroidViewModel(application)
{
    val database= getDatabase(application)

    val userHomeRepository=UserHomeRepository(database)

    //for hold list of user summery data return by repository
    var  summeryList=userHomeRepository.googleSheetDataResponceModel



    //for show loading spinner
    var showLoadingProgressBar=MutableLiveData<Boolean>()

    /*
    * get list of contacts and save into room databse when app load first time
    * */
    init {
        showLoadingProgressBar.value=false

        viewModelScope.launch {
            userHomeRepository.getDataFromGoogleSheet( sharedPrefHelper.getStringValueFromSharedPrefrences(Appconstants.USERNAME))
        }
    }








    /*
    * for show loading spinner
    * */
    fun showLoadingSpinner()
    {
        showLoadingProgressBar.value=true;
    }


    /*
   * for hide loading spinner
   * */
    fun hideLoadingSpinner()
    {
        showLoadingProgressBar.postValue(false)
    }

}