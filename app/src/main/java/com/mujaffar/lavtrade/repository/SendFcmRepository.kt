package com.mujaffar.lavtrade.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.mujaffar.currencyconverter.network.ScheduleApi
import com.mujaffar.lavtrade.network.NotificationRequestModel
import com.mujaffar.lavtrade.utils.Appconstants
import com.mujaffar.lavtrade.utils.createDialogue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SendFcmRepository() {




    suspend fun sendFcm(notificationRequestModel: NotificationRequestModel) {
        withContext(Dispatchers.IO) {
          try {
              val response = ScheduleApi.retrofitService.sendfcm(notificationRequestModel).await()
          }
          catch (ex:Exception)
          {

          }
        }
    }


}