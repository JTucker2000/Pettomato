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
    private class ViewHolder(val actionNameTextView: TextView, val actionsListViewBtn: Button)

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
        val layout: View
        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(curContext)
            layout = layoutInflater.inflate(R.layout.action_list_view_item, parent, false)

            val actionNameTextView = layout.findViewById<TextView>(R.id.action_name_textview)
            val actionsListViewBtn = layout.findViewById<Button>(R.id.actions_list_view_btn)
            val viewHolder = ViewHolder(actionNameTextView, actionsListViewBtn)
            layout.tag = viewHolder
        } else {
            layout = convertView
        }

        val viewHolder = layout.tag as ViewHolder

        // Modify action name text
        viewHolder.actionNameTextView.text = ACTION_OPTIONS[position]

        // Modify action button
        viewHolder.actionsListViewBtn.text = ACTION_PRICES[position]
        viewHolder.actionsListViewBtn.setOnClickListener { (curContext as MainActivity).onActionListBtnPress(position) }

        return layout
    }
}