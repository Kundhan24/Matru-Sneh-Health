package com.matrusneh.health.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.matrusneh.health.ui.viewmodel.SetupViewModel
import com.matrusneh.health.ui.viewmodel.ViewModelFactory

class SplashActivity : AppCompatActivity() {
    private val viewModel: SetupViewModel by viewModels { ViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        viewModel.isSetupComplete().observe(this) { isComplete ->
            if (isComplete) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, SetupActivity::class.java))
            }
            finish()
        }
    }
}
