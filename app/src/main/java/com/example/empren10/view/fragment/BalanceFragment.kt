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
import com.example.empren10.databinding.FragmentBalanceBinding
import com.example.empren10.model.ContactoEntity
import com.example.empren10.model.GastosEntity
import com.example.empren10.model.TotalEntity
import com.example.empren10.model.VentasEntity
import com.example.empren10.view.adapter.AdapterGastos
import com.example.empren10.view.adapter.AdapterVentas
import com.example.empren10.view.adapter.OnClickListener
import com.example.empren10.viewmodel.GastosViewModel
import com.example.empren10.viewmodel.TotalViewModel
import com.example.empren10.viewmodel.VentasViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BalanceFragment : Fragment(), OnClickListener {

    private lateinit var binding: FragmentBalanceBinding
    private lateinit var ventaAdapter: AdapterVentas
    private lateinit var gastoAdapter: AdapterGastos
    private lateinit var mVentasViewModel: VentasViewModel
    private lateinit var mGastosViewModel: GastosViewModel
    private lateinit var mTotalViewModel: TotalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBalanceBinding.inflate(inflater, container, false)

        val recyclerVentas = binding.rvVentas
        val linearManager = LinearLayoutManager(context)
        ventaAdapter = AdapterVentas(this)
        gastoAdapter = AdapterGastos(this)
        linearManager.orientation = LinearLayoutManager.VERTICAL
        recyclerVentas.layoutManager = linearManager

        binding.rvVentas.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ventaAdapter
        }

        binding.rvGastos.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = gastoAdapter
        }

        mVentasViewModel = ViewModelProvider(this).get(VentasViewModel::class.java)
        mVentasViewModel.readAllData.observe(viewLifecycleOwner, Observer { venta ->
            ventaAdapter.setData(venta)
        })
        mGastosViewModel = ViewModelProvider(this).get(GastosViewModel::class.java)
        mGastosViewModel.readAllData.observe(viewLifecycleOwner, Observer { gasto ->
            gastoAdapter.setData(gasto)
        })
        mTotalViewModel = ViewModelProvider(this).get(TotalViewModel::class.java)
        val total = TotalEntity(1,0,0,0)
        mTotalViewModel.addTotal(total)
        mTotalViewModel.readTotal.observe(viewLifecycleOwner, Observer { total ->
            binding.tvGastos.setText("C$ ${total.gastosTotal}")
            binding.tvVentas.setText("C$ ${total.ventasTotal}")
        })

        binding.btnNuevaVenta.setOnClickListener {
            val ventasEntity = VentasEntity(0,"",0,"","")
            val bundle = bundleOf("VentasEntity" to ventasEntity)
            NavHostFragment.findNavController(this).navigate(R.id.ventasFragment, bundle)
        }
        binding.btnNuevoGasto.setOnClickListener {
            val gastosEntity = GastosEntity(0,"",0,"","")
            val bundle = bundleOf("GastosEntity" to gastosEntity)
            NavHostFragment.findNavController(this).navigate(R.id.gastosFragment, bundle)
        }

        return binding.root
    }

    override fun onVentaClick(ventasEntity: VentasEntity) {
        val bundle = bundleOf("VentasEntity" to ventasEntity)

        NavHostFragment.findNavController(this).navigate(R.id.ventasFragment, bundle)
    }
    override fun onGastoClick(gastosEntity: GastosEntity) {
        val bundle = bundleOf("GastosEntity" to gastosEntity)

        NavHostFragment.findNavController(this).navigate(R.id.gastosFragment, bundle)
    }

    override fun onContactoClick(contactoEntity: ContactoEntity) {}

    override fun onDeleteVenta(ventasEntity: VentasEntity) {
        val items = resources.getStringArray(R.array.array_eliminar)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_options_title)
            .setItems(items) { _, i ->
                when (i) {
                    0 -> mVentasViewModel.deleteVenta(ventasEntity)

                    1 -> Toast.makeText(requireContext(),"Cancelado", Toast.LENGTH_SHORT).show()

                }
            }
            .show()
    }

    override fun onDeleteGasto(gastosEntity: GastosEntity) {
        val items = resources.getStringArray(R.array.array_eliminar)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_options_title)
            .setItems(items) { _, i ->
                when (i) {
                    0 -> mGastosViewModel.deleteGasto(gastosEntity)

                    1 -> Toast.makeText(requireContext(),"Cancelado", Toast.LENGTH_SHORT).show()

                }
            }
            .show()
    }

    override fun onDeleteContacto(contactoEntity: ContactoEntity) {}

}