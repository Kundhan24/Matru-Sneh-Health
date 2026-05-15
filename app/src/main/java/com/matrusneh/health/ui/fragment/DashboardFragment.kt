package com.matrusneh.health.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.matrusneh.health.R
import com.matrusneh.health.data.BabyGrowthData
import com.matrusneh.health.databinding.FragmentDashboardBinding
import com.matrusneh.health.ui.viewmodel.DashboardViewModel
import com.matrusneh.health.ui.viewmodel.ViewModelFactory

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels { ViewModelFactory(requireActivity().application) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profile.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                binding.tvGreeting.text = "Hello, ${it.name} 🌸"
            }
        }

        viewModel.currentWeek.observe(viewLifecycleOwner) { week ->
            binding.tvWeekNumber.text = "Week $week"
            val info = BabyGrowthData.getWeek(week)
            binding.tvBabySize.text = "Baby is the size of a ${info?.sizeAnalogy}"
        }

        viewModel.todayKickCount.observe(viewLifecycleOwner) { count ->
            binding.tvKickCountLarge.text = count.toString()
            binding.tvKickStatus.text = when {
                count >= 10 -> "Baby is very active!"
                count >= 5 -> "Baby is doing well."
                else -> "Low activity. Rest and monitor."
            }
            binding.tvKickCountLarge.setTextColor(resources.getColor(
                when {
                    count >= 10 -> R.color.teal_700
                    count >= 5 -> R.color.amber_500
                    else -> R.color.red_500
                }, null
            ))
        }

        viewModel.nutritionPercent.observe(viewLifecycleOwner) { percent ->
            binding.pbNutrition.progress = percent
            binding.tvNutritionPercent.text = "$percent%"
        }

        viewModel.nextCheckup.observe(viewLifecycleOwner) { checkup ->
            binding.tvNextCheckupTitle.text = checkup?.title ?: "No upcoming check-ups"
        }

        viewModel.daysUntilCheckup.observe(viewLifecycleOwner) { days ->
            binding.tvCheckupCountdown.text = if (days >= 0) "$days Days Remaining" else "--"
        }

        // Navigation
        binding.btnKick.setOnClickListener { findNavController().navigate(R.id.action_dashboardFragment_to_kickFragment) }
        binding.btnNutrition.setOnClickListener { findNavController().navigate(R.id.action_dashboardFragment_to_nutritionFragment) }
        binding.btnGrowth.setOnClickListener { findNavController().navigate(R.id.action_dashboardFragment_to_growthFragment) }
        binding.btnDanger.setOnClickListener { findNavController().navigate(R.id.action_dashboardFragment_to_dangerFragment) }
        binding.btnCheckup.setOnClickListener { findNavController().navigate(R.id.action_dashboardFragment_to_checkupFragment) }

        binding.dashboardRoot.setOnLongClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_workerFragment)
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
