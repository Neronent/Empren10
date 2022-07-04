package com.example.empren10.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.empren10.model.GastosEntity

@Dao
interface GastoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addGasto(gasto: GastosEntity)

    @Update
    fun updateGasto(gasto: GastosEntity)

    @Delete
    fun deleteGasto(gasto: GastosEntity)

    @Query("DELETE FROM gasto_table")
    fun deleteAllGastos()

    @Query("SELECT * FROM gasto_table ORDER BY nombreGastos ASC")
    fun readAllGastos(): LiveData<List<GastosEntity>>

    @Query("SELECT COUNT(*) AS tam FROM gasto_table")
    fun readTamGastos(): Int

}