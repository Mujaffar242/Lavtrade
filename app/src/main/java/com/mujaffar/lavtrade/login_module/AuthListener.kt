package com.mujaffar.lavtrade.login_module

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}