package com.matrusneh.health.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.matrusneh.health.databinding.ActivitySetupBinding
import com.matrusneh.health.ui.viewmodel.SetupViewModel
import com.matrusneh.health.ui.viewmodel.ViewModelFactory
import java.util.*

class SetupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetupBinding
    private val viewModel: SetupViewModel by viewModels { ViewModelFactory(application) }
    private var selectedLmpDateMs: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLmpDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                cal.set(y, m, d)
                selectedLmpDateMs = cal.timeInMillis
                binding.btnLmpDate.text = "LMP: $d/${m + 1}/$y"
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnSaveProfile.setOnClickListener {
            val name = binding.etMotherName.text.toString()
            val asha = binding.etAshaPhone.text.toString()
            val phc = binding.etPhcName.text.toString()
            val phcPhone = binding.etPhcPhone.text.toString()

            if (name.isEmpty() || selectedLmpDateMs == 0L) {
                Toast.makeText(this, "Please enter name and LMP date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.saveProfile(name, selectedLmpDateMs, asha, phc, phcPhone)
        }

        viewModel.setupDone.observe(this) { done ->
            if (done) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}
