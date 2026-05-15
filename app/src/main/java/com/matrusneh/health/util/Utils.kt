package com.matrusneh.health.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.matrusneh.health.R
import com.matrusneh.health.ui.activity.MainActivity
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

object PinManager {
    private const val PREFS_NAME = "secure_prefs"
    private const val KEY_PIN_HASH = "pin_hash"
    private const val KEY_SALT = "pin_salt"

    private fun getPrefs(context: Context): SharedPreferences {
        return try {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            EncryptedSharedPreferences.create(
                context,
                PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
    }

    fun isPinSet(context: Context): Boolean = getPrefs(context).contains(KEY_PIN_HASH)

    fun setPin(context: Context, pin: String) {
        val salt = System.currentTimeMillis().toString()
        val hash = sha256(pin + salt)
        getPrefs(context).edit()
            .putString(KEY_PIN_HASH, hash)
            .putString(KEY_SALT, salt)
            .apply()
    }

    fun verifyPin(context: Context, pin: String): Boolean {
        val prefs = getPrefs(context)
        val storedHash = prefs.getString(KEY_PIN_HASH, null)
        val salt = prefs.getString(KEY_SALT, "")
        return storedHash == sha256(pin + salt)
    }

    fun clearPin(context: Context) {
        getPrefs(context).edit().remove(KEY_PIN_HASH).remove(KEY_SALT).apply()
    }

    private fun sha256(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}

object NotificationHelper {
    const val CHANNEL_CHECKUP = "channel_checkup"
    const val CHANNEL_KICK = "channel_kick"
    const val CHANNEL_DANGER = "channel_danger"

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(NotificationManager::class.java)
            
            val checkupChannel = NotificationChannel(CHANNEL_CHECKUP, "Check-ups", NotificationManager.IMPORTANCE_HIGH)
            val kickChannel = NotificationChannel(CHANNEL_KICK, "Kick Counter", NotificationManager.IMPORTANCE_DEFAULT)
            val dangerChannel = NotificationChannel(CHANNEL_DANGER, "Danger Signs", NotificationManager.IMPORTANCE_HIGH)
            
            manager.createNotificationChannels(listOf(checkupChannel, kickChannel, dangerChannel))
        }
    }

    fun showCheckupReminder(context: Context, title: String, body: String, notifId: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("nav_target", "checkup")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, CHANNEL_CHECKUP)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Placeholder
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notifId, builder.build())
    }

    fun showKickAlert(context: Context, count: Int) {
        val builder = NotificationCompat.Builder(context, CHANNEL_KICK)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.notif_kick_low_title))
            .setContentText(context.getString(R.string.notif_kick_low_body, count))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(2001, builder.build())
    }
}

object DateUtils {
    private val keyFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val displayFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    fun todayKey(): String = keyFormat.format(Date())
    fun toDisplayDate(ms: Long): String = displayFormat.format(Date(ms))
    fun toTime(ms: Long): String = timeFormat.format(Date(ms))

    fun dayStart(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    fun dayEnd(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)
        cal.set(Calendar.MILLISECOND, 999)
        return cal.timeInMillis
    }

    fun daysFromNow(ms: Long): Long {
        val diff = ms - System.currentTimeMillis()
        return if (diff < 0) 0 else diff / (1000 * 60 * 60 * 24)
    }
}
