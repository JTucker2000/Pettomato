package com.example.pettomato.viewadapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.pettomato.R

class ActionsListViewAdapter(context: Context): BaseAdapter() {
    private val TAG: String = "ActionsLstViewAdptrTag"
    private val curContext: Context
    private val actionOptions = arrayOf("Feed", "Give Water", "Pet", "Go for a walk")
    private val actionButtonText = arrayOf("-5 Coins", "-10 Coins", "-15 Coins", "Free")

    init {
        curContext = context
    }

    override fun getCount(): Int {
        return actionOptions.size
    }

    override fun getItem(position: Int): Any {
        return "Placeholder string"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(curContext)
        val layout = layoutInflater.inflate(R.layout.action_list_view_item, parent, false)

        val actionNameTextView = layout.findViewById<TextView>(R.id.action_name_textview)
        actionNameTextView.text = actionOptions[position]

        val actionButton = layout.findViewById<Button>(R.id.action_button)
        actionButton.text = actionButtonText[position]
        actionButton.setOnClickListener {
            // Do stuff based on the position of the button
        }

        return layout
    }
}