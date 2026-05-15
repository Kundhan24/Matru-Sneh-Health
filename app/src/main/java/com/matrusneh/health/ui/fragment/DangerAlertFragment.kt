package com.matrusneh.health.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.matrusneh.health.R
import com.matrusneh.health.data.DangerSignData
import com.matrusneh.health.databinding.FragmentDangerAlertBinding
import com.matrusneh.health.ui.viewmodel.DangerViewModel
import com.matrusneh.health.ui.viewmodel.ViewModelFactory
import com.matrusneh.health.ui.viewmodel.WorkerViewModel

class DangerAlertFragment : Fragment() {
    private var _binding: FragmentDangerAlertBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DangerViewModel by viewModels { ViewModelFactory(requireActivity().application) }
    private val workerViewModel: WorkerViewModel by viewModels { ViewModelFactory(requireActivity().application) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDangerAlertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signId = arguments?.getString("signId")
        val sign = DangerSignData.signs.find { it.id == signId }

        sign?.let {
            binding.tvAlertEmoji.text = it.emoji
            binding.tvAlertName.text = it.name
            binding.tvAlertNameKn.text = it.name_kn
            binding.tvAlertSeverity.text = "${it.severity} SEVERITY"
            binding.tvAlertAction.text = it.action
            binding.tvAlertActionKn.text = it.action_kn
            
            val color = if (it.severity == "HIGH") R.color.red_500 else R.color.amber_500
            binding.alertRoot.setBackgroundResource(color)
            binding.tvAlertSeverity.setTextColor(resources.getColor(color, null))
        }

        binding.btnCallAshaAlert.setOnClickListener {
            workerViewModel.profile.value?.ashaWorkerPhone?.let { phone ->
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                startActivity(intent)
            }
        }

        binding.btnDismissAlert.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
