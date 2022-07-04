package com.example.empren10.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.empren10.model.VentasEntity

@Dao
interface VentaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addVenta(ventas: VentasEntity)

    @Update
    fun updateVenta(ventas: VentasEntity)

    @Delete
    fun deleteVenta(ventas: VentasEntity)

    @Query("DELETE FROM ventas_table")
    fun deleteAllVentas()

    @Query("SELECT * FROM ventas_table ORDER BY nombreVentas ASC")
    fun readAllVentas(): LiveData<List<VentasEntity>>

}