package com.pinrushcollect.app.data

import android.content.Context
import android.content.SharedPreferences

object Prefs {
    private lateinit var prefs: SharedPreferences

    private const val KEY_START_STEP_COMPLETED = "StartStepCompleted"

    var startStepCompleted: Boolean
        get() = prefs.getBoolean(KEY_START_STEP_COMPLETED, false)
        set(value) {
            prefs.edit().putBoolean(KEY_START_STEP_COMPLETED, value).apply()
        }

    fun init(context: Context) {
        prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    var coin:Int
        get() = prefs.getInt("coin", 0)
        set(value) {
            prefs.edit().putInt("coin", value).apply()
        }


    const val LEVEL_EASY = "level_easy"
    const val LEVEL_MEDIUM = "level_medium"
    const val LEVEL_HARD = "level_hard"
    const val LEVEL_EXTREME = "level_extreme"



    fun isLevelUnlocked(level: String): Boolean {
        return prefs.getBoolean(level, false)
    }

    fun unlockLevel(level: String) {
        prefs.edit().putBoolean(level, true).apply()
    }
}