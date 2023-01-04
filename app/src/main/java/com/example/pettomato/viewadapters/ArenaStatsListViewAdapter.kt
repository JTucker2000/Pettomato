package com.example.pettomato.viewadapters

import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ProgressBar
import android.widget.TextView
import com.example.pettomato.ARENA_STATS_NAMES
import com.example.pettomato.PROGRESSBAR_ANIMATION_DURATION
import com.example.pettomato.R
import com.example.pettomato.roomentities.PlayerEntity

class ArenaStatsListViewAdapter (context: Context, private var player: PlayerEntity): BaseAdapter() {
    private val curContext: Context
    private class ViewHolder(val statNameText: TextView, val statNumText: TextView, val statProgressBar: ProgressBar, val statToGoalText: TextView, val statGoalRewardText: TextView)

    init {
        curContext = context
    }

    override fun getCount(): Int {
        return ARENA_STATS_NAMES.size
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
            layout = layoutInflater.inflate(R.layout.stats_list_view_item, parent, false)

            val statNameText = layout.findViewById<TextView>(R.id.stat_name_text)
            val statNumText = layout.findViewById<TextView>(R.id.stat_num_text)
            val statProgressBar = layout.findViewById<ProgressBar>(R.id.stat_progressBar)
            val statToGoalText = layout.findViewById<TextView>(R.id.stat_toGoal_text)
            val statGoalRewardText = layout.findViewById<TextView>(R.id.stat_goalReward_text)
            val viewHolder = ViewHolder(statNameText, statNumText, statProgressBar, statToGoalText, statGoalRewardText)
            layout.tag = viewHolder
        } else {
            layout = convertView
        }

        val viewHolder = layout.tag as ViewHolder

        // Modify statistic name text
        viewHolder.statNameText.text = "${ARENA_STATS_NAMES[position]}:"

        when (position) {
            0 -> {
                // Modify statistic number text
                viewHolder.statNumText.text = getNumAbbreviation(player.num_fightswon)

                // Modify statistic progress bar
                viewHolder.statProgressBar.max = player.fightswongoal
                ObjectAnimator.ofInt(viewHolder.statProgressBar, "progress", player.num_fightswon).setDuration(
                    PROGRESSBAR_ANIMATION_DURATION
                ).start()

                // Modify to goal text
                viewHolder.statToGoalText.text = "${getNumAbbreviation(player.toFightsWonGoal)} to next goal"

                // Modify goal reward text
                viewHolder.statGoalRewardText.text = "+${getNumAbbreviation(player.fightsWonGoalReward)}"
            }
            1 -> {
                viewHolder.statNumText.text = getNumAbbreviation(player.num_fightslost)

                viewHolder.statProgressBar.max = player.fightslostgoal
                ObjectAnimator.ofInt(viewHolder.statProgressBar, "progress", player.num_fightslost).setDuration(
                    PROGRESSBAR_ANIMATION_DURATION
                ).start()

                viewHolder.statToGoalText.text = "${getNumAbbreviation(player.toFightsLostGoal)} to next goal"

                viewHolder.statGoalRewardText.text = "+${getNumAbbreviation(player.fightsLostGoalReward)}"
            }
            2 -> {
                viewHolder.statNumText.text = getNumAbbreviation(player.num_arenacoinsearned)

                viewHolder.statProgressBar.max = player.coinsearnedgoal
                ObjectAnimator.ofInt(viewHolder.statProgressBar, "progress", player.num_arenacoinsearned).setDuration(
                    PROGRESSBAR_ANIMATION_DURATION
                ).start()

                viewHolder.statToGoalText.text = "${getNumAbbreviation(player.toCoinsEarnedGoal)} to next goal"

                viewHolder.statGoalRewardText.text = "+${getNumAbbreviation(player.coinsEarnedGoalReward)}"
            }
            3 -> {
                viewHolder.statNumText.text = getNumAbbreviation(player.num_bandagesused)

                viewHolder.statProgressBar.max = player.bandagesusedgoal
                ObjectAnimator.ofInt(viewHolder.statProgressBar, "progress", player.num_bandagesused).setDuration(
                    PROGRESSBAR_ANIMATION_DURATION
                ).start()

                viewHolder.statToGoalText.text = "${getNumAbbreviation(player.toBandagesUsedGoal)} to next goal"

                viewHolder.statGoalRewardText.text = "+${getNumAbbreviation(player.bandagesUsedGoalReward)}"
            }
            4 -> {
                viewHolder.statNumText.text = getNumAbbreviation(player.num_firstaidused)

                viewHolder.statProgressBar.max = player.firstaidusedgoal
                ObjectAnimator.ofInt(viewHolder.statProgressBar, "progress", player.num_firstaidused).setDuration(
                    PROGRESSBAR_ANIMATION_DURATION
                ).start()

                viewHolder.statToGoalText.text = "${getNumAbbreviation(player.toFirstAidUsedGoal)} to next goal"

                viewHolder.statGoalRewardText.text = "+${getNumAbbreviation(player.firstAidUsedGoalReward)}"
            }
            5 -> {
                viewHolder.statNumText.text = getNumAbbreviation(player.num_ironpawsused)

                viewHolder.statProgressBar.max = player.ironpawsusedgoal
                ObjectAnimator.ofInt(viewHolder.statProgressBar, "progress", player.num_ironpawsused).setDuration(
                    PROGRESSBAR_ANIMATION_DURATION
                ).start()

                viewHolder.statToGoalText.text = "${getNumAbbreviation(player.toIronPawsUsedGoal)} to next goal"

                viewHolder.statGoalRewardText.text = "+${getNumAbbreviation(player.ironPawsUsedGoalReward)}"
            }
            6 -> {
                viewHolder.statNumText.text = getNumAbbreviation(player.num_damagedealt)

                viewHolder.statProgressBar.max = player.damagedealtgoal
                ObjectAnimator.ofInt(viewHolder.statProgressBar, "progress", player.num_damagedealt).setDuration(
                    PROGRESSBAR_ANIMATION_DURATION
                ).start()

                viewHolder.statToGoalText.text = "${getNumAbbreviation(player.toDamageDealtGoal)} to next goal"

                viewHolder.statGoalRewardText.text = "+${getNumAbbreviation(player.damageDealtGoalReward)}"
            }
            7 -> {
                viewHolder.statNumText.text = getNumAbbreviation(player.num_damagetaken)

                viewHolder.statProgressBar.max = player.damagetakengoal
                ObjectAnimator.ofInt(viewHolder.statProgressBar, "progress", player.num_damagetaken).setDuration(
                    PROGRESSBAR_ANIMATION_DURATION
                ).start()

                viewHolder.statToGoalText.text = "${getNumAbbreviation(player.toDamageTakenGoal)} to next goal"

                viewHolder.statGoalRewardText.text = "+${getNumAbbreviation(player.damageTakenGoalReward)}"
            }
            else -> {
                viewHolder.statNumText.text = "Error"
                Log.e("ArenaStatsLVAdapter", "Invalid position in ArenaStatsListViewAdapter")
            }
        }

        return layout
    }

    // Returns an abbreviated string version of the given amount.
    // 500 = "500", 1324 = "1.3k", 1200000 = "1.2m", etc.
    // Only abbreviates up to the trillions.
    private fun getNumAbbreviation(amount: Int): String {
        return when {
            amount >= 1000000000000 -> "${amount / 1000000000000}.${(amount % 1000000000000) / 100000000000}t"
            amount >= 1000000000 -> "${amount / 1000000000}.${(amount % 1000000000) / 100000000}b"
            amount >= 1000000 -> "${amount / 1000000}.${(amount % 1000000) / 100000}m"
            amount >= 1000 -> "${amount / 1000}.${(amount % 1000) / 100}k"
            else -> "$amount"
        }
    }

    fun updateStats(newPlayer: PlayerEntity) {
        player = newPlayer
        notifyDataSetChanged()
    }
}