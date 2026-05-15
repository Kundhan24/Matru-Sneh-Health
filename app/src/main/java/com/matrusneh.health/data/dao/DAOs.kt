package com.matrusneh.health.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.matrusneh.health.data.entity.*

@Dao
interface KickDao {
    @Insert
    suspend fun insert(kick: KickEvent)

    @Query("SELECT COUNT(*) FROM kick_events WHERE timestamp >= :dayStart AND timestamp <= :dayEnd")
    fun getTodayKickCount(dayStart: Long, dayEnd: Long): LiveData<Int>

    @Query("SELECT * FROM kick_events WHERE sessionId = :sessionId ORDER BY timestamp DESC")
    fun getKicksBySession(sessionId: Long): LiveData<List<KickEvent>>

    @Query("SELECT * FROM kick_events WHERE timestamp >= :since ORDER BY timestamp ASC")
    suspend fun getKicksSince(since: Long): List<KickEvent>

    @Query("SELECT MAX(timestamp) FROM kick_events")
    suspend fun getLastKickTimestamp(): Long?

    @Delete
    suspend fun delete(kick: KickEvent)
}

@Dao
interface NutritionDao {
    @Upsert
    suspend fun upsert(record: NutritionRecord)

    @Query("SELECT * FROM nutrition_records WHERE dateKey = :dateKey")
    fun getRecordForDate(dateKey: String): LiveData<NutritionRecord?>

    @Query("SELECT * FROM nutrition_records WHERE dateKey = :dateKey")
    suspend fun getRecordForDateSync(dateKey: String): NutritionRecord?

    @Query("SELECT * FROM nutrition_records WHERE dateKey >= :fromDate ORDER BY dateKey DESC")
    fun getRecentRecords(fromDate: String): LiveData<List<NutritionRecord>>

    @Delete
    suspend fun delete(record: NutritionRecord)
}

@Dao
interface CheckupDao {
    @Insert
    suspend fun insert(event: CheckupEvent): Long

    @Update
    suspend fun update(event: CheckupEvent)

    @Delete
    suspend fun delete(event: CheckupEvent)

    @Query("SELECT * FROM checkup_events ORDER BY checkupDateMs ASC")
    fun getAllCheckups(): LiveData<List<CheckupEvent>>

    @Query("SELECT * FROM checkup_events WHERE isCompleted = 0 AND checkupDateMs >= :nowMs ORDER BY checkupDateMs ASC LIMIT 1")
    fun getNextCheckup(nowMs: Long): LiveData<CheckupEvent?>

    @Query("SELECT * FROM checkup_events WHERE id = :id")
    suspend fun getById(id: Long): CheckupEvent?
}

@Dao
interface DangerSignDao {
    @Insert
    suspend fun insert(log: DangerSignLog): Long

    @Delete
    suspend fun delete(log: DangerSignLog)

    @Query("SELECT * FROM danger_sign_logs ORDER BY loggedAt DESC")
    fun getAllLogs(): LiveData<List<DangerSignLog>>

    @Query("SELECT * FROM danger_sign_logs ORDER BY loggedAt DESC LIMIT 50")
    fun getRecentLogs(): LiveData<List<DangerSignLog>>
}

@Dao
interface MotherProfileDao {
    @Upsert
    suspend fun upsert(profile: MotherProfile)

    @Query("SELECT * FROM mother_profile WHERE id = 1")
    fun getProfile(): LiveData<MotherProfile?>

    @Query("SELECT * FROM mother_profile WHERE id = 1")
    suspend fun getProfileSync(): MotherProfile?
}
