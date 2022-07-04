package com.example.empren10.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "ventas_table")
data class VentasEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var nombreVentas: String = "",
    var costoVentas: Int = 0,
    var fechaVentas: String = "",
    var pagoVentas: String = ""
): Serializable
