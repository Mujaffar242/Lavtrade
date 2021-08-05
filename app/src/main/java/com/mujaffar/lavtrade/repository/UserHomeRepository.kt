package com.mujaffar.lavtrade.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import com.mujaffar.currencyconverter.network.GoogleSheetApi
import com.mujaffar.lavtrade.MainApplication
import com.mujaffar.lavtrade.admin_module.models.BuySellModel
import com.mujaffar.lavtrade.models.FirebaseDataModel
import com.mujaffar.lavtrade.models.SheetPostModel
import com.mujaffar.lavtrade.network.SheetsExample
import com.mujaffar.lavtrade.utils.SpreadsheetSnippets
import com.mujaffar.lavtrade.utils.UtilityFaction
import com.mujaffar.medremind.database.BuySellDatabase
import com.mujaffar.medremind.database.DatabaseBuySellModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserHomeRepository(val database: BuySellDatabase) {


    /*
    * get all the buy sell data from firebase realtime database
    * and insert it to room database
    * */

    val firebaseDatabase = FirebaseDatabase.getInstance()
    var ref: DatabaseReference = firebaseDatabase.getReference("notifications/"+UtilityFaction.getCurrentDateString())



    /*
* get list of all buy sell that are store in database
*  */
    val allBuySellForToday: LiveData<List<DatabaseBuySellModel>> = database.buySellDao.getAllBuySellListForDay()


    /*
* function for update task complete status
* */
    suspend fun updateCompleteStatus(databaseBuySellModel: DatabaseBuySellModel)
    {
       /* withContext(Dispatchers.IO){
            databaseBuySellModel.isByOrSell = true
            database.buySellDao.update(databaseBuySellModel)
        }*/


        withContext(Dispatchers.IO)
        {
            val arraylist=ArrayList<ArrayList<String>>()

            val buysellarry=ArrayList<String>()

            buysellarry.add("Mujaffar")
            buysellarry.add("12")

            arraylist.add(buysellarry)

           val sheetPostModel= SheetPostModel("A1:B1","ROWS",arraylist)


          GoogleSheetApi.retrofitService.postDataTOGoogleSheet("15B-ce4CC7OBqieMwH4TLBzUzMHHC4qzt2UEfbCfaPnM","Sheet1","A1:B1","USER_ENTERED",sheetPostModel).await()


            /*val sheeetSnap= SpreadsheetSnippets(SheetsExample.createSheetsService(MainApplication.applicationContext()))

          val res = sheeetSnap.appendValues("15B-ce4CC7OBqieMwH4TLBzUzMHHC4qzt2UEfbCfaPnM","Sheet1!A1B1","USER_ENTERED",
                arraylist as List<MutableList<Any>>?
            )*/


          // Log.e("",res.toString())
        }


    }




}

