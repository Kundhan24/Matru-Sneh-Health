package com.matrusneh.health.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.matrusneh.health.databinding.FragmentGrowthBinding
import com.matrusneh.health.ui.adapter.GrowthAdapter
import com.matrusneh.health.ui.viewmodel.GrowthViewModel
import com.matrusneh.health.ui.viewmodel.ViewModelFactory

class GrowthFragment : Fragment() {
    private var _binding: FragmentGrowthBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GrowthViewModel by viewModels { ViewModelFactory(requireActivity().application) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGrowthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GrowthAdapter(viewModel.allWeeks) { week ->
            // Scroll to clicked week or show detail
        }
        binding.rvGrowth.layoutManager = LinearLayoutManager(context)
        binding.rvGrowth.adapter = adapter

        viewModel.currentWeek.observe(viewLifecycleOwner) { week ->
            adapter.setCurrentWeek(week)
            val position = viewModel.allWeeks.indexOfFirst { it.week == week }.coerceAtLeast(0)
            binding.rvGrowth.scrollToPosition(position)
        }

        viewModel.currentWeekInfo.observe(viewLifecycleOwner) { info ->
            info?.let {
                binding.tvDetailWeek.text = "Week ${it.week}"
                binding.tvDetailSize.text = "${it.sizeAnalogy} / ${it.sizeAnalogy_kn}"
                binding.tvDetailWeight.text = "Weight: ${it.weightRange}"
                binding.tvDetailMilestone.text = "${it.milestone} / ${it.milestone_kn}"
                binding.tvDetailNote.text = "💡 ${it.motherNote} / ${it.motherNote_kn}"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
