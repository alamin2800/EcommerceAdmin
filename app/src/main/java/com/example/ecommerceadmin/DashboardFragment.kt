package com.example.ecommerceadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceadmin.adapters.DashboardItemAdapter
import com.example.ecommerceadmin.databinding.FragmentDashboardBinding
import com.example.ecommerceadmin.viewmodels.LoginvViewModel

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val loginViewModel: LoginvViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        loginViewModel.authLD.observe(viewLifecycleOwner) {
            if (it == LoginvViewModel.Authentication.UNAUTHENTICATED) {
                findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
            }
        }
        val adapter = DashboardItemAdapter()
        val glm = GridLayoutManager(requireActivity(), 2)
        binding.dashboardRV.layoutManager = glm
        binding.dashboardRV.adapter = adapter

        return binding.root
    }


}