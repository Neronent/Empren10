package com.example.empren10.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.empren10.model.ContactoEntity

@Dao
interface ContactoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addContacto(contacto: ContactoEntity)

    @Update
    fun updateContacto(contacto: ContactoEntity)

    @Delete
    fun deleteContacto(contacto: ContactoEntity)

    @Query("DELETE FROM contacto_table")
    fun deleteAllContactos()

    @Query("SELECT * FROM contacto_table ORDER BY nombreContacto ASC")
    fun readAllContactos(): LiveData<List<ContactoEntity>>

}