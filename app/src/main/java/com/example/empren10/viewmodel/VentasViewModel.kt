package com.example.empren10.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.empren10.data.EmprenDB
import com.example.empren10.data.respository.VentaRepository
import com.example.empren10.model.VentasEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VentasViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<VentasEntity>>
    private val repository: VentaRepository

    init {
        val ventaDao = EmprenDB.getDatabase(application).ventaDao()
        repository = VentaRepository(ventaDao)
        readAllData = repository.readAllVentas
    }

    fun addVenta(Venta: VentasEntity)
    {
        viewModelScope.launch(Dispatchers.IO){
            repository.addVenta(Venta)
        }
    }
    fun updateVenta(Venta: VentasEntity) {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateVenta(Venta) }
    }
    fun deleteVenta(Venta:VentasEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteVenta(Venta) }
    }
    fun deleteAllVenta(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllVentas() }
    }

}