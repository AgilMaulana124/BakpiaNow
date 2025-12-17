package com.android.bakpia

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.bakpia.model.Order
import com.google.firebase.database.FirebaseDatabase

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

//        val txtAmount: TextView = findViewById(R.id.txtAmount)
        val btnPay: Button = findViewById(R.id.btnPay)
        val txtAmount = findViewById<TextView>(R.id.txtAmount)

//        val userId = intent.getStringExtra("userId") ?: return
//        val total = intent.getIntExtra("total", 0)
        val userId = "userId123"


        val total = intent.getIntExtra("total", 0)
        txtAmount.text = "Total Payment: Rp $total"


        txtAmount.text = "Total: Rp $total"

        btnPay.setOnClickListener {
            FirebaseDatabase.getInstance()
                .getReference("orders")
                .push()
                .setValue(Order(userId, total, "PAID"))

            FirebaseDatabase.getInstance()
                .getReference("cart")
                .child(userId)
                .removeValue()

            Toast.makeText(this, "Payment Successful", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
