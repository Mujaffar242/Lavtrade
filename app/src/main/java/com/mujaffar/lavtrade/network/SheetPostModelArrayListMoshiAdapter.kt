package com.mujaffar.lavtrade.network

import com.mujaffar.lavtrade.models.SheetPostModel
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class SheetPostModelArrayListMoshiAdapter {
    @ToJson
    fun arrayListToJson(list: ArrayList<SheetPostModel>): List<SheetPostModel> = list

    @FromJson
    fun arrayListFromJson(list: List<SheetPostModel>): ArrayList<SheetPostModel> = ArrayList(list)
}