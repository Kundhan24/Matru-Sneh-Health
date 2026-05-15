package com.matrusneh.health.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.matrusneh.health.data.MatruSnehDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val db = MatruSnehDatabase.getDatabase(context)
            val dao = db.checkupDao()
            
            CoroutineScope(Dispatchers.IO).launch {
                val checkups = dao.getAllCheckups().value ?: emptyList()
                checkups.forEach { checkup ->
                    if (!checkup.isCompleted && checkup.checkupDateMs > System.currentTimeMillis()) {
                        CheckupReminderWorker.schedule(context, checkup.id, checkup.title, checkup.checkupDateMs)
                    }
                }
            }
        }
    }
}
