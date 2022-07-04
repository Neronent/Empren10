package com.example.empren10.data.respository

import androidx.lifecycle.LiveData
import com.example.empren10.data.dao.VentaDao
import com.example.empren10.model.VentasEntity

class VentaRepository(private val ventaDao: VentaDao) {

    val readAllVentas: LiveData<List<VentasEntity>> =  ventaDao.readAllVentas()

    fun addVenta(venta:VentasEntity)
    {  ventaDao.addVenta(venta) }

    fun updateVenta(venta:VentasEntity){
        ventaDao.updateVenta(venta)
    }

    fun deleteVenta(venta:VentasEntity){
        ventaDao.deleteVenta(venta)
    }

    fun deleteAllVentas(){
        ventaDao.deleteAllVentas()
    }

}