package com.matrusneh.health.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.matrusneh.health.databinding.FragmentWorkerBinding
import com.matrusneh.health.ui.adapter.CheckupAdapter
import com.matrusneh.health.ui.adapter.DangerLogAdapter
import com.matrusneh.health.ui.viewmodel.ViewModelFactory
import com.matrusneh.health.ui.viewmodel.WorkerViewModel
import com.matrusneh.health.util.PinManager

class WorkerFragment : Fragment() {
    private var _binding: FragmentWorkerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WorkerViewModel by viewModels { ViewModelFactory(requireActivity().application) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPinDialog()
    }

    private fun showPinDialog() {
        if (!PinManager.isPinSet(requireContext())) {
            PinManager.setPin(requireContext(), "1234") // Default PIN
        }

        val input = EditText(context)
        input.hint = "Enter Worker PIN"
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD

        AlertDialog.Builder(requireContext())
            .setTitle("Secure Access")
            .setView(input)
            .setPositiveButton("Verify") { _, _ ->
                if (PinManager.verifyPin(requireContext(), input.text.toString())) {
                    setupWorkerContent()
                } else {
                    Toast.makeText(context, "Wrong PIN", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
            .setNegativeButton("Cancel") { _, _ -> findNavController().popBackStack() }
            .setCancelable(false)
            .show()
    }

    private fun setupWorkerContent() {
        val checkupAdapter = CheckupAdapter({}, {})
        binding.rvWorkerCheckups.layoutManager = LinearLayoutManager(context)
        binding.rvWorkerCheckups.adapter = checkupAdapter

        viewModel.allCheckups.observe(viewLifecycleOwner) { checkups ->
            checkupAdapter.submitList(checkups)
        }

        viewModel.recentNutrition.observe(viewLifecycleOwner) { records ->
            val summary = records.joinToString("\n") { "${it.dateKey}: ${it.completedCount}/${it.totalItems} done" }
            binding.tvWorkerNutrition.text = if (summary.isEmpty()) "No recent data" else summary
        }

        val dangerAdapter = DangerLogAdapter()
        binding.rvWorkerDanger.layoutManager = LinearLayoutManager(context)
        binding.rvWorkerDanger.adapter = dangerAdapter
        viewModel.dangerLogs.observe(viewLifecycleOwner) { logs ->
            dangerAdapter.submitList(logs)
        }

        binding.btnShareSummary.setOnClickListener { shareSummary() }
    }

    private fun shareSummary() {
        val profile = viewModel.profile.value ?: return
        val text = """
            Matru-Sneh Health Summary
            Name: ${profile.name}
            Week: ${profile.currentWeek()}
            PHC: ${profile.nearestPhcName}
            Checkups: ${viewModel.allCheckups.value?.size ?: 0}
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(intent, "Share via"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
