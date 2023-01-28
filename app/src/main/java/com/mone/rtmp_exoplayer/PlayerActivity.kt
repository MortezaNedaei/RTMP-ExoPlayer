package com.mone.rtmp_exoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mone.rtmp_exoplayer.ui.player.PlayerFragment

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)
        if (savedInstanceState == null) {
            val playerFragment = PlayerFragment.newInstance()
            playerFragment.arguments = intent.extras
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, playerFragment)
                .commitNow()
        }
    }
}