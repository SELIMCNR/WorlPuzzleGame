package com.example.worlpuzzlegame

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.worlpuzzlegame.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.lottieIntro.apply {
            playAnimation()
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    val animator = ObjectAnimator.ofFloat(R.drawable.ic_launcher_background, "alpha", 0f, 1f)
                    animator.duration = 1000
                    animator.start()
                }
                override fun onAnimationEnd(animation: Animator) { visibility = View.GONE }
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }

        // Kullanıcıyı belirli bir süre sonra ana ekrana yönlendir
        Handler(Looper.getMainLooper()).postDelayed({

            startActivity(Intent(this, StartActivity::class.java))
            finish()
        }, 4000) // 2 saniye bekleme süresi
    }
}