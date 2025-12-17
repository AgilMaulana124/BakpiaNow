package com.android.bakpia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.bakpia.model.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuActivity : AppCompatActivity() {


    private lateinit var rvProducts: RecyclerView
    private val productList = ArrayList<Product>()

    val database = FirebaseDatabase.getInstance(
        "https://final-e3f9e-default-rtdb.asia-southeast1.firebasedatabase.app/"
    )
    private val productRef = database.getReference("products")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        rvProducts = findViewById(R.id.rvProducts)
        rvProducts.layoutManager = LinearLayoutManager(this)
        val btnCart = findViewById<Button>(R.id.btnCart)
        btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }



        productRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for (ds in snapshot.children) {
                    ds.getValue(Product::class.java)?.let {
                        productList.add(it)
                    }
                }
                rvProducts.adapter = ProductAdapter(productList)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
