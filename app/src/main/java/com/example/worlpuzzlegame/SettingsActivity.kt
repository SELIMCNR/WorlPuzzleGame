package com.example.worlpuzzlegame

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.worlpuzzlegame.databinding.ActivitySettingsBinding
class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var mediaPlayer: MediaPlayer
    private val musicList = listOf(
        R.raw.music1,
        R.raw.music2,
        R.raw.music3
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ana ekranın aksiyon barını kaldır
        supportActionBar?.hide()
        // MediaPlayer nesnesini oluştur
      //  mediaPlayer = MediaPlayer.create(this, R.raw.music1) // Varsayılan müzik başlat


        // Müzik oynatıcıyı başlat
       // playRandomMusic()

        // Dark Mode Kontrolü
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
/*
        binding.switchMusic.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                }
            } else {
                mediaPlayer.pause()
            }
        }
*/


    }



/*
        private fun playRandomMusic() {
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release() // Önceki sesi temizle
        }

        val randomMusic = musicList.random()
        mediaPlayer = MediaPlayer.create(this, randomMusic)
        mediaPlayer.start()
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer.stop()
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }*/
}