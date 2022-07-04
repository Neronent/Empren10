package com.example.empren10.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.empren10.data.EmprenDB
import com.example.empren10.data.respository.ContactoRepository
import com.example.empren10.model.ContactoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactosViewModel (application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<ContactoEntity>>
    private val repository: ContactoRepository

    init {
        val contactoDao = EmprenDB.getDatabase(application).contactoDao()
        repository = ContactoRepository(contactoDao)
        readAllData = repository.readAllData
    }

    fun addContacto(contacto: ContactoEntity)
    {
        viewModelScope.launch(Dispatchers.IO){
            repository.addContacto(contacto)
        }
    }
    fun updateContacto(contacto: ContactoEntity) {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateContacto(contacto) }
    }
    fun deleteContacto(contacto:ContactoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteContacto(contacto) }
    }
    fun deleteAllContactos(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllContactos() }
    }

}