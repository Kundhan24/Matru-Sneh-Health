package com.matrusneh.health.ui.fragment

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class DangerAlertFragmentArgs(
  public val signId: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("signId", this.signId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("signId", this.signId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): DangerAlertFragmentArgs {
      bundle.setClassLoader(DangerAlertFragmentArgs::class.java.classLoader)
      val __signId : String?
      if (bundle.containsKey("signId")) {
        __signId = bundle.getString("signId")
        if (__signId == null) {
          throw IllegalArgumentException("Argument \"signId\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"signId\" is missing and does not have an android:defaultValue")
      }
      return DangerAlertFragmentArgs(__signId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): DangerAlertFragmentArgs {
      val __signId : String?
      if (savedStateHandle.contains("signId")) {
        __signId = savedStateHandle["signId"]
        if (__signId == null) {
          throw IllegalArgumentException("Argument \"signId\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"signId\" is missing and does not have an android:defaultValue")
      }
      return DangerAlertFragmentArgs(__signId)
    }
  }
}
