package com.matrusneh.health.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kick_events")
data class KickEvent(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val sessionId: Long
)

@Entity(tableName = "nutrition_records")
data class NutritionRecord(
    @PrimaryKey val dateKey: String, // format yyyy-MM-dd
    val completedItems: String, // comma-separated item IDs
    val totalItems: Int = 10,
    val completedCount: Int,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "checkup_events")
data class CheckupEvent(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val checkupDateMs: Long,
    val notes: String,
    val isCompleted: Boolean = false,
    val workManagerTag: String
)

@Entity(tableName = "danger_sign_logs")
data class DangerSignLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val signId: String,
    val signName: String,
    val severity: String, // HIGH or MEDIUM
    val loggedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "mother_profile")
data class MotherProfile(
    @PrimaryKey val id: Int = 1,
    val name: String,
    val pregnancyStartDateMs: Long,
    val ashaWorkerPhone: String,
    val nearestPhcName: String,
    val nearestPhcPhone: String,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun currentWeek(): Int {
        val diffMs = System.currentTimeMillis() - pregnancyStartDateMs
        val weeks = (diffMs / (1000 * 60 * 60 * 24 * 7)).toInt() + 1
        return weeks.coerceIn(1, 42)
    }
}
