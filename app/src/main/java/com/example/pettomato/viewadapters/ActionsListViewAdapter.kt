package com.example.pettomato.viewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.pettomato.*
import com.example.pettomato.activities.MainActivity

class ActionsListViewAdapter(context: Context): BaseAdapter() {
    private val curContext: Context

    init {
        curContext = context
    }

    override fun getCount(): Int {
        return ACTION_OPTIONS.size
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
        actionNameTextView.text = ACTION_OPTIONS[position]

        val actionsListViewBtn = layout.findViewById<Button>(R.id.actions_list_view_btn)
        actionsListViewBtn.text = ACTION_PRICES[position]
        actionsListViewBtn.setOnClickListener { (curContext as MainActivity).onActionListBtnPress(position) }

        return layout
    }
}