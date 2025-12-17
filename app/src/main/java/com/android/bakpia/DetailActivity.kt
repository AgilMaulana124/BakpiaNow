package com.android.bakpia

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.bakpia.model.CartItem
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val txtName: TextView = findViewById(R.id.txtName)
        val txtPrice: TextView = findViewById(R.id.txtPrice)
        val txtDescription: TextView = findViewById(R.id.txtDescription)
        val edtQty: EditText = findViewById(R.id.edtQty)
        val btnAdd: Button = findViewById(R.id.btnAddToCart)

        val userId = intent.getStringExtra("userId") ?: return
        val name = intent.getStringExtra("name") ?: ""
        val price = intent.getIntExtra("price", 0)
        val desc = intent.getStringExtra("description") ?: ""

        txtName.text = name
        txtPrice.text = "Rp $price"
        txtDescription.text = desc

        btnAdd.setOnClickListener {
            val qty = edtQty.text.toString().toInt()

            FirebaseDatabase.getInstance()
                .getReference("cart")
                .child(userId)
                .push()
                .setValue(CartItem(name, price, qty))

            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
