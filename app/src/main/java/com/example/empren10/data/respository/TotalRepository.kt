package com.example.empren10.data.respository

import androidx.lifecycle.LiveData
import com.example.empren10.data.dao.TotalDao
import com.example.empren10.model.TotalEntity

class TotalRepository(private val totalDao: TotalDao) {

    val readTotal: LiveData<TotalEntity> =  totalDao.readTotal()

    fun addTotal(total: TotalEntity)
    {  totalDao.addTotal(total) }

    fun updateTotal(total: TotalEntity){
        totalDao.updateTotal(total)
    }

}