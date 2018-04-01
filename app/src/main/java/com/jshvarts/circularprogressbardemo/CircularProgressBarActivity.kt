package com.jshvarts.circularprogressbardemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class CircularProgressBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(CircularProgressBar(this))
    }
}
