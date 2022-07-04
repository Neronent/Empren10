package com.example.empren10.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.empren10.R
import com.example.empren10.databinding.ItemContactosBinding
import com.example.empren10.model.ContactoEntity

class AdapterContactos(private val listener: OnClickListener) : RecyclerView.Adapter<AdapterContactos.ViewHolder>()  {

    private var contactosList = emptyList<ContactoEntity>()

    private lateinit var mContext: Context

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = ItemContactosBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contactos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contacto = contactosList[position]

        with(holder.binding){
            tvNombreContacto.text = contacto.nombreContacto
            tvTelefonoContacto.text = contacto.telefonoContacto
            root.setOnClickListener { listener.onContactoClick(contacto) }
            root.setOnLongClickListener {
                listener.onDeleteContacto(contacto)
                true
            }
        }

    }

    override fun getItemCount(): Int = contactosList.size

    fun setData(contacto:List<ContactoEntity>){
        this.contactosList = contacto
        notifyDataSetChanged()
    }

}