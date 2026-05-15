package com.matrusneh.health.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.matrusneh.health.data.NutritionData
import com.matrusneh.health.databinding.FragmentNutritionBinding
import com.matrusneh.health.ui.adapter.NutritionAdapter
import com.matrusneh.health.ui.viewmodel.NutritionViewModel
import com.matrusneh.health.ui.viewmodel.ViewModelFactory

class NutritionFragment : Fragment() {
    private var _binding: FragmentNutritionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NutritionViewModel by viewModels { ViewModelFactory(requireActivity().application) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNutritionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NutritionAdapter(NutritionData.items) { itemId ->
            viewModel.toggleItem(itemId)
        }
        binding.rvNutrition.layoutManager = LinearLayoutManager(context)
        binding.rvNutrition.adapter = adapter

        viewModel.checkedItems.observe(viewLifecycleOwner) { checked ->
            adapter.updateChecked(checked)
        }

        viewModel.completionPercent.observe(viewLifecycleOwner) { percent ->
            binding.pbNutritionRing.progress = percent
            binding.tvNutritionPercentLarge.text = "$percent%"
            binding.tvNutritionStatus.text = when {
                percent == 100 -> "Perfect day! ⭐"
                percent >= 80 -> "Great job!"
                percent >= 50 -> "Keep going"
                else -> "Start eating well"
            }
        }

        viewModel.streak.observe(viewLifecycleOwner) { streak ->
            binding.tvStreakCount.text = "🔥 $streak Day Streak"
        }

        viewModel.computeStreak()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
