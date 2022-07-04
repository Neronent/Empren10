package com.example.empren10.view.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.empren10.R
import com.example.empren10.databinding.FragmentContactoAddBinding
import com.example.empren10.model.ContactoEntity
import com.example.empren10.viewmodel.ContactosViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class ContactoAddFragment : Fragment() {

    private lateinit var binding: FragmentContactoAddBinding
    private lateinit var mContactoViewModel: ContactosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactoAddBinding.inflate(inflater, container, false)

        mContactoViewModel = ViewModelProvider(this).get(ContactosViewModel::class.java)

        try {
            if(arguments != null){
                val contacto = arguments!!.getSerializable("ContactoEntity") as ContactoEntity

                when {
                    contacto.nombreContacto.isEmpty() -> {
                        binding.btnAgregarContacto.setOnClickListener {
                            AddContact()
                        }
                    }
                    else -> {
                        with(binding){
                            etNombre.setText(contacto.nombreContacto)
                            etTelefono.setText(contacto.telefonoContacto)
                            etWebsite.setText(contacto.websiteContacto)
                            etCorreo.setText(contacto.emailContacto)
                        }
                        binding.btnAgregarContacto.setOnClickListener {
                            UpdateContacto(contacto.id)
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

    private fun UpdateContacto(id: Long) {

        if(validateFields(binding.tilTelefono,binding.tilNombre)){
            val contacto = ContactoEntity(id, binding.etNombre.text.toString(),binding.etTelefono.text.toString(),binding.etWebsite.text.toString(),binding.etCorreo.text.toString())
            mContactoViewModel.updateContacto(contacto)
            Toast.makeText(requireContext(), "Se guardo el contacto", Toast.LENGTH_LONG).show()
            NavHostFragment.findNavController(this).navigate(R.id.balanceFragment)
        }
        else
        {
            Toast.makeText(requireContext(), "Debe ingresar todos los campos", Toast.LENGTH_LONG).show()
        }

    }

    private fun AddContact(){

        if(validateFields(binding.tilTelefono,binding.tilNombre)){
            val contacto = ContactoEntity(0, binding.etNombre.text.toString(),binding.etTelefono.text.toString(),binding.etWebsite.text.toString(),binding.etCorreo.text.toString())
            mContactoViewModel.addContacto(contacto)
            Toast.makeText(requireContext(), "Se agregó un nuevo contacto", Toast.LENGTH_LONG).show()
            NavHostFragment.findNavController(this).navigate(R.id.contactosFragment)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: Toolbar = binding.ToolNewContacto

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = "Nuevo Contacto"
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }

}