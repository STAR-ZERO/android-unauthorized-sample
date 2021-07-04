package com.star_zero.unauthorizedsample.ui.sample

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.star_zero.unauthorizedsample.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleActivity: AppCompatActivity() {
    private val viewModel: SampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.getUser()
        }
    }
}
