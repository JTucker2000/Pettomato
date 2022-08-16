package com.example.pettomato.viewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pettomato.R
import com.example.pettomato.itemOptions

class ItemsListViewAdapter(context: Context, private var numOwned: Array<Int>): BaseAdapter() {
    private val curContext: Context

    init {
        curContext = context
    }

    override fun getCount(): Int {
        return itemOptions.size
    }

    override fun getItem(position: Int): Any {
        return "Placeholder string"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(curContext)
        val layout = layoutInflater.inflate(R.layout.items_list_view_item, parent, false)

        val itemNameTextview = layout.findViewById<TextView>(R.id.item_name_textview)
        itemNameTextview.text = itemOptions[position]

        val numOwnedTextView = layout.findViewById<TextView>(R.id.numOwned_textview)
        numOwnedTextView.text = "Currently owned: ${numOwned[position]}"

        return layout
    }

    fun updateNumOwned(arrNumOwned: Array<Int>) {
        numOwned = arrNumOwned
        notifyDataSetChanged()
    }
}