package com.mujaffar.lavtrade

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.sheets.v4.Sheets
import com.mujaffar.currencyconverter.network.GoogleSheetApi
import com.mujaffar.currencyconverter.network.GoogleSheetService
import com.mujaffar.lavtrade.admin_module.models.BuySellModel
import com.mujaffar.lavtrade.login_module.ui.activities.LoginActivity
import com.mujaffar.lavtrade.models.SheetPostModel
import com.mujaffar.lavtrade.utils.Appconstants
import com.mujaffar.lavtrade.utils.SharedPrefHelper
import com.mujaffar.lavtrade.utils.SpreadsheetSnippets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class SplashActivity : AppCompatActivity() {

    lateinit var sharedPreferences:SharedPrefHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


       sharedPreferences =SharedPrefHelper(this)


        //hide the actionbarz
        supportActionBar?.hide()

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.

       // authExplicit()

    }



    fun authExplicit() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val credentials =
                    GoogleCredential.fromStream(resources.assets.open("mujaffar-4d858-e3add9560dd6.json"))
                        .createScoped(Collections.singletonList("https://www.googleapis.com/auth/spreadsheets"))
              //  credentials.refreshIfExpired()
                credentials.refreshToken()
                val accessToken = credentials.accessToken
                Log.e("log_data", "Token :" + accessToken)
                if (accessToken.isNotEmpty()) {
                    Log.e(
                        "log_data",
                        "Got it --> authenticationType :"
                    )
                   // AuthToken = "Bearer " + accessToken.tokenValue
                    sharedPreferences.insertStringToSharedPrefrences(Appconstants.AUTHTOKEN,accessToken)


                } else {
                    Log.e("log_data", "No token")
                   // AuthToken = ""
                }
                CoroutineScope(Dispatchers.Main).launch {
                   // initViews()
                }
            } catch (e: Exception) {
                Log.e("log_data", e.toString())
            }
        }
    }

}