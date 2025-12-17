package com.android.bakpia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.bakpia.model.CartItem

class CartAdapter(
    private val cartList: ArrayList<CartItem>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtQty: TextView = itemView.findViewById(R.id.txtQty)
        val txtSubtotal: TextView = itemView.findViewById(R.id.txtSubtotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartList[position]

        val subtotal = item.price * item.qty

        holder.txtName.text = item.name
        holder.txtPrice.text = "Price: Rp ${item.price}"
        holder.txtQty.text = "Qty: ${item.qty}"
        holder.txtSubtotal.text = "Subtotal: Rp $subtotal"
    }

    override fun getItemCount(): Int = cartList.size
}
