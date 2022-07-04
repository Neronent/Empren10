package com.example.empren10.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.empren10.data.EmprenDB
import com.example.empren10.data.respository.TotalRepository
import com.example.empren10.data.respository.VentaRepository
import com.example.empren10.model.TotalEntity
import com.example.empren10.model.VentasEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TotalViewModel(application: Application): AndroidViewModel(application) {

    val readTotal: LiveData<TotalEntity>
    private val repository: TotalRepository

    init {
        val totalDao = EmprenDB.getDatabase(application).totalDao()
        repository = TotalRepository(totalDao)
        readTotal = repository.readTotal
    }

    fun addTotal(total: TotalEntity)
    {
        viewModelScope.launch(Dispatchers.IO){
            repository.addTotal(total)
        }
    }
    fun updateTotal(total: TotalEntity) {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTotal(total) }
    }


}