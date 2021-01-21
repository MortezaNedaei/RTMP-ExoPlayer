package com.mone.rtmp_exoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mone.rtmp_exoplayer.ui.player.PlayerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PlayerFragment.newInstance())
                .commitNow()
        }
    }
}