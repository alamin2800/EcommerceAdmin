package com.example.ecommerceadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ecommerceadmin.databinding.FragmentLoginBinding
import com.example.ecommerceadmin.viewmodels.LoginvViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginvViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        loginViewModel.authLD.observe(viewLifecycleOwner) {
            if (it == LoginvViewModel.Authentication.AUTHENTICATED) {
                findNavController().popBackStack()
            }
        }

        loginViewModel.errMsgLD.observe(viewLifecycleOwner) {
            binding.errMsgTV.text = it
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.emailET.text.toString()
            val pass = binding.passET.text.toString()
            // TODO: validate fields
            loginViewModel.loginAdmin(email, pass)
        }

        return binding.root
    }

}