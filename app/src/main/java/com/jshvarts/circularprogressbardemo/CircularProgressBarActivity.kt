package com.jshvarts.circularprogressbardemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_circular_progress_bar.*
import java.util.*

class CircularProgressBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circular_progress_bar)

        progressBar.setOnClickListener {
            v -> (v as CircularProgressBar).updateProgress(Random().nextInt(100).toFloat())
        }
    }
}
