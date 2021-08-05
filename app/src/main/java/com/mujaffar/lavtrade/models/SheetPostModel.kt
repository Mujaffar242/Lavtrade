package com.mujaffar.lavtrade.models

import com.mujaffar.lavtrade.admin_module.models.BuySellModel

data class SheetPostModel(val range:String,val majorDimension:String,
                          val values:List<List<String>>
                          )