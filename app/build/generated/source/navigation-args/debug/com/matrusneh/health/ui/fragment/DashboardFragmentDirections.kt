package com.matrusneh.health.ui.fragment

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.matrusneh.health.R

public class DashboardFragmentDirections private constructor() {
  public companion object {
    public fun actionDashboardFragmentToKickFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_kickFragment)

    public fun actionDashboardFragmentToNutritionFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_nutritionFragment)

    public fun actionDashboardFragmentToGrowthFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_growthFragment)

    public fun actionDashboardFragmentToCheckupFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_checkupFragment)

    public fun actionDashboardFragmentToDangerFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_dangerFragment)

    public fun actionDashboardFragmentToWorkerFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_workerFragment)
  }
}
