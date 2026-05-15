package com.matrusneh.health.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.matrusneh.health.R
import com.matrusneh.health.data.DangerSignData
import com.matrusneh.health.databinding.FragmentDangerBinding
import com.matrusneh.health.ui.adapter.DangerSignAdapter
import com.matrusneh.health.ui.viewmodel.DangerViewModel
import com.matrusneh.health.ui.viewmodel.ViewModelFactory

class DangerFragment : Fragment() {
    private var _binding: FragmentDangerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DangerViewModel by viewModels { ViewModelFactory(requireActivity().application) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDangerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DangerSignAdapter(DangerSignData.signs) { sign ->
            viewModel.selectSign(sign)
            val bundle = Bundle().apply { putString("signId", sign.id) }
            findNavController().navigate(R.id.action_dangerFragment_to_dangerAlertFragment, bundle)
        }
        binding.rvDangerSigns.layoutManager = GridLayoutManager(context, 2)
        binding.rvDangerSigns.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
