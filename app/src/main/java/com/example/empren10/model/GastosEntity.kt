package com.example.empren10.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "gasto_table")
data class GastosEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var nombreGastos: String = "",
    var costoGastos: Int = 0,
    var fechaGastos: String = "",
    var pagoGastos: String = ""
): Serializable
