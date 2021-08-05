package com.mujaffar.lavtrade.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.mujaffar.currencyconverter.network.ScheduleApi
import com.mujaffar.lavtrade.network.NotificationRequestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SendFcmRepository {

    suspend fun sendFcm(notificationRequestModel: NotificationRequestModel) {
        withContext(Dispatchers.IO) {
            val response = ScheduleApi.retrofitService.sendfcm(notificationRequestModel).await()
            Log.e("","")
        }
    }


}