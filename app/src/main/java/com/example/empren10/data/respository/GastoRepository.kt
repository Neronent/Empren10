package com.example.empren10.data.respository

import androidx.lifecycle.LiveData
import com.example.empren10.data.dao.GastoDao
import com.example.empren10.model.GastosEntity


class GastoRepository(private val gastoDao: GastoDao) {

    val readAllGastos: LiveData<List<GastosEntity>> = gastoDao.readAllGastos()

    fun addGasto(gasto:GastosEntity)
    {  gastoDao.addGasto(gasto) }

    fun updateGasto(gasto:GastosEntity){
        gastoDao.updateGasto(gasto)
    }

    fun deleteGasto(gasto:GastosEntity){
        gastoDao.deleteGasto(gasto)
    }
    fun readTamGastos(){
        gastoDao.readTamGastos()
    }

    fun deleteAllGastos(){
        gastoDao.deleteAllGastos()
    }

}