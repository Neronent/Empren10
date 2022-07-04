package com.example.empren10.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.empren10.R
import com.example.empren10.databinding.GastosItemBinding
import com.example.empren10.model.GastosEntity

class AdapterGastos (private val listener: OnClickListener): RecyclerView.Adapter<AdapterGastos.ViewHolder>() {

    private var gastosList = emptyList<GastosEntity>()

    private lateinit var mContext: Context

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = GastosItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(parent.context).inflate(R.layout.gastos_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val gastos = gastosList[position]

        with(holder.binding){
            tvDetalleVentas.text = gastos.nombreGastos
            tvDinero.text = "C$ ${gastos.costoGastos}"
            metodoPago.text = gastos.pagoGastos
            root.setOnClickListener { listener.onGastoClick(gastos) }
            root.setOnLongClickListener {
                listener.onDeleteGasto(gastos)
                true
            }

        }
    }

    override fun getItemCount(): Int = gastosList.size

    fun setData(gasto:List<GastosEntity>){
        this.gastosList = gasto
        notifyDataSetChanged()
    }

}
