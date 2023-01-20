package com.example.pettomato.viewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.pettomato.ENEMY_LIST
import com.example.pettomato.R
import com.example.pettomato.activities.PetArenaActivity
import com.example.pettomato.roomentities.EnemyEntity
import com.example.pettomato.roomentities.PlayerEntity

class ArenaLevelsListViewAdapter(context: Context, private var player: PlayerEntity): BaseAdapter() {
    private val curContext: Context
    private class ViewHolder(val levelEnemyPetImage: ImageView, val levelEnemyNameText: TextView, val levelEnemyLevelText: TextView, val arenaLevelsListViewBtn: Button)

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
            layout = layoutInflater.inflate(R.layout.arena_levels_list_view_item, parent, false)

            val levelEnemyPetImage = layout.findViewById<ImageView>(R.id.level_enemy_petImage)
            val levelEnemyNameText = layout.findViewById<TextView>(R.id.level_enemyName_text)
            val levelEnemyLevelText = layout.findViewById<TextView>(R.id.level_enemyLevel_text)
            val arenaLevelsListViewBtn = layout.findViewById<Button>(R.id.arena_levels_list_view_btn)
            val viewHolder = ViewHolder(levelEnemyPetImage, levelEnemyNameText, levelEnemyLevelText, arenaLevelsListViewBtn)
            layout.tag = viewHolder
        } else {
            layout = convertView
        }

        val viewHolder = layout.tag as ViewHolder

        if(player.max_arena_level >= position + 1) {
            val curEnemy: EnemyEntity = ENEMY_LIST[position]

            // Modify level enemy image
            viewHolder.levelEnemyPetImage.setImageResource(curEnemy.image_id)

            // Modify level enemy name text
            viewHolder.levelEnemyNameText.text = curEnemy.enemy_name

            // Modify level enemy level text
            viewHolder.levelEnemyLevelText.text = "LVL ${curEnemy.enemy_level}"

            // Modify arena level button
            if(player.arena_level == position + 1) {
                viewHolder.arenaLevelsListViewBtn.text = "SELECTED"
                viewHolder.arenaLevelsListViewBtn.textSize = 9f
                viewHolder.arenaLevelsListViewBtn.alpha = .5f
                viewHolder.arenaLevelsListViewBtn.isClickable = false
            } else {
                viewHolder.arenaLevelsListViewBtn.text = "SELECT"
                viewHolder.arenaLevelsListViewBtn.textSize = 10f
                viewHolder.arenaLevelsListViewBtn.alpha = 1f
                viewHolder.arenaLevelsListViewBtn.isClickable = true
            }
            viewHolder.arenaLevelsListViewBtn.setOnClickListener { (curContext as PetArenaActivity).onArenaLevelsListViewBtnPress(position) }
        } else {
            // Modify showing level is not unlocked
            viewHolder.levelEnemyPetImage.setImageResource(R.drawable.enemy_level_locked)
            viewHolder.levelEnemyNameText.text = "LOCKED"
            viewHolder.levelEnemyLevelText.text = "UNKNOWN"
            viewHolder.arenaLevelsListViewBtn.text = "LOCKED"
            viewHolder.arenaLevelsListViewBtn.textSize = 10f
            viewHolder.arenaLevelsListViewBtn.alpha = .5f
            viewHolder.arenaLevelsListViewBtn.isClickable = false
        }

        return layout
    }

    fun updateLevels(newPlayer: PlayerEntity) {
        player = newPlayer
        notifyDataSetChanged()
    }
}