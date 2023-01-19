package com.example.pettomato.roomentities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "players")
data class PlayerEntity(
    @PrimaryKey val username: String = "",
    var money_amount: Int = 0,
    var arena_level: Int = 0,
    var max_arena_level: Int = 0,
    var num_bandages: Int = 0,
    var num_firstaid: Int = 0,
    var num_ironpaw: Int = 0,
    var num_fightswon: Int = 0,
    var num_fightslost: Int = 0,
    var num_arenacoinsearned: Int = 0,
    var num_bandagesused: Int = 0,
    var num_firstaidused: Int = 0,
    var num_ironpawsused: Int = 0,
    var num_damagedealt: Int = 0,
    var num_damagetaken: Int = 0,
    var fightswongoal: Int = 0,
    var fightslostgoal: Int = 0,
    var coinsearnedgoal: Int = 0,
    var bandagesusedgoal: Int = 0,
    var firstaidusedgoal: Int = 0,
    var ironpawsusedgoal: Int = 0,
    var damagedealtgoal: Int = 0,
    var damagetakengoal: Int = 0
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