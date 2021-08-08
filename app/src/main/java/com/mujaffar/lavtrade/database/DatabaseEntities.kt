package com.mujaffar.medremind.database


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

import java.sql.Time
import java.util.*
import kotlin.collections.ArrayList


/*
* for save day by day schedule by date
* */
@Entity
data class DatabaseBuySellModel constructor(
     val shareName:String, val command:String,var isByOrSell:Boolean
)
{
    @PrimaryKey(autoGenerate = true)
    var id:Int=0

    var numberOfShare:Int=0
}


