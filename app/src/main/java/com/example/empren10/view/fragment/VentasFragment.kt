package com.example.empren10.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.empren10.R
import com.example.empren10.databinding.FragmentGastosBinding
import com.example.empren10.databinding.FragmentVentasBinding
import com.example.empren10.model.ContactoEntity
import com.example.empren10.model.GastosEntity
import com.example.empren10.model.TotalEntity
import com.example.empren10.model.VentasEntity
import com.example.empren10.view.adapter.OnClickListener
import com.example.empren10.viewmodel.TotalViewModel
import com.example.empren10.viewmodel.VentasViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class VentasFragment : Fragment() {

    private lateinit var binding: FragmentVentasBinding
    private lateinit var mVentasViewModel: VentasViewModel
    private lateinit var mTotalViewModel: TotalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVentasBinding.inflate(inflater, container, false)
        val metodo_pago = resources.getStringArray(R.array.metodo_pago)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,metodo_pago)
        binding.autoComplete.setAdapter(arrayAdapter)
        mVentasViewModel = ViewModelProvider(this).get(VentasViewModel::class.java)
        mTotalViewModel = ViewModelProvider(this).get(TotalViewModel::class.java)

        binding.etFechaVenta.setOnClickListener { showDatePickerDialog() }

        try {
            if(arguments != null){
                val venta = arguments?.getSerializable("VentasEntity") as VentasEntity

                when {
                    venta.nombreVentas.isEmpty() -> {
                        binding.btnNuevaVenta.setOnClickListener {
                            AddVenta()
                        }
                    }
                    else -> {
                        with(binding){
                            etNombre.setText(venta.nombreVentas)
                            etPrecio.setText(venta.costoVentas.toString())
                            etFechaVenta.setText(venta.fechaVentas)
                            autoComplete.setText(venta.pagoVentas)
                        }
                        binding.btnNuevaVenta.setOnClickListener {
                            UpdateVenta(venta.id)
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Problemitas", Toast.LENGTH_LONG).show()
            }

        } catch (ex:Exception){
            Toast.makeText(requireContext(), "Problemitas", Toast.LENGTH_LONG).show()
        }



        return binding.root
    }

    private fun UpdateVenta(id: Int) {
        val precio = Integer.parseInt(binding.etPrecio.text.toString())

            if(validateFields(binding.tilPrecio,binding.tilNombre)){
                val venta = VentasEntity(id, binding.etNombre.text.toString(),precio,binding.etFechaVenta.text.toString(),binding.autoComplete.text.toString())
                mVentasViewModel.updateVenta(venta)
                Toast.makeText(requireContext(), "Se guardo la venta", Toast.LENGTH_LONG).show()
                NavHostFragment.findNavController(this).navigate(R.id.balanceFragment)
            }
            else
            {
                Toast.makeText(requireContext(), "Debe ingresar todos los campos", Toast.LENGTH_LONG).show()
            }

    }

    private fun AddVenta(){
        var id1 = 0

        if(validateFields(binding.tilPrecio,binding.tilNombre)){
            val precio = Integer.parseInt(binding.etPrecio.text.toString())
            var gastito = VentasEntity(1,"", 0, "", "")
            var gas = 0
            var totalAnterior = 0
            var totalEntity = TotalEntity(1,0,0,0)
            mTotalViewModel.readTotal.observe(viewLifecycleOwner, Observer { total ->
                totalAnterior = total.ventasTotal
                totalEntity = total.copy()
            })
            mVentasViewModel.readAllData.observe(viewLifecycleOwner, Observer { gasto ->
                for (i in 0..id1-1){
                    gastito = gasto.get(i)
                    gas += gastito.costoVentas
                }
                val precTotal = totalAnterior + gas
                totalEntity.ventasTotal = precTotal
                mTotalViewModel.updateTotal(totalEntity)
                totalEntity.ventasTotal = totalEntity.ventasTotal + precio
                mTotalViewModel.updateTotal(totalEntity)
            })
            val venta = VentasEntity(0, binding.etNombre.text.toString(),precio,binding.etFechaVenta.text.toString(),binding.autoComplete.text.toString())
            mVentasViewModel.addVenta(venta)
            Toast.makeText(requireContext(), "Se guardo la venta", Toast.LENGTH_LONG).show()
            NavHostFragment.findNavController(this).navigate(R.id.balanceFragment)
        }
        else
        {
            Toast.makeText(requireContext(), "Debe ingresar todos los campos", Toast.LENGTH_LONG).show()
        }

    }

    private fun validateFields(vararg textFields: TextInputLayout): Boolean {
        var isValid = true

        for(textField in textFields) {
            if (textField.editText?.text.toString().trim().isEmpty()) {
                textField.error = getString(R.string.helper_text_require)
                isValid = false
            } else textField.error = null
        }

        if(!isValid) Snackbar.make(binding.root,
            R.string.message_valid,
            Snackbar.LENGTH_SHORT).show()

        return isValid
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        val supportFragmentManager = requireActivity().supportFragmentManager
        datePicker.show(supportFragmentManager, "datePicker")
    }
    fun onDateSelected(day:Int, month:Int, year:Int){
        val mes = month + 1
        binding.etFechaVenta.setText("$day/${mes}/$year")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: Toolbar = binding.ToolVenta

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = "Nueva venta"
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }



}
