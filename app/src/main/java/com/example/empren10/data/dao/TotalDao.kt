package com.example.empren10.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.empren10.model.TotalEntity

@Dao
interface TotalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTotal(total: TotalEntity)

    @Update
    fun updateTotal(total: TotalEntity)

    @Query("SELECT * FROM total_table WHERE id = 1")
    fun readTotal(): LiveData<TotalEntity>



//    @Query("SELECT gastosTotal FROM total_table WHERE id=1")
//    fun readGastoTotal(): LiveData<TotalEntity>

}