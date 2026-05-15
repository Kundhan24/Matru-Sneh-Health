package com.matrusneh.health.data.repository

import androidx.lifecycle.LiveData
import com.matrusneh.health.data.dao.*
import com.matrusneh.health.data.entity.*
import java.text.SimpleDateFormat
import java.util.*

class KickRepository(private val kickDao: KickDao) {
    private val DEBOUNCE = 300L

    suspend fun recordKick(sessionId: Long): Boolean {
        val lastTimestamp = kickDao.getLastKickTimestamp()
        val currentTimestamp = System.currentTimeMillis()
        
        if (lastTimestamp != null && (currentTimestamp - lastTimestamp) < DEBOUNCE) {
            return false
        }
        
        kickDao.insert(KickEvent(sessionId = sessionId, timestamp = currentTimestamp))
        return true
    }

    fun getTodayKickCount(): LiveData<Int> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        val dayStart = cal.timeInMillis
        cal.set(Calendar.HOUR_OF_DAY, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)
        val dayEnd = cal.timeInMillis
        return kickDao.getTodayKickCount(dayStart, dayEnd)
    }

    fun getKicksBySession(sessionId: Long): LiveData<List<KickEvent>> {
        return kickDao.getKicksBySession(sessionId)
    }

    suspend fun getWeeklyKickCounts(): List<Int> {
        val counts = mutableListOf<Int>()
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        
        for (i in 6 downTo 0) {
            val start = cal.timeInMillis - (i * 24 * 60 * 60 * 1000)
            val end = start + (24 * 60 * 60 * 1000) - 1
            val kicks = kickDao.getKicksSince(start).filter { it.timestamp <= end }
            counts.add(kicks.size)
        }
        return counts
    }
}

class NutritionRepository(private val nutritionDao: NutritionDao) {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun todayKey(): String = dateFormat.format(Date())

    fun getTodayRecord(): LiveData<NutritionRecord?> {
        return nutritionDao.getRecordForDate(todayKey())
    }

    fun getRecentRecords(days: Int): LiveData<List<NutritionRecord>> {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -days)
        return nutritionDao.getRecentRecords(dateFormat.format(cal.time))
    }

    suspend fun toggleItem(itemId: String, allItemIds: List<String>) {
        val key = todayKey()
        val record = nutritionDao.getRecordForDateSync(key) ?: NutritionRecord(
            dateKey = key,
            completedItems = "",
            completedCount = 0,
            totalItems = allItemIds.size
        )
        
        val items = record.completedItems.split(",").filter { it.isNotEmpty() }.toMutableSet()
        if (items.contains(itemId)) {
            items.remove(itemId)
        } else {
            items.add(itemId)
        }
        
        val updatedRecord = record.copy(
            completedItems = items.joinToString(","),
            completedCount = items.size,
            updatedAt = System.currentTimeMillis()
        )
        nutritionDao.upsert(updatedRecord)
    }

    suspend fun calculateStreak(records: List<NutritionRecord>): Int {
        var streak = 0
        val today = todayKey()
        val sortedRecords = records.sortedByDescending { it.dateKey }
        
        for (record in sortedRecords) {
            val percent = (record.completedCount.toFloat() / record.totalItems) * 100
            if (percent >= 80) {
                streak++
            } else if (record.dateKey != today) {
                break
            }
        }
        return streak
    }
}

class CheckupRepository(private val checkupDao: CheckupDao) {
    fun getAllCheckups(): LiveData<List<CheckupEvent>> = checkupDao.getAllCheckups()
    
    fun getNextCheckup(): LiveData<CheckupEvent?> {
        return checkupDao.getNextCheckup(System.currentTimeMillis())
    }

    suspend fun addCheckup(event: CheckupEvent): Long = checkupDao.insert(event)

    suspend fun updateCheckup(event: CheckupEvent) = checkupDao.update(event)

    suspend fun deleteCheckup(event: CheckupEvent) = checkupDao.delete(event)

    suspend fun markCompleted(id: Long) {
        val event = checkupDao.getById(id)
        event?.let {
            checkupDao.update(it.copy(isCompleted = true))
        }
    }

    fun daysUntilNext(checkup: CheckupEvent?): Long {
        if (checkup == null) return -1
        val diff = checkup.checkupDateMs - System.currentTimeMillis()
        return if (diff < 0) 0 else diff / (1000 * 60 * 60 * 24)
    }
}

class DangerSignRepository(private val dangerDao: DangerSignDao) {
    fun getRecentLogs(): LiveData<List<DangerSignLog>> = dangerDao.getRecentLogs()
    fun getAllLogs(): LiveData<List<DangerSignLog>> = dangerDao.getAllLogs()
    
    suspend fun logSign(signId: String, signName: String, severity: String): Long {
        return dangerDao.insert(DangerSignLog(signId = signId, signName = signName, severity = severity))
    }

    suspend fun deleteLog(log: DangerSignLog) = dangerDao.delete(log)
}

class MotherProfileRepository(private val profileDao: MotherProfileDao) {
    fun getProfile(): LiveData<MotherProfile?> = profileDao.getProfile()
    suspend fun getProfileSync(): MotherProfile? = profileDao.getProfileSync()
    suspend fun saveProfile(profile: MotherProfile) = profileDao.upsert(profile)
    
    suspend fun isProfileSetup(): Boolean {
        val profile = profileDao.getProfileSync()
        return profile != null && profile.pregnancyStartDateMs > 0
    }
}
