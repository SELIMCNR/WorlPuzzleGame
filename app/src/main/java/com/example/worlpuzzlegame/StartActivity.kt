package com.example.worlpuzzlegame
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.worlpuzzlegame.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

private lateinit var binding: ActivityStartBinding
    private lateinit var mediaPlayer: MediaPlayer
    private val musicList = listOf(
        R.raw.music1,
        R.raw.music2,
        R.raw.music3
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        // Müzik oynatıcıyı başlat
        playRandomMusic()
        binding.buttonStart.setOnClickListener {
        val intent =  Intent(this@StartActivity, MainActivity::class.java)
        startActivity(intent)
    }

        binding.buttonSettings .setOnClickListener {
            val intent =  Intent(this@StartActivity, SettingsActivity::class.java)
            startActivity(intent)
        }
        binding.buttonExit.setOnClickListener {
            finish()
        }
        binding.buttonSettings2.setOnClickListener {
            val intent =  Intent(this@StartActivity, Puan::class.java)
            startActivity(intent)
        }
    }
    private fun playRandomMusic() {
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release() // Önceki sesi temizle
        }

        val randomMusic = musicList.random()
        mediaPlayer = MediaPlayer.create(this, randomMusic)
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}