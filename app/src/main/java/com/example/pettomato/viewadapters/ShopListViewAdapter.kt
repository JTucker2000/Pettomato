package com.example.pettomato.viewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.pettomato.*

class ShopListViewAdapter(context: Context, private var numOwned: Array<Int>): BaseAdapter() {
    private val curContext: Context

    init {
        curContext = context
    }

    override fun getCount(): Int {
        return ITEM_OPTIONS.size
    }

    override fun getItem(position: Int): Any {
        return "Placeholder string"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(curContext)
        val layout = layoutInflater.inflate(R.layout.shop_list_view_item, parent, false)

        val shopItemNameTextview = layout.findViewById<TextView>(R.id.shopItem_name_textview)
        shopItemNameTextview.text = ITEM_OPTIONS[position]

        val buyButton = layout.findViewById<Button>(R.id.buy_button)
        buyButton.text = ITEM_PRICES[position]

        val numOwnedTextView = layout.findViewById<TextView>(R.id.numOwned_textview)
        numOwnedTextView.text = "Currently owned: ${numOwned[position]}"

        return layout
    }

    fun updateNumOwned(arrNumOwned: Array<Int>) {
        numOwned = arrNumOwned
        notifyDataSetChanged()
    }
}