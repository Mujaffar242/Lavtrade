package com.mujaffar.lavtrade.utils


/*
* for hold all contants realted to app
* */
class Appconstants {

    companion object{
        val sharedPrefFile:String="LavTradeSharedPrefrences";

        val IS_LOGIN:String="islogin"

        val USERNAME="username"

        val AUTHTOKEN="authtoken"
    }


    /*
    * for hold type of dialouge
    * */
    class DialogueType{
        companion object{
            val BUY_DIALOUGE=1
            val SELL_DIALOUGE=2
            val BUY_CONFIRM=3
            val SELL_CONFIRM=4
            val LOG_OUT=5
            val SEND_NOTIFICATION=6
            val CONFRIM_NOTIFICATION=7
        }
    }
}