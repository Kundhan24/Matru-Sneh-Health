package com.matrusneh.health.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.matrusneh.health.data.dao.*
import com.matrusneh.health.data.entity.*

@Database(
    entities = [
        KickEvent::class,
        NutritionRecord::class,
        CheckupEvent::class,
        DangerSignLog::class,
        MotherProfile::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MatruSnehDatabase : RoomDatabase() {

    abstract fun kickDao(): KickDao
    abstract fun nutritionDao(): NutritionDao
    abstract fun checkupDao(): CheckupDao
    abstract fun dangerSignDao(): DangerSignDao
    abstract fun motherProfileDao(): MotherProfileDao

    companion object {
        @Volatile
        private var INSTANCE: MatruSnehDatabase? = null

        fun getDatabase(context: Context): MatruSnehDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MatruSnehDatabase::class.java,
                    "matrusneh_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
