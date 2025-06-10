package com.example.worlpuzzlegame

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.worlpuzzlegame.databinding.ActivityPuanBinding
import java.text.SimpleDateFormat
import java.util.*

class Puan : AppCompatActivity() {

    private lateinit var binding: ActivityPuanBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPuanBinding.inflate(layoutInflater)
        setContentView(binding.root)



        sharedPreferences = getSharedPreferences("GameData", MODE_PRIVATE)

        score = sharedPreferences.getInt("SCORE", 0) // Skoru yükle


        saveHighScore(score) // Yeni skoru kaydet
        setupScoreList() // Skor listesini yükle
    }

    private fun saveHighScore(newScore: Int) {
        val sharedPreferences = getSharedPreferences("GameData", MODE_PRIVATE)

        // **Skor listesini yükleyelim, hata önleyelim**
        val scoresList = sharedPreferences.getString("SCORE_LIST", "")
            ?.split(";")
            ?.filter { it.isNotEmpty() }
            ?.toMutableList() ?: mutableListOf()

        val currentDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val newEntry = "Score : ${newScore.toString()} -  date : ${currentDate.toString()}"

        scoresList.add(newEntry)

        // **Listeyi sıralayalım ve yanlış formatları önleyelim**
        scoresList.sortByDescending { it.split(" - ")[0].toIntOrNull() ?: 0 }

        // **En düşük skoru silelim (18’dan büyük olursa)**
        if (scoresList.size >= 18) {
            scoresList.removeAt(scoresList.lastIndex)
        }

        sharedPreferences.edit()
            .putString("SCORE_LIST", scoresList.joinToString(";"))
            .apply()
    }

    private fun setupScoreList() {
        val scores = loadHighScores()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, scores)
        binding.listViewScores.adapter = adapter
    }

    private fun loadHighScores(): List<String> {
        val sharedPreferences = getSharedPreferences("GameData", MODE_PRIVATE)

        val scores = sharedPreferences.getString("SCORE_LIST", "")
            ?.split(";")
            ?.filter { it.isNotEmpty() } ?: listOf()

        return scores.sortedByDescending { it.split(" - ")[0].replace("Score : ", "").toIntOrNull() ?: 0 }
    }

}