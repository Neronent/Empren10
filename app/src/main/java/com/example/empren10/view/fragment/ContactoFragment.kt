package com.example.empren10.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.empren10.R
import com.example.empren10.databinding.FragmentContactoBinding
import com.example.empren10.model.ContactoEntity
import com.example.empren10.model.GastosEntity
import com.example.empren10.model.VentasEntity
import com.example.empren10.view.adapter.AdapterContactos
import com.example.empren10.view.adapter.OnClickListener
import com.example.empren10.viewmodel.ContactosViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ContactoFragment : Fragment(), OnClickListener {

    private lateinit var binding: FragmentContactoBinding
    private lateinit var contactosAdapter: AdapterContactos
    private lateinit var mContactoViewModel: ContactosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactoBinding.inflate(inflater, container, false)

        val recyclerAyuda = binding.rvContactos
        val linearManager = LinearLayoutManager(context)
        contactosAdapter = AdapterContactos(this)
        linearManager.orientation = LinearLayoutManager.VERTICAL
        recyclerAyuda.layoutManager = linearManager

        binding.rvContactos.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = contactosAdapter
        }

        mContactoViewModel = ViewModelProvider(this).get(ContactosViewModel::class.java)
        mContactoViewModel.readAllData.observe(viewLifecycleOwner, Observer { contacto ->
            contactosAdapter.setData(contacto)
        })


        binding.btnAgregarContacto.setOnClickListener {
            val contactoEntity = ContactoEntity(0,"","","","")
            val bundle = bundleOf("ContactoEntity" to contactoEntity)
            NavHostFragment.findNavController(this).navigate(R.id.contactoAddFragment, bundle)
        }

        return binding.root
    }

    override fun onVentaClick(ventasEntity: VentasEntity) {}
    override fun onGastoClick(gastosEntity: GastosEntity) {}
    override fun onContactoClick(contactoEntity: ContactoEntity) {

        val bundle = bundleOf("ContactoEntity" to contactoEntity)

        NavHostFragment.findNavController(this).navigate(R.id.contactoAddFragment, bundle)

    }
    override fun onDeleteVenta(ventasEntity: VentasEntity) {}
    override fun onDeleteGasto(gastosEntity: GastosEntity) {}
    override fun onDeleteContacto(contactoEntity: ContactoEntity) {
        val items = resources.getStringArray(R.array.array_eliminar_contacto)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_options_title)
            .setItems(items) { _, i ->
                when (i) {
                    0 -> mContactoViewModel.deleteContacto(contactoEntity)

                    1 -> Toast.makeText(requireContext(),"Cancelado", Toast.LENGTH_SHORT).show()

                }
            }
            .show()
    }

}