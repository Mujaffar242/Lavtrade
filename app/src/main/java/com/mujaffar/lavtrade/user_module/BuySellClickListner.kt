package com.mujaffar.lavtrade.user_module

import com.mujaffar.medremind.database.DatabaseBuySellModel


/*
* interface for handle update contact events on contact list activity
* */
interface BuySellClickListner {
    fun onBuySellClick(databaseBuySellModel: DatabaseBuySellModel)
}