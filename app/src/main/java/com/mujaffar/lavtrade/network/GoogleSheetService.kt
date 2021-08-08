package com.mujaffar.currencyconverter.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mujaffar.lavtrade.MainApplication
import com.mujaffar.lavtrade.admin_module.models.BuySellModel
import com.mujaffar.lavtrade.models.SheetPostModel
import com.mujaffar.lavtrade.models.SheetresponceModel
import com.mujaffar.lavtrade.network.SendNotificationResponce
import com.mujaffar.lavtrade.network.SheetDataResponceModel
import com.mujaffar.lavtrade.utils.Appconstants
import com.mujaffar.lavtrade.utils.SharedPrefHelper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


/*
* use retofit library  for networking request
* */

private const val BASE_URL = "https://sheets.googleapis.com/v4/spreadsheets/"

interface GoogleSheetService {


    @POST("{sheetId}/values/{sheetName}!{range}:append")
    fun postDataTOGoogleSheet(@Path("sheetId") sheetId:String,@Path("sheetName")sheetName:String,@Path("range")range:String,@Query("valueInputOption")valueInputOption:String,
                              @Body sheetPostModel: SheetPostModel
    )
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            :Deferred<SheetresponceModel>


    @GET("{sheetId}/values/{sheetName}!{range}")
    fun getGoogleSheetData(@Path("sheetId") sheetId:String,@Path("sheetName")sheetName:String,@Path("range")range:String,@Query("key")key:String
    )
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            :Deferred<SheetDataResponceModel>

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


val sharedPrefHelper: SharedPrefHelper = SharedPrefHelper(MainApplication.applicationContext())


var client = OkHttpClient.Builder().addInterceptor { chain ->
    val newRequest: Request = chain.request().newBuilder()
        .addHeader("Authorization", "Bearer ya29.a0ARrdaM8lXOE8jzu1znB1yAMa5Z86hEf2qTfEeQdeytXs_stH6tHoG93KVrRx_qXnUM8XkQiQbHFxk3mx77N8_ZzcrYSvlN5Y2iuKlUbBw48VFrkkH_jGzQ6aHHdSyg9ru5W8y37qNcWmGVJIEh9qqM1Gt2Mi")
        .build()
    chain.proceed(newRequest)
}.build()


private val retrofitWithAuth = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

object GoogleSheetApi {
    val retrofitService : GoogleSheetService by lazy { retrofitWithAuth.create(GoogleSheetService::class.java) }

    val retrofitServiceWithoutAuth : GoogleSheetService by lazy { retrofit.create(GoogleSheetService::class.java) }

}



