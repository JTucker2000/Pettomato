package com.example.pettomato.roomentities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "players")
data class PlayerEntity(
    @PrimaryKey val username: String,
    var money_amount: Int,
    var arena_level: Int,
    var num_bandages: Int,
    var num_firstaid: Int,
    var num_ironpaw: Int,
    var num_fightswon: Int,
    var num_fightslost: Int,
    var num_arenacoinsearned: Int,
    var num_bandagesused: Int,
    var num_firstaidused: Int,
    var num_ironpawsused: Int,
    var num_damagedealt: Int,
    var num_damagetaken: Int,
    var fightswongoal: Int,
    var fightslostgoal: Int,
    var coinsearnedgoal: Int,
    var bandagesusedgoal: Int,
    var firstaidusedgoal: Int,
    var ironpawsusedgoal: Int,
    var damagedealtgoal: Int,
    var damagetakengoal: Int
) {
    // Distance to goal variables
    val toFightsWonGoal: Int
        get() = fightswongoal - num_fightswon
    val toFightsLostGoal: Int
        get() = fightslostgoal - num_fightslost
    val toCoinsEarnedGoal: Int
        get() = coinsearnedgoal - num_arenacoinsearned
    val toBandagesUsedGoal: Int
        get() = bandagesusedgoal - num_bandagesused
    val toFirstAidUsedGoal: Int
        get() = firstaidusedgoal - num_firstaidused
    val toIronPawsUsedGoal: Int
        get() = ironpawsusedgoal - num_ironpawsused
    val toDamageDealtGoal: Int
        get() = damagedealtgoal - num_damagedealt
    val toDamageTakenGoal: Int
        get() = damagetakengoal - num_damagetaken

    // Goal reward variables
    val fightsWonGoalReward: Int
        get() = fightswongoal * 10
    val fightsLostGoalReward: Int
        get() = fightslostgoal * 10
    val coinsEarnedGoalReward: Int
        get() = coinsearnedgoal
    val bandagesUsedGoalReward: Int
        get() = bandagesusedgoal * 10
    val firstAidUsedGoalReward: Int
        get() = firstaidusedgoal * 10
    val ironPawsUsedGoalReward: Int
        get() = ironpawsusedgoal * 10
    val damageDealtGoalReward: Int
        get() = damagedealtgoal * 10
    val damageTakenGoalReward: Int
        get() = damagetakengoal * 10
}