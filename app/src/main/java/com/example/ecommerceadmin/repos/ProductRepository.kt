package com.example.ecommerceadmin.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceadmin.models.Product
import com.example.ecommerceadmin.models.Purchase
import com.example.ecommerceadmin.utlis.collectionCategory
import com.example.ecommerceadmin.utlis.collectionProduct
import com.example.ecommerceadmin.utlis.collectionPurchase
import com.google.firebase.firestore.FirebaseFirestore

class ProductRepository {
    val db = FirebaseFirestore.getInstance()



    fun getAllCategories() : LiveData<List<String>> {
        val catLD = MutableLiveData<List<String>>()
        db.collection(collectionCategory)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<String>()
                for (doc in value!!.documents) {
                    tempList.add(doc.get("name").toString())
                }
                catLD.value = tempList
            }
        return catLD
    }

    fun addProduct(product: Product, purchase: Purchase, callback: (String) -> Unit) {
        val wb = db.batch()
        val productDocRef = db.collection(collectionProduct).document()
        val purchaseDocRef = db.collection(collectionPurchase).document()
        product.id = productDocRef.id
        purchase.purchaseId = purchaseDocRef.id
        purchase.productId = product.id

        wb.set(productDocRef, product)
        wb.set(purchaseDocRef, purchase)
        wb.commit().addOnSuccessListener {
            callback("Success")
        }.addOnFailureListener {
            callback("Failure")
        }
    }


    fun getAllProducts() : LiveData<List<Product>> {
        val productLD = MutableLiveData<List<Product>>()
        db.collection(collectionProduct)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<Product>()
                for (doc in value!!.documents) {
                    doc.toObject(Product::class.java)?.let { tempList.add(it) }
                }
                productLD.value = tempList
            }
        return productLD
    }


}