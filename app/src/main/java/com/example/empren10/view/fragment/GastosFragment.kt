package com.example.empren10.view.fragment

import android.graphics.Color
import android.os.Bundle
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
import com.example.empren10.data.respository.TotalRepository
import com.example.empren10.databinding.FragmentGastosBinding
import com.example.empren10.model.GastosEntity
import com.example.empren10.model.TotalEntity
import com.example.empren10.viewmodel.GastosViewModel
import com.example.empren10.viewmodel.TotalViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class GastosFragment : Fragment() {

    private lateinit var binding: FragmentGastosBinding
    private lateinit var mGastosViewModel: GastosViewModel
    private lateinit var mTotalViewModel: TotalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGastosBinding.inflate(inflater, container, false)

        val metodo_pago = resources.getStringArray(R.array.metodo_pago)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,metodo_pago)
        binding.autoComplete.setAdapter(arrayAdapter)

        mGastosViewModel = ViewModelProvider(this).get(GastosViewModel::class.java)
        mTotalViewModel = ViewModelProvider(this).get(TotalViewModel::class.java)

        try {
            if(arguments != null){
                val Gastos = arguments!!.getSerializable("GastosEntity") as GastosEntity

                when {
                    Gastos.nombreGastos.isEmpty() -> {
                        binding.btnNuevoGasto.setOnClickListener {
                            AddGasto(Gastos.id)
                        }
                    }
                    else -> {
                        with(binding){
                            etNombre.setText(Gastos.nombreGastos)
                            etPrecio.setText(Gastos.costoGastos.toString())
                            fechaGasto.setText(Gastos.fechaGastos)
                            autoComplete.setText(Gastos.pagoGastos)
                        }
                        binding.btnNuevoGasto.setOnClickListener {
                            UpdateGasto(Gastos.id)
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Problemitas", Toast.LENGTH_LONG).show()
            }

        } catch (ex:Exception){
            Toast.makeText(requireContext(), "Problemitas", Toast.LENGTH_LONG).show()
        }

        binding.fechaGasto.setOnClickListener { showDatePickerDialog() }

        return binding.root
    }

    private fun UpdateGasto(id: Int) {

        if(validateFields(binding.tilPrecio,binding.tilNombre)){
            val precio = Integer.parseInt(binding.etPrecio.text.toString())
            var gastito = GastosEntity(1)
            var gas = 0
            var totalEntity = TotalEntity(1)
            var totalAnterior = totalEntity.gastosTotal
            totalAnterior -= gastito.costoGastos
            totalAnterior += precio
            totalEntity.gastosTotal = totalAnterior
            mTotalViewModel.updateTotal(totalEntity)

//            mTotalViewModel.readTotal.observe(viewLifecycleOwner, Observer { total ->
//                mGastosViewModel.readAllData.observe(viewLifecycleOwner, Observer { gasto ->
//                    gastito = gasto.get(id-1)
//                })
//            })

//            updateTotal(DiositoPlis,gastito,precio)

            val gasto = GastosEntity(id, binding.etNombre.text.toString(),precio,binding.fechaGasto.text.toString(),binding.autoComplete.text.toString())
            mGastosViewModel.updateGasto(gasto)
            Toast.makeText(requireContext(), "Se guardo el gasto", Toast.LENGTH_LONG).show()
            NavHostFragment.findNavController(this).navigate(R.id.balanceFragment)
        }
        else
        {
            Toast.makeText(requireContext(), "Debe ingresar todos los campos", Toast.LENGTH_LONG).show()
        }

    }

//    private fun updateTotal(gastosTotal: TotalEntity, get: GastosEntity, precio: Int) {
//
//        val Diositotelopido = gastosTotal.gastosTotal - get.costoGastos
//        gastosTotal.gastosTotal = Diositotelopido
//        gastosTotal.gastosTotal += precio
//
//        mTotalViewModel.updateTotal(gastosTotal)
//
//    }

    private fun AddGasto(Id: Int) {
        var id1 = 0

        if(validateFields(binding.tilPrecio,binding.tilNombre)){
            val precio = Integer.parseInt(binding.etPrecio.text.toString())
            var gastito = GastosEntity(1)
            var gas = 0
            var totalEntity = TotalEntity(1)
            var totalAnterior = totalEntity.gastosTotal
//            totalAnterior -= gastito.costoGastos
//            totalAnterior += precio
//            totalEntity.gastosTotal = totalAnterior
//            mTotalViewModel.updateTotal(totalEntity)

            mTotalViewModel.readTotal.observe(viewLifecycleOwner, Observer { total ->
                totalAnterior = total.gastosTotal
                totalEntity = total.copy()
            })
            mGastosViewModel.readAllData.observe(viewLifecycleOwner, Observer { gasto ->
                for (i in 0..id1-1){
                    gastito = gasto.get(i)
                    gas += gastito.costoGastos
                }
                Toast.makeText(requireContext(),"El valor de gas es $gas", Toast.LENGTH_SHORT).show()
                val precTotal = totalAnterior + gas
                totalEntity.gastosTotal = precTotal
                mTotalViewModel.updateTotal(totalEntity)
                totalEntity.gastosTotal = totalEntity.gastosTotal + precio
                mTotalViewModel.updateTotal(totalEntity)
            })



            val gasto = GastosEntity(nombreGastos = binding.etNombre.text.toString(), costoGastos = precio, fechaGastos = binding.fechaGasto.text.toString(), pagoGastos = binding.autoComplete.text.toString())
            mGastosViewModel.addGasto(gasto)
            Toast.makeText(requireContext(), "${gasto.id}", Toast.LENGTH_LONG).show()
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
        binding.fechaGasto.setText("$day/${mes}/$year")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: Toolbar = binding.ToolGasto

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = "Nuevo gasto"
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }

}