package com.example.empren10.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.empren10.R
import com.example.empren10.databinding.VentasItemBinding
import com.example.empren10.model.ContactoEntity
import com.example.empren10.model.GastosEntity
import com.example.empren10.model.VentasEntity

class AdapterVentas (private val listener: OnClickListener) : RecyclerView.Adapter<AdapterVentas.ViewHolder>() {

    private var ventaList = emptyList<VentasEntity>()

    private lateinit var mContext: Context

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = VentasItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(parent.context).inflate(R.layout.ventas_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ventas = ventaList[position]

        with(holder.binding){
            tvDetalleVentas.text = ventas.nombreVentas
            tvDinero.text = "C$ ${ventas.costoVentas}"
            metodoPago.text = ventas.pagoVentas

            root.setOnClickListener { listener.onVentaClick(ventas) }
            root.setOnLongClickListener {
                listener.onDeleteVenta(ventas)
                true
            }

        }

    }

    override fun getItemCount(): Int = ventaList.size

    fun setData(ventas:List<VentasEntity>){
        this.ventaList = ventas
        notifyDataSetChanged()
    }

}