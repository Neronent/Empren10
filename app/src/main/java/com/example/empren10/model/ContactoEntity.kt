package com.example.empren10.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "contacto_table")
data class ContactoEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var nombreContacto:String = "",
    var telefonoContacto:String = "",
    var websiteContacto:String = "",
    var emailContacto:String = ""
):Serializable
