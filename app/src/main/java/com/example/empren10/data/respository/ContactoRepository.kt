package com.example.empren10.data.respository

import androidx.lifecycle.LiveData
import com.example.empren10.data.dao.ContactoDao
import com.example.empren10.model.ContactoEntity


class ContactoRepository(private val contactoDao: ContactoDao) {

    val readAllData: LiveData<List<ContactoEntity>> = contactoDao.readAllContactos()

    fun addContacto(contacto:ContactoEntity)
    {  contactoDao.addContacto(contacto) }

    fun updateContacto(contacto:ContactoEntity){
        contactoDao.updateContacto(contacto)
    }

    fun deleteContacto(contacto:ContactoEntity){
        contactoDao.deleteContacto(contacto)
    }

    fun deleteAllContactos(){
        contactoDao.deleteAllContactos()
    }

}