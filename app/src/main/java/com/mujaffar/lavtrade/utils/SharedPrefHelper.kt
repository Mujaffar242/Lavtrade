package com.mujaffar.lavtrade.utils

import android.content.Context
import android.content.SharedPreferences
import com.mujaffar.lavtrade.utils.Appconstants.Companion.sharedPrefFile

class SharedPrefHelper(context: Context) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun insertStringToSharedPrefrences(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }


    fun insertBooleanToSharedPrefrences(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }


    fun getStringValueFromSharedPrefrences(key: String): String {
        return sharedPreferences.getString(key.toString(), "").toString()
    }

    fun getBooleanValuesFromSharedPrefrences(key: String): Boolean {
        return sharedPreferences.getBoolean(key.toString(),false)
    }

    fun clearSharedPrefrence()
    {
        editor.clear()
        editor.commit()
    }
}