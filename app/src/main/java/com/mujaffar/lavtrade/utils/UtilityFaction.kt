package com.mujaffar.lavtrade.utils

import android.content.Context
import android.content.Intent
import com.mujaffar.lavtrade.login_module.ui.activities.LoginActivity
import java.text.SimpleDateFormat
import java.util.*

class UtilityFaction {


    /*
    * get current date for create and get node for save notification date
    * */
    companion object{
        fun getCurrentDateString():String{
            val c: Date = Calendar.getInstance().getTime()
            val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
            val formattedDate: String = df.format(c)

            return formattedDate
        }

        fun doLogout(context: Context)
        {
            val sharedPrefHelper=SharedPrefHelper(context)
            sharedPrefHelper.clearSharedPrefrence()

            context.startActivity(Intent(context,LoginActivity::class.java))

        }
    }
}