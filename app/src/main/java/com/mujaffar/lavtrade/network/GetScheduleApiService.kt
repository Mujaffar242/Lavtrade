package com.mujaffar.currencyconverter.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mujaffar.lavtrade.network.NotificationRequestModel
import com.mujaffar.lavtrade.network.SendNotificationResponce
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlinx.coroutines.Deferred
import retrofit2.http.*

/*
* use retofit library  for networking request
* */

private const val BASE_URL = "https://fcm.googleapis.com/"

interface GetScheduleApiService {


    @Headers(
        "Content-Type: application/json",
        "Authorization: key=AAAAp3FVe5U:APA91bFiRE8EAl7mXHK1ujB1Rm3SfoNAKGqQBFMB60q-Az28OEos5AemC4ZdL4pM2Z2rfOrd5t2oOt1Rot1lSh0t1pT3Efi9aE95IxeZENkqVJphzZUQP6PXsT_4sUxa7TXNpDgUjUTV",
    )
    @POST("fcm/send")
    fun sendfcm(@Body notificationRequestModel: NotificationRequestModel)
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            :Deferred<SendNotificationResponce>
}

/*
* moshi library for remove callback methods of retrofit
* */
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()


object ScheduleApi {
    val retrofitService : GetScheduleApiService by lazy { retrofit.create(GetScheduleApiService::class.java) }
}



