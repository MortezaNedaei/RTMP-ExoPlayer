package com.mone.rtmp_exoplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.editText)

        editText.addTextChangedListener {
            button.isEnabled = editText.text.isNotEmpty()
        }

        button.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, PlayerActivity::class.java)
            val bundle = Bundle()
            bundle.putString("url", editText.text.toString())
            intent.putExtras(bundle)
            startActivity(intent)
        })
    }
}