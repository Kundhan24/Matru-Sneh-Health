package com.matrusneh.health.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.map
import com.matrusneh.health.data.*
import com.matrusneh.health.data.entity.*
import com.matrusneh.health.data.repository.*
import com.matrusneh.health.util.CheckupReminderWorker
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val kickRepo: KickRepository,
    private val nutritionRepo: NutritionRepository,
    private val checkupRepo: CheckupRepository,
    private val profileRepo: MotherProfileRepository
) : ViewModel() {
    val profile: LiveData<MotherProfile?> = profileRepo.getProfile()
    val todayKickCount: LiveData<Int> = kickRepo.getTodayKickCount()
    val todayNutrition: LiveData<NutritionRecord?> = nutritionRepo.getTodayRecord()
    val nextCheckup: LiveData<CheckupEvent?> = checkupRepo.getNextCheckup()

    val daysUntilCheckup: LiveData<Long> = nextCheckup.map { event ->
        checkupRepo.daysUntilNext(event)
    }

    val currentWeek: LiveData<Int> = profile.map { p ->
        p?.currentWeek() ?: 1
    }

    val nutritionPercent: LiveData<Int> = todayNutrition.map { record ->
        record?.let { (it.completedCount * 100) / it.totalItems } ?: 0
    }
}

class KickViewModel(private val kickRepo: KickRepository) : ViewModel() {
    val sessionId: Long = System.currentTimeMillis()
    val sessionKicks: LiveData<List<KickEvent>> = kickRepo.getKicksBySession(sessionId)
    val todayKickCount: LiveData<Int> = kickRepo.getTodayKickCount()
    
    private val _kickResult = MutableLiveData<Boolean>()
    val kickResult: LiveData<Boolean> = _kickResult

    private val _weeklyData = MutableLiveData<List<Int>>()
    val weeklyData: LiveData<List<Int>> = _weeklyData

    fun recordKick() {
        viewModelScope.launch {
            val success = kickRepo.recordKick(sessionId)
            _kickResult.postValue(success)
        }
    }

    fun loadWeeklyData() {
        viewModelScope.launch {
            _weeklyData.postValue(kickRepo.getWeeklyKickCounts())
        }
    }
}

class NutritionViewModel(private val nutritionRepo: NutritionRepository) : ViewModel() {
    val todayRecord: LiveData<NutritionRecord?> = nutritionRepo.getTodayRecord()
    val recentRecords: LiveData<List<NutritionRecord>> = nutritionRepo.getRecentRecords(7)
    
    val checkedItems: LiveData<Set<String>> = todayRecord.map { record ->
        record?.completedItems?.split(",")?.filter { id -> id.isNotEmpty() }?.toSet() ?: emptySet()
    }

    val completionPercent: LiveData<Int> = todayRecord.map { record ->
        record?.let { (it.completedCount * 100) / it.totalItems } ?: 0
    }

    private val _streak = MutableLiveData<Int>()
    val streak: LiveData<Int> = _streak

    fun toggleItem(itemId: String) {
        viewModelScope.launch {
            nutritionRepo.toggleItem(itemId, NutritionData.allIds)
            computeStreak()
        }
    }

    fun computeStreak() {
        viewModelScope.launch {
            val records = recentRecords.value ?: emptyList()
            _streak.postValue(nutritionRepo.calculateStreak(records))
        }
    }
}

class GrowthViewModel(private val profileRepo: MotherProfileRepository) : ViewModel() {
    val profile: LiveData<MotherProfile?> = profileRepo.getProfile()
    val currentWeek: LiveData<Int> = profile.map { p ->
        p?.currentWeek() ?: 1
    }
    
    val currentWeekInfo: LiveData<WeekInfo?> = currentWeek.map { week ->
        BabyGrowthData.getWeek(week)
    }

    val allWeeks: List<WeekInfo> = BabyGrowthData.weeks
}

class CheckupViewModel(
    private val checkupRepo: CheckupRepository,
    private val application: Application
) : AndroidViewModel(application) {
    val allCheckups: LiveData<List<CheckupEvent>> = checkupRepo.getAllCheckups()
    val nextCheckup: LiveData<CheckupEvent?> = checkupRepo.getNextCheckup()
    
    val daysUntilNext: LiveData<Long> = nextCheckup.map { event ->
        checkupRepo.daysUntilNext(event)
    }

    fun addCheckup(title: String, dateMs: Long, notes: String) {
        viewModelScope.launch {
            val event = CheckupEvent(
                title = title,
                checkupDateMs = dateMs,
                notes = notes,
                workManagerTag = "checkup_${System.currentTimeMillis()}"
            )
            val id = checkupRepo.addCheckup(event)
            CheckupReminderWorker.schedule(application, id, title, dateMs)
        }
    }

    fun deleteCheckup(event: CheckupEvent) {
        viewModelScope.launch {
            CheckupReminderWorker.cancel(application, event.id)
            checkupRepo.deleteCheckup(event)
        }
    }

    fun markCompleted(id: Long) {
        viewModelScope.launch {
            CheckupReminderWorker.cancel(application, id)
            checkupRepo.markCompleted(id)
        }
    }
}

class DangerViewModel(private val dangerRepo: DangerSignRepository) : ViewModel() {
    val recentLogs: LiveData<List<DangerSignLog>> = dangerRepo.getRecentLogs()
    
    private val _selectedSign = MutableLiveData<DangerSign?>()
    val selectedSign: LiveData<DangerSign?> = _selectedSign

    fun selectSign(sign: DangerSign) {
        _selectedSign.value = sign
        viewModelScope.launch {
            dangerRepo.logSign(sign.id, sign.name, sign.severity)
        }
    }

    fun clearSelection() {
        _selectedSign.value = null
    }
}

class WorkerViewModel(
    private val profileRepo: MotherProfileRepository,
    private val checkupRepo: CheckupRepository,
    private val kickRepo: KickRepository,
    private val nutritionRepo: NutritionRepository,
    private val dangerRepo: DangerSignRepository
) : ViewModel() {
    val profile: LiveData<MotherProfile?> = profileRepo.getProfile()
    val allCheckups: LiveData<List<CheckupEvent>> = checkupRepo.getAllCheckups()
    val dangerLogs: LiveData<List<DangerSignLog>> = dangerRepo.getAllLogs()
    val recentNutrition: LiveData<List<NutritionRecord>> = nutritionRepo.getRecentRecords(7)

    fun updateProfile(profile: MotherProfile) {
        viewModelScope.launch {
            profileRepo.saveProfile(profile)
        }
    }
}

class SetupViewModel(private val profileRepo: MotherProfileRepository) : ViewModel() {
    val profile: LiveData<MotherProfile?> = profileRepo.getProfile()
    
    private val _setupDone = MutableLiveData<Boolean>()
    val setupDone: LiveData<Boolean> = _setupDone

    fun saveProfile(name: String, lmpDateMs: Long, ashaPhone: String, phcName: String, phcPhone: String) {
        viewModelScope.launch {
            val profile = MotherProfile(
                name = name,
                pregnancyStartDateMs = lmpDateMs,
                ashaWorkerPhone = ashaPhone,
                nearestPhcName = phcName,
                nearestPhcPhone = phcPhone
            )
            profileRepo.saveProfile(profile)
            _setupDone.postValue(true)
        }
    }

    fun isSetupComplete(): LiveData<Boolean> {
        return profile.map { p -> p != null && p.pregnancyStartDateMs > 0 }
    }
}

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    private val db = MatruSnehDatabase.getDatabase(application)
    private val kickRepo = KickRepository(db.kickDao())
    private val nutritionRepo = NutritionRepository(db.nutritionDao())
    private val checkupRepo = CheckupRepository(db.checkupDao())
    private val dangerRepo = DangerSignRepository(db.dangerSignDao())
    private val profileRepo = MotherProfileRepository(db.motherProfileDao())

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel(kickRepo, nutritionRepo, checkupRepo, profileRepo) as T
            modelClass.isAssignableFrom(KickViewModel::class.java) -> KickViewModel(kickRepo) as T
            modelClass.isAssignableFrom(NutritionViewModel::class.java) -> NutritionViewModel(nutritionRepo) as T
            modelClass.isAssignableFrom(GrowthViewModel::class.java) -> GrowthViewModel(profileRepo) as T
            modelClass.isAssignableFrom(CheckupViewModel::class.java) -> CheckupViewModel(checkupRepo, application) as T
            modelClass.isAssignableFrom(DangerViewModel::class.java) -> DangerViewModel(dangerRepo) as T
            modelClass.isAssignableFrom(WorkerViewModel::class.java) -> WorkerViewModel(profileRepo, checkupRepo, kickRepo, nutritionRepo, dangerRepo) as T
            modelClass.isAssignableFrom(SetupViewModel::class.java) -> SetupViewModel(profileRepo) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
