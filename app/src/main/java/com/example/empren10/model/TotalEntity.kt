package com.example.empren10.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "total_table")
data class TotalEntity(
    @PrimaryKey
    var id: Int = 1,
    var ventasTotal: Int = 0,
    var gastosTotal: Int = 0,
    var utilidadTotal: Int = 0
): Serializable
