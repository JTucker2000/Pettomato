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
        petArenaViewModel.playerLive.observe(viewLifecycleOwner, Observer<PlayerEntity> { currentPlayer ->
            updateUIFromPlayer(currentPlayer)
        })

        return view
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

    private fun updateUIFromPlayer(player: PlayerEntity) {
        // Set raw statistic number textviews
        fightsWonNumText.text = getNumAbbreviation(player.num_fightswon)
        fightsLostNumText.text = getNumAbbreviation(player.num_fightslost)
        coinsEarnedNumText.text = getNumAbbreviation(player.num_arenacoinsearned)
        bandagesUsedNumText.text = getNumAbbreviation(player.num_bandagesused)
        firstAidUsedNumText.text = getNumAbbreviation(player.num_firstaidused)
        ironPawsUsedNumText.text = getNumAbbreviation(player.num_ironpawsused)
        damageDealtNumText.text = getNumAbbreviation(player.num_damagedealt)
        damageTakenNumText.text = getNumAbbreviation(player.num_damagetaken)

        // Set number to goal textviews
        fightsWonGoalText.text = "${getNumAbbreviation(player.toFightsWonGoal)} to next goal"
        fightsLostGoalText.text = "${getNumAbbreviation(player.toFightsLostGoal)} to next goal"
        coinsEarnedGoalText.text = "${getNumAbbreviation(player.toCoinsEarnedGoal)} to next goal"
        bandagesUsedGoalText.text = "${getNumAbbreviation(player.toBandagesUsedGoal)} to next goal"
        firstAidUsedGoalText.text = "${getNumAbbreviation(player.toFirstAidUsedGoal)} to next goal"
        ironPawsUsedGoalText.text = "${getNumAbbreviation(player.toIronPawsUsedGoal)} to next goal"
        damageDealtGoalText.text = "${getNumAbbreviation(player.toDamageDealtGoal)} to next goal"
        damageTakenGoalText.text = "${getNumAbbreviation(player.toDamageTakenGoal)} to next goal"

        // Set goal reward textviews
        fightsWonGoalRewardText.text = "+${getNumAbbreviation(player.fightsWonGoalReward)}"
        fightsLostGoalRewardText.text = "+${getNumAbbreviation(player.fightsLostGoalReward)}"
        coinsEarnedGoalRewardText.text = "+${getNumAbbreviation(player.coinsEarnedGoalReward)}"
        bandagesUsedGoalRewardText.text = "+${getNumAbbreviation(player.bandagesUsedGoalReward)}"
        firstAidUsedGoalRewardText.text = "+${getNumAbbreviation(player.firstAidUsedGoalReward)}"
        ironPawsUsedGoalRewardText.text = "+${getNumAbbreviation(player.ironPawsUsedGoalReward)}"
        damageDealtGoalRewardText.text = "+${getNumAbbreviation(player.damageDealtGoalReward)}"
        damageTakenGoalRewardText.text = "+${getNumAbbreviation(player.damageTakenGoalReward)}"

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
        fun newInstance() = ArenaStatsFragment()
    }
}