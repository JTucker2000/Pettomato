package com.example.pettomato.viewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.pettomato.ITEM_OPTIONS
import com.example.pettomato.R
import com.example.pettomato.activities.PetArenaActivity

class ItemsListViewAdapter(context: Context, private var numOwned: Array<Int>): BaseAdapter() {
    private val curContext: Context
    private class ViewHolder(val itemNameTextView: TextView, val numOwnedTextView: TextView, val itemUseBtn: Button)

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
        val layout: View
        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(curContext)
            layout = layoutInflater.inflate(R.layout.items_list_view_item, parent, false)

            val itemNameTextView = layout.findViewById<TextView>(R.id.item_name_textview)
            val numOwnedTextView = layout.findViewById<TextView>(R.id.numOwned_textview)
            val itemUseBtn = layout.findViewById<Button>(R.id.item_use_btn)
            val viewHolder = ViewHolder(itemNameTextView, numOwnedTextView, itemUseBtn)
            layout.tag = viewHolder
        } else {
            layout = convertView
        }

        val viewHolder = layout.tag as ViewHolder

        // Modify item name text
        viewHolder.itemNameTextView.text = ITEM_OPTIONS[position]

        // Modify num owned text
        viewHolder.numOwnedTextView.text = "Currently owned: ${numOwned[position]}"

        // Modify item use button
        viewHolder.itemUseBtn.setOnClickListener { (curContext as PetArenaActivity).onItemUseBtnPress(position) }

        return layout
    }

    fun updateNumOwned(arrNumOwned: Array<Int>) {
        numOwned = arrNumOwned
        notifyDataSetChanged()
    }
}