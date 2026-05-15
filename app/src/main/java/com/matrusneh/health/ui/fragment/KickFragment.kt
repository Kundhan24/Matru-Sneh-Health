package com.matrusneh.health.ui.fragment

import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.matrusneh.health.R
import com.matrusneh.health.databinding.FragmentKickBinding
import com.matrusneh.health.ui.viewmodel.KickViewModel
import com.matrusneh.health.ui.viewmodel.ViewModelFactory
import com.matrusneh.health.util.DateUtils

class KickFragment : Fragment() {
    private var _binding: FragmentKickBinding? = null
    private val binding get() = _binding!!
    private val viewModel: KickViewModel by viewModels { ViewModelFactory(requireActivity().application) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKickBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnKickTap.setOnClickListener {
            viewModel.recordKick()
        }

        viewModel.kickResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                vibrate()
                animateButton()
            } else {
                Toast.makeText(context, "Too fast, wait a moment", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.sessionKicks.observe(viewLifecycleOwner) { kicks ->
            binding.tvSessionCount.text = "Session Kicks: ${kicks.size}"
            if (kicks.isNotEmpty()) {
                binding.tvLastKickTime.text = "Last kick: ${DateUtils.toTime(kicks.first().timestamp)}"
            }
        }

        viewModel.todayKickCount.observe(viewLifecycleOwner) { count ->
            binding.tvTodayTotal.text = "Total Today: $count"
            updateAdvice(count)
        }

        viewModel.weeklyData.observe(viewLifecycleOwner) { data ->
            binding.kickChart.setData(data)
        }

        viewModel.loadWeeklyData()
    }

    private fun vibrate() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = requireContext().getSystemService(android.content.Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator
        } else {
            requireContext().getSystemService(android.content.Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(80, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(80)
        }
    }

    private fun animateButton() {
        binding.btnKickTap.animate()
            .scaleX(0.9f)
            .scaleY(0.9f)
            .setDuration(50)
            .withEndAction {
                binding.btnKickTap.animate().scaleX(1f).scaleY(1f).setDuration(50).start()
            }.start()
    }

    private fun updateAdvice(count: Int) {
        val (text, color) = when {
            count >= 10 -> "Great! Baby is doing well." to R.color.teal_500
            count >= 5 -> "Monitor closely." to R.color.amber_500
            else -> "Count is low. Contact ASHA worker if concerned." to R.color.red_500
        }
        binding.tvKickAdvice.text = text
        binding.tvKickAdvice.setTextColor(resources.getColor(color, null))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
