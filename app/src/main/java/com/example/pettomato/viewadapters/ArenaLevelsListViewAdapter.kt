package com.example.pettomato.viewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.pettomato.ENEMY_LIST
import com.example.pettomato.R
import com.example.pettomato.roomentities.PlayerEntity

class ArenaLevelsListViewAdapter(context: Context, private var player: PlayerEntity): BaseAdapter() {
    private val curContext: Context
    private class ViewHolder()

    init {
        curContext = context
    }

    override fun getCount(): Int {
        return ENEMY_LIST.size
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

            // init views here
            val viewHolder = ViewHolder()
            layout.tag = viewHolder
        } else {
            layout = convertView
        }

        val viewHolder = layout.tag as ViewHolder

        // Modify views here

        return layout
    }

    fun updateLevels(newPlayer: PlayerEntity) {
        player = newPlayer
        notifyDataSetChanged()
    }
}