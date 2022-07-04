package com.example.empren10.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.empren10.data.dao.ContactoDao
import com.example.empren10.data.dao.GastoDao
import com.example.empren10.data.dao.TotalDao
import com.example.empren10.data.dao.VentaDao
import com.example.empren10.model.ContactoEntity
import com.example.empren10.model.GastosEntity
import com.example.empren10.model.TotalEntity
import com.example.empren10.model.VentasEntity

@Database(entities = [ContactoEntity::class, VentasEntity::class, GastosEntity::class, TotalEntity::class], version = 1, exportSchema = false)
abstract class EmprenDB: RoomDatabase() {

    abstract fun contactoDao(): ContactoDao
    abstract fun ventaDao(): VentaDao
    abstract fun gastoDao(): GastoDao
    abstract fun totalDao(): TotalDao

    companion object {
        @Volatile
        private var INSTANCE: EmprenDB? = null
        fun getDatabase (context: Context): EmprenDB {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmprenDB::class.java,
                    "empren_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}