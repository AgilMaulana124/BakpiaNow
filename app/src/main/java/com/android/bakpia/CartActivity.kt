package com.android.bakpia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.bakpia.model.CartItem
import com.google.androidbrowserhelper.playbilling.provider.PaymentActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartActivity : AppCompatActivity() {

    private lateinit var rvCart: RecyclerView
    private lateinit var txtTotal: TextView
    private lateinit var btnCheckout: Button

    private val cartList = ArrayList<CartItem>()
    private lateinit var adapter: CartAdapter

    private var total = 0
    private val userId = "userId123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        rvCart = findViewById(R.id.rvCart)
        txtTotal = findViewById(R.id.txtTotal)
        btnCheckout = findViewById(R.id.btnCheckout)

        adapter = CartAdapter(cartList)
        rvCart.layoutManager = LinearLayoutManager(this)
        rvCart.adapter = adapter

        FirebaseDatabase.getInstance("https://final-e3f9e-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("cart")
            .child(userId)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    cartList.clear()
                    total = 0

                    for (ds in snapshot.children) {
                        val item = ds.getValue(CartItem::class.java)
                        if (item != null) {
                            cartList.add(item)
                            total += item.price * item.qty
                        }
                    }

                    adapter.notifyDataSetChanged()
                    txtTotal.text = "Total: Rp $total"
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FIREBASE", error.message)
                }
            })

//        btnCheckout.setOnClickListener {
//            val intent = Intent(this, PaymentActivity::class.java)
//            intent.putExtra("userId", userId)
//            intent.putExtra("total", total)
//            startActivity(intent)
//        }
        btnCheckout.setOnClickListener {

            if (total <= 0) {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("total", total)
            startActivity(intent)
        }

    }
}

