

package com.mujaffar.medremind.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/*
* room query for communicate with sqlite database
* */
@Dao
interface BuySellDao {

    @Query("select * from DatabaseBuySellModel")
    fun getAllBuySellListForDay():LiveData<List<DatabaseBuySellModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // fun insertAll(vararg task: DatabaseTaskModel)
    fun insertAll(list: List<DatabaseBuySellModel>)


    @Insert()
    fun insertSinglevalue(databaseBuySellModel: DatabaseBuySellModel)

    @Query("SELECT * FROM DatabaseBuySellModel WHERE id = :id")
    fun getSingleValue(id:String):DatabaseBuySellModel

    /*
   *for update database model single row
   * use for update task complete or not
    */
    @Update
    fun update(contact: DatabaseBuySellModel)


}

@Database(entities = [DatabaseBuySellModel::class], version = 1,exportSchema = false)
abstract class BuySellDatabase : RoomDatabase() {
    abstract val buySellDao: BuySellDao
}

private lateinit var INSTANCE: BuySellDatabase

fun getDatabase(context: Context): BuySellDatabase {
    synchronized(BuySellDatabase::class.java)
    {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                BuySellDatabase::class.java,
                "buycell"
            ).build()
        }
    }

    return INSTANCE
}