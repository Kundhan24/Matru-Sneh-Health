package com.matrusneh.health.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.matrusneh.health.databinding.FragmentCheckupBinding
import com.matrusneh.health.ui.adapter.CheckupAdapter
import com.matrusneh.health.ui.viewmodel.CheckupViewModel
import com.matrusneh.health.ui.viewmodel.ViewModelFactory
import java.util.*

class CheckupFragment : Fragment() {
    private var _binding: FragmentCheckupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckupViewModel by viewModels { ViewModelFactory(requireActivity().application) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCheckupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CheckupAdapter(
            onMarkComplete = { id -> viewModel.markCompleted(id) },
            onDelete = { event -> viewModel.deleteCheckup(event) }
        )
        binding.rvCheckups.layoutManager = LinearLayoutManager(context)
        binding.rvCheckups.adapter = adapter

        viewModel.allCheckups.observe(viewLifecycleOwner) { checkups ->
            adapter.submitList(checkups)
        }

        viewModel.nextCheckup.observe(viewLifecycleOwner) { checkup ->
            binding.tvNextCheckupName.text = checkup?.title ?: "No upcoming check-ups"
        }

        viewModel.daysUntilNext.observe(viewLifecycleOwner) { days ->
            binding.tvCountdownLarge.text = if (days >= 0) "$days Days" else "-- Days"
        }

        binding.fabAddCheckup.setOnClickListener { showAddDialog() }
    }

    private fun showAddDialog() {
        val etTitle = EditText(requireContext())
        etTitle.hint = "Check-up Title"
        val padding = (24 * resources.displayMetrics.density).toInt()
        etTitle.setPadding(padding, padding, padding, padding)

        val cal = Calendar.getInstance()

        AlertDialog.Builder(requireContext())
            .setTitle("Add Check-up")
            .setView(etTitle)
            .setPositiveButton("Set Date") { _, _ ->
                DatePickerDialog(requireContext(), { _, y: Int, m: Int, d: Int ->
                    cal.set(y, m, d)
                    val selectedDateMs = cal.timeInMillis
                    viewModel.addCheckup(etTitle.text.toString(), selectedDateMs, "")
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
