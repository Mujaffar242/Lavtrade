package com.mujaffar.lavtrade

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.api.services.sheets.v4.Sheets
import com.mujaffar.currencyconverter.network.GoogleSheetApi
import com.mujaffar.currencyconverter.network.GoogleSheetService
import com.mujaffar.lavtrade.admin_module.models.BuySellModel
import com.mujaffar.lavtrade.login_module.ui.activities.LoginActivity
import com.mujaffar.lavtrade.models.SheetPostModel
import com.mujaffar.lavtrade.utils.SpreadsheetSnippets

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)




        //hide the actionbar
        supportActionBar?.hide()

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.

    }
}