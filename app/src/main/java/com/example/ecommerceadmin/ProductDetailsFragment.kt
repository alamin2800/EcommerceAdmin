package com.example.ecommerceadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ecommerceadmin.databinding.FragmentProductDetailsBinding
import com.example.ecommerceadmin.utlis.getFormattedDate
import com.example.ecommerceadmin.viewmodels.ProductViewModel

class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    private val productViewModel: ProductViewModel by activityViewModels()
    private var id: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        id = arguments?.getString("id")
        id?.let {
            productViewModel.getProductByProductId(it).observe(viewLifecycleOwner) {
                binding.product = it
            }
        }
        id?.let {
            productViewModel.getPurchasesByProductId(it).observe(viewLifecycleOwner) {
                var purchaseTxt = ""
                it.forEach {
                    //     purchaseTxt += "${getFormattedDate(it.purchaseDate!!.seconds * 1000, "dd/MM/yyyy")} - Qty: ${it.quantity} - Price: ${it.purchasePrice}\n"
                    purchaseTxt += " ${
                        getFormattedDate(
                            it.purchaseDate!!.seconds * 1000,
                            "dd/MM/yyyy"
                        )
                    } - Qty: ${it.quantity} - Price: ${it.purchasePrice}\\n"
                }
                binding.purchaseListTV.text = purchaseTxt
            }
        }

        return binding.root
    }


}