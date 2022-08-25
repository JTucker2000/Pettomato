package com.example.pettomato.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.pettomato.PROGRESSBAR_ANIMATION_DURATION
import com.example.pettomato.R
import com.example.pettomato.roomentities.PlayerEntity
import com.example.pettomato.viewmodels.PetArenaViewModel

class ArenaStatsFragment : Fragment() {
    private val petArenaViewModel: PetArenaViewModel by viewModels()

    // View variables
    private lateinit var fightsWonNumText: TextView
    private lateinit var fightsLostNumText: TextView
    private lateinit var coinsEarnedNumText: TextView
    private lateinit var bandagesUsedNumText: TextView
    private lateinit var firstAidUsedNumText: TextView
    private lateinit var ironPawsUsedNumText: TextView
    private lateinit var damageDealtNumText: TextView
    private lateinit var damageTakenNumText: TextView
    private lateinit var fightsWonGoalText: TextView
    private lateinit var fightsLostGoalText: TextView
    private lateinit var coinsEarnedGoalText: TextView
    private lateinit var bandagesUsedGoalText: TextView
    private lateinit var firstAidUsedGoalText: TextView
    private lateinit var ironPawsUsedGoalText: TextView
    private lateinit var damageDealtGoalText: TextView
    private lateinit var damageTakenGoalText: TextView
    private lateinit var fightsWonGoalRewardText: TextView
    private lateinit var fightsLostGoalRewardText: TextView
    private lateinit var coinsEarnedGoalRewardText: TextView
    private lateinit var bandagesUsedGoalRewardText: TextView
    private lateinit var firstAidUsedGoalRewardText: TextView
    private lateinit var ironPawsUsedGoalRewardText: TextView
    private lateinit var damageDealtGoalRewardText: TextView
    private lateinit var damageTakenGoalRewardText: TextView
    private lateinit var fightsWonGoalProgressBar: ProgressBar
    private lateinit var fightsLostGoalProgressBar: ProgressBar
    private lateinit var coinsEarnedGoalProgressBar: ProgressBar
    private lateinit var bandagesUsedGoalProgressBar: ProgressBar
    private lateinit var firstAidUsedGoalProgressBar: ProgressBar
    private lateinit var ironPawsUsedGoalProgressBar: ProgressBar
    private lateinit var damageDealtGoalProgressBar: ProgressBar
    private lateinit var damageTakenGoalProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_arena_stats, container, false)

        // Set up view variables
        fightsWonNumText = view.findViewById<TextView>(R.id.fightsWonNum_text)
        fightsLostNumText = view.findViewById<TextView>(R.id.fightsLostNum_text)
        coinsEarnedNumText = view.findViewById<TextView>(R.id.coinsEarnedNum_text)
        bandagesUsedNumText = view.findViewById<TextView>(R.id.bandagesUsedNum_text)
        firstAidUsedNumText = view.findViewById<TextView>(R.id.firstAidUsedNum_text)
        ironPawsUsedNumText = view.findViewById<TextView>(R.id.ironPawsUsedNum_text)
        damageDealtNumText = view.findViewById<TextView>(R.id.damageDealtNum_text)
        damageTakenNumText = view.findViewById<TextView>(R.id.damageTakenNum_text)
        fightsWonGoalText = view.findViewById<TextView>(R.id.fightsWonGoal_text)
        fightsLostGoalText = view.findViewById<TextView>(R.id.fightsLostGoal_text)
        coinsEarnedGoalText = view.findViewById<TextView>(R.id.coinsEarnedGoal_text)
        bandagesUsedGoalText = view.findViewById<TextView>(R.id.bandagesUsedGoal_text)
        firstAidUsedGoalText = view.findViewById<TextView>(R.id.firstAidUsedGoal_text)
        ironPawsUsedGoalText = view.findViewById<TextView>(R.id.ironPawsUsedGoal_text)
        damageDealtGoalText = view.findViewById<TextView>(R.id.damageDealtGoal_text)
        damageTakenGoalText = view.findViewById<TextView>(R.id.damageTakenGoal_text)
        fightsWonGoalRewardText = view.findViewById<TextView>(R.id.fightsWonGoalReward_text)
        fightsLostGoalRewardText = view.findViewById<TextView>(R.id.fightsLostGoalReward_text)
        coinsEarnedGoalRewardText = view.findViewById<TextView>(R.id.coinsEarnedGoalReward_text)
        bandagesUsedGoalRewardText = view.findViewById<TextView>(R.id.bandagesUsedGoalReward_text)
        firstAidUsedGoalRewardText = view.findViewById<TextView>(R.id.firstAidUsedGoalReward_text)
        ironPawsUsedGoalRewardText = view.findViewById<TextView>(R.id.ironPawsUsedGoalReward_text)
        damageDealtGoalRewardText = view.findViewById<TextView>(R.id.damageDealtGoalReward_text)
        damageTakenGoalRewardText = view.findViewById<TextView>(R.id.damageTakenGoalReward_text)
        fightsWonGoalProgressBar = view.findViewById<ProgressBar>(R.id.fightsWonGoal_progressBar)
        fightsLostGoalProgressBar = view.findViewById<ProgressBar>(R.id.fightsLostGoal_progressBar)
        coinsEarnedGoalProgressBar = view.findViewById<ProgressBar>(R.id.coinsEarnedGoal_progressBar)
        bandagesUsedGoalProgressBar = view.findViewById<ProgressBar>(R.id.bandagesUsedGoal_progressBar)
        firstAidUsedGoalProgressBar = view.findViewById<ProgressBar>(R.id.firstAidUsedGoal_progressBar)
        ironPawsUsedGoalProgressBar = view.findViewById<ProgressBar>(R.id.ironPawsUsedGoal_progressBar)
        damageDealtGoalProgressBar = view.findViewById<ProgressBar>(R.id.damageDealtGoal_progressBar)
        damageTakenGoalProgressBar = view.findViewById<ProgressBar>(R.id.damageTakenGoal_progressBar)

        // Set up observer(s)
        petArenaViewModel.playerLive.observe(viewLifecycleOwner, Observer<PlayerEntity>{ currentPlayer ->
            updateUIFromPlayer(currentPlayer)
        })

        return view
    }

    private fun updateUIFromPlayer(player: PlayerEntity) {
        // Set raw statistic number textviews
        fightsWonNumText.text = player.num_fightswon.toString()
        fightsLostNumText.text = player.num_fightslost.toString()
        coinsEarnedNumText.text = player.num_arenacoinsearned.toString()
        bandagesUsedNumText.text = player.num_bandagesused.toString()
        firstAidUsedNumText.text = player.num_firstaidused.toString()
        ironPawsUsedNumText.text = player.num_ironpawsused.toString()
        damageDealtNumText.text = player.num_damagedealt.toString()
        damageTakenNumText.text = player.num_damagetaken.toString()

        // Set number to goal textviews
        fightsWonGoalText.text = "${player.toFightsWonGoal} to next goal"
        fightsLostGoalText.text = "${player.toFightsLostGoal} to next goal"
        coinsEarnedGoalText.text = "${player.toCoinsEarnedGoal} to next goal"
        bandagesUsedGoalText.text = "${player.toBandagesUsedGoal} to next goal"
        firstAidUsedGoalText.text = "${player.toFirstAidUsedGoal} to next goal"
        ironPawsUsedGoalText.text = "${player.toIronPawsUsedGoal} to next goal"
        damageDealtGoalText.text = "${player.toDamageDealtGoal} to next goal"
        damageTakenGoalText.text = "${player.toDamageTakenGoal} to next goal"

        // Set goal reward textviews
        fightsWonGoalRewardText.text = "+${player.fightsWonGoalReward} Coins"
        fightsLostGoalRewardText.text = "+${player.fightsLostGoalReward} Coins"
        coinsEarnedGoalRewardText.text = "+${player.coinsEarnedGoalReward} Coins"
        bandagesUsedGoalRewardText.text = "+${player.bandagesUsedGoalReward} Coins"
        firstAidUsedGoalRewardText.text = "+${player.firstAidUsedGoalReward} Coins"
        ironPawsUsedGoalRewardText.text = "+${player.ironPawsUsedGoalReward} Coins"
        damageDealtGoalRewardText.text = "+${player.damageDealtGoalReward} Coins"
        damageTakenGoalRewardText.text = "+${player.damageTakenGoalReward} Coins"

        // Set goal progressbars
        fightsWonGoalProgressBar.max = player.fightswongoal
        ObjectAnimator.ofInt(fightsWonGoalProgressBar, "progress", player.num_fightswon).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        fightsLostGoalProgressBar.max = player.fightslostgoal
        ObjectAnimator.ofInt(fightsLostGoalProgressBar, "progress", player.num_fightslost).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        coinsEarnedGoalProgressBar.max = player.coinsearnedgoal
        ObjectAnimator.ofInt(coinsEarnedGoalProgressBar, "progress", player.num_arenacoinsearned).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        bandagesUsedGoalProgressBar.max = player.bandagesusedgoal
        ObjectAnimator.ofInt(bandagesUsedGoalProgressBar, "progress", player.num_bandagesused).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        firstAidUsedGoalProgressBar.max = player.firstaidusedgoal
        ObjectAnimator.ofInt(firstAidUsedGoalProgressBar, "progress", player.num_firstaidused).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        ironPawsUsedGoalProgressBar.max = player.ironpawsusedgoal
        ObjectAnimator.ofInt(ironPawsUsedGoalProgressBar, "progress", player.num_ironpawsused).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        damageDealtGoalProgressBar.max = player.damagedealtgoal
        ObjectAnimator.ofInt(damageDealtGoalProgressBar, "progress", player.num_damagedealt).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
        damageTakenGoalProgressBar.max = player.damagetakengoal
        ObjectAnimator.ofInt(damageTakenGoalProgressBar, "progress", player.num_damagetaken).setDuration(PROGRESSBAR_ANIMATION_DURATION).start()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ArenaStatsFragment()
    }
}