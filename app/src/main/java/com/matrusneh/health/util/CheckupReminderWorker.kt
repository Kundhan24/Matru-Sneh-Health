package com.matrusneh.health.util

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class CheckupReminderWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Check-up Reminder"
        val body = inputData.getString("body") ?: "You have a check-up scheduled."
        val notifId = inputData.getInt("notif_id", 0)

        NotificationHelper.showCheckupReminder(applicationContext, title, body, notifId)
        return Result.success()
    }

    companion object {
        fun schedule(context: Context, checkupId: Long, checkupTitle: String, checkupDateMs: Long) {
            val now = System.currentTimeMillis()
            
            // 3 days before
            val threeDaysBeforeMs = checkupDateMs - (3 * 24 * 60 * 60 * 1000)
            if (threeDaysBeforeMs > now) {
                val delay = threeDaysBeforeMs - now
                val data = workDataOf(
                    "title" to "Upcoming Check-up",
                    "body" to "Your check-up '$checkupTitle' is in 3 days.",
                    "notif_id" to (checkupId.toInt() * 10 + 1)
                )
                val request = OneTimeWorkRequestBuilder<CheckupReminderWorker>()
                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .setInputData(data)
                    .addTag("checkup_$checkupId")
                    .build()
                WorkManager.getInstance(context).enqueue(request)
            }

            // On the day
            if (checkupDateMs > now) {
                val delay = checkupDateMs - now
                val data = workDataOf(
                    "title" to "Check-up Today",
                    "body" to "You have a check-up '$checkupTitle' scheduled for today.",
                    "notif_id" to (checkupId.toInt() * 10 + 2)
                )
                val request = OneTimeWorkRequestBuilder<CheckupReminderWorker>()
                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .setInputData(data)
                    .addTag("checkup_$checkupId")
                    .build()
                WorkManager.getInstance(context).enqueue(request)
            }
        }

        fun cancel(context: Context, checkupId: Long) {
            WorkManager.getInstance(context).cancelAllWorkByTag("checkup_$checkupId")
        }
    }
}
