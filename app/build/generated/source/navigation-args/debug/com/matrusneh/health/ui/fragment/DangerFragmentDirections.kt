package com.matrusneh.health.ui.fragment

import android.os.Bundle
import androidx.navigation.NavDirections
import com.matrusneh.health.R
import kotlin.Int
import kotlin.String

public class DangerFragmentDirections private constructor() {
  private data class ActionDangerFragmentToDangerAlertFragment(
    public val signId: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_dangerFragment_to_dangerAlertFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("signId", this.signId)
        return result
      }
  }

  public companion object {
    public fun actionDangerFragmentToDangerAlertFragment(signId: String): NavDirections =
        ActionDangerFragmentToDangerAlertFragment(signId)
  }
}
