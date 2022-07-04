package com.example.empren10.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.empren10.data.EmprenDB
import com.example.empren10.data.respository.GastoRepository
import com.example.empren10.model.GastosEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GastosViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<GastosEntity>>
    private val repository: GastoRepository

    init {
        val gastoDao = EmprenDB.getDatabase(application).gastoDao()
        repository = GastoRepository(gastoDao)
        readAllData = repository.readAllGastos
    }

    fun addGasto(gasto: GastosEntity)
    {
        viewModelScope.launch(Dispatchers.IO){
            repository.addGasto(gasto)
        }
    }
    fun updateGasto(gasto: GastosEntity) {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateGasto(gasto) }
    }
    fun deleteGasto(gasto:GastosEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGasto(gasto) }
    }
    fun readTamGastos(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.readTamGastos()
        }
    }
    fun deleteAllGasto(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllGastos() }
    }

}