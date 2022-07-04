package com.example.empren10.view.adapter

import com.example.empren10.model.ContactoEntity
import com.example.empren10.model.GastosEntity
import com.example.empren10.model.VentasEntity

interface OnClickListener {

    fun onVentaClick(ventasEntity: VentasEntity)
    fun onGastoClick(gastosEntity: GastosEntity)
    fun onContactoClick(contactoEntity: ContactoEntity)
    fun onDeleteVenta(ventasEntity: VentasEntity)
    fun onDeleteGasto(gastosEntity: GastosEntity)
    fun onDeleteContacto(contactoEntity: ContactoEntity)

}