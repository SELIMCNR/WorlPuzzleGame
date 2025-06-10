package com.example.worlpuzzlegame

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.worlpuzzlegame.R

import com.example.worlpuzzlegame.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var score = 0
    private var level = 1
    private var selectedCategory = "Meyve" // Varsayılan kategori
    private var currentWord = ""
    private var currentHint = ""
    private lateinit var sharedPreferences: SharedPreferences
    private var timeLeft = 180 // Başlangıç süresi
     var countDownTimer: CountDownTimer? = null


    private val wordData = mapOf(
        "Meyve" to listOf(
            "ELMA" to "Kırmızı veya yeşil renkli, tatlı ve gevrek bir meyve 🍏🍎",
            "PORTAKAL" to "Turuncu renkli, C vitamini zengini, ferahlatıcı narenciye 🍊",
            "MUZ" to "Uzun, sarı ve yumuşak dokulu tropikal bir meyve 🍌",
            "ŞEFTALİ" to "Kadifemsi kabuklu, sulu ve tatlı aromalı bir meyve 🍑",
            "KİRAZ" to "Küçük, parlak kırmızı ve tatlı bir meyve 🍒",
            "ANANAS" to "Sarı etli, dikenli kabuklu, tropikal ve yoğun aromalı bir meyve 🍍",
            "ÇİLEK" to "Kırmızı, tatlı ve minik çekirdekleri olan meyve 🍓",
            "KİVİ" to "Yeşil içi ve tüylü dışı olan egzotik bir meyve 🥝",
            "MANGO" to "Hindistan kökenli, tatlı ve sulu tropikal bir meyve 🥭",
            "NAR" to "Küçük kırmızı taneleri olan, besleyici ve antioksidan deposu bir meyve 🍎",
            "ÜZÜM" to "Şarap yapımında kullanılan küçük yuvarlak meyve 🍇",
            "KARPUZ" to "Yaz aylarının vazgeçilmezi, içi kırmızı ve bol sulu bir meyve 🍉",
            "LİMON" to "Ekşi tadıyla yemeklere lezzet katan narenciye 🍋",
            "İNCİR" to "Ege bölgesinde yetişen, tatlı ve lifli bir meyve 🍈",
            "ERİK" to "Mor veya kırmızı renkli, ekşi-tatlı bir meyve 🍑",
            "HİNDİSTAN CEVİZİ" to "Tropikal bölgelerde yetişen, sert kabuklu ve beyaz içli meyve 🥥",
            "AVOKADO" to "Sağlıklı yağ içeren, kremsi yapılı besleyici bir meyve 🥑",
            "YABAN MERSİNİ" to "Küçük, koyu mavi renkli, antioksidan deposu bir meyve 🫐",
            "AHUDUDU" to "Küçük kırmızı ve tatlı bir meyve 🍇",
            "KAYISI" to "Şeftaliye benzeyen, narin ve tatlı bir meyve 🍑",
            "MANDALİNA" to "Kolay soyulan, tatlı ve ferahlatıcı bir narenciye 🍊",
        ),
        "Hayvan" to listOf(

                    "KÖPEK" to "Sadık dostumuz, havlayarak iletişim kurar 🐶",
                    "KEDİ" to "Bağımsız ruhlu evcil hayvan 🐱",
                    "ASLAN" to "Ormanların kralı, güçlü ve yeleleri olan bir hayvan 🦁",
                    "FİL" to "Dünyanın en büyük kara hayvanı, uzun hortumu var 🐘",
                    "ZÜRAFA" to "Uzun boynu ile ünlü, Afrika'da yaşayan hayvan 🦒",
                    "KAPLAN" to "Turuncu ve siyah çizgileri ile güçlü bir avcı 🐅",
                    "AYI" to "Kış uykusuna yatabilen büyük ve güçlü hayvan 🐻",
                    "KURT" to "Sürüler halinde gezen vahşi bir avcı 🐺",
                    "KANGURU" to "Zıplayarak hareket eden, Avustralya’nın simgesi 🦘",
                    "TİLKİ" to "Kurnazlığı ile bilinen, kırmızı kürklü yaban hayvanı 🦊",
                    "TAVŞAN" to "Hızlı koşan, uzun kulaklı sevimli hayvan 🐰",
                    "GEYİK" to "Uzun bacaklı ve boynuzlu zarif bir hayvan 🦌",
                    "KARTAL" to "Keskin gözleri ve güçlü kanatlarıyla yırtıcı kuş 🦅",
                    "BAYKUŞ" to "Gece aktif olan, bilgeliğin sembolü kuş 🦉",
                    "YUNUS" to "Zeki ve dost canlısı deniz memelisi 🐬",
                    "KÖPEKBALIĞI" to "Okyanusların korkulu rüyası, keskin dişli avcı 🦈",
                    "TİMSAH" to "Sıcak bölgelerde yaşayan, güçlü çeneli sürüngen 🐊",
                    "YILAN" to "Zehirli veya zehirsiz türleri olan uzun sürüngen 🐍",
                    "PAPAĞAN" to "Konuşmayı taklit edebilen, renkli tüylü kuş 🦜",
                    "ÇİTA" to "Dünyanın en hızlı koşan kara hayvanı 🐆",
                    "PENGUEN" to "Soğuk iklimlerde yaşayan, yüzme yeteneği güçlü kuş 🐧",
                    "SU AYGIRI" to "Suya bağlı yaşayan, iri ve güçlü memeli 🦛",
                    "GERGEDAN" to "Kalın derili, büyük boynuzlu otçul memeli 🦏",
                    "ZEBRA" to "Siyah-beyaz çizgili, hızlı koşabilen otçul hayvan 🦓",
                    "GORİL" to "Güçlü, sosyal ve zeki primat 🦍",

                    ),
            "Ülke" to listOf(
                "JAPONYA" to "Sushi kültürü ve ileri teknolojisiyle ünlü ülke 🇯🇵",
                "ÇİN" to "Dünyanın en kalabalık ülkesi, Büyük Çin Seddi ile ünlü 🇨🇳",
                "HİNDİSTAN" to "Bollywood sineması ve baharatlarıyla tanınan ülke 🇮🇳",
                "GÜNEY KORE" to "K-Pop müziği ve teknolojik gelişmişliği ile bilinen ülke 🇰🇷",
                "TAYLAND" to "Tapınakları ve tropikal plajlarıyla ünlü ülke 🇹🇭",
                "VİETNAM" to "Zengin tarihi ve eşsiz mutfağı ile dikkat çeken ülke 🇻🇳",
                "MALEZYA" to "Petronas İkiz Kuleleri ve kültürel çeşitliliği ile bilinen ülke 🇲🇾",
                "ENDONEZYA" to "Binlerce ada üzerine kurulu tropikal güzellikleri ile ünlü 🇮🇩",
                "FİLİPİNLER" to "Etkileyici sahilleri ve adalarıyla tanınan ülke 🇵🇭",
                "TÜRKİYE" to "İstanbul’un tarihi mirası ve Kapadokya’nın doğal güzellikleri ile ünlü 🇹🇷",
                "SUUDİ ARABİSTAN" to "İslam’ın kutsal şehirleri ve büyük petrol rezervleri ile bilinen ülke 🇸🇦",
                "BİRLEŞİK ARAP EMİRLİKLERİ" to "Lüks yaşam tarzı ve modern gökdelenleriyle bilinen ülke 🇦🇪",
                "KATAR" to "Küçük yüzölçümüne rağmen büyük ekonomik güce sahip ülke 🇶🇦",
                "BİRLEŞİK KRALLIK" to "Kraliyet ailesi ve tarihi yapıları ile tanınan ülke 🇬🇧",
                "ALMANYA" to "Otomobil üretimi ve tarihi şehirleriyle öne çıkan Avrupa ülkesi 🇩🇪",
                "FRANSA" to "Eyfel Kulesi ve romantik atmosferi ile ünlü ülke 🇫🇷",
                "İTALYA" to "Pizza ve makarnaları ile bilinen Akdeniz ülkesi 🇮🇹",
                "İSPANYA" to "Flamenko dansı ve güzel sahilleri ile tanınan ülke 🇪🇸",
                "HOLLANDA" to "Yel değirmenleri ve laleleri ile ünlü ülke 🇳🇱",
                "YUNANİSTAN" to "Antik mitoloji ve Akropolis ile öne çıkan ülke 🇬🇷",
                "PORTEKİZ" to "Denizcilik geçmişi ve tarihi yapılarıyla tanınan ülke 🇵🇹",
                "NORVEÇ" to "Fiyortları ve kuzey ışıkları ile ünlü ülke 🇳🇴",
                "İSVEÇ" to "İskandinav tasarımı ve doğal güzellikleri ile bilinen ülke 🇸🇪",
                "FİNLANDİYA" to "Dünyanın en mutlu ülkelerinden biri olarak bilinir 🇫🇮",
                "İSVİÇRE" to "Muhteşem Alp dağları ve çikolatasıyla tanınan ülke 🇨🇭",
                "POLONYA" to "Zengin tarihi ve kültürüyle dikkat çeken Avrupa ülkesi 🇵🇱",
                "BELÇİKA" to "Lezzetli çikolataları ve tarihi şehirleriyle ünlü ülke 🇧🇪",
                "ABD" to "Özgürlük Heykeli ve Hollywood ile tanınan büyük ülke 🇺🇸",
                "KANADA" to "Geniş doğal alanları ve kış sporları ile ünlü ülke 🇨🇦",
                "BREZİLYA" to "Karnavalları ve futboluyla bilinen Güney Amerika ülkesi 🇧🇷",
                "ARJANTİN" to "Tango müziği ve futbol tutkusu ile ünlü ülke 🇦🇷",
                "MEKSİKA" to "Tacos ve eski Aztek kalıntıları ile tanınan ülke 🇲🇽",
                "KOLOMBİYA" to "Ünlü kahve üretimi ve renkli kültürü ile dikkat çeken ülke 🇨🇴",
                "PERU" to "Machu Picchu ve İnka kalıntıları ile ünlü ülke 🇵🇪",
                "ŞİLİ" to "Uzun ve dar sahil şeridiyle tanınan Güney Amerika ülkesi 🇨🇱",
                "VENEZUELA" to "Büyük petrol rezervleri ve Angel Şelalesi ile ünlü ülke 🇻🇪",
                "KÜBA" to "Eski arabaları ve salsa müziğiyle tanınan Karayip ülkesi 🇨🇺",
                "DOMİNİK CUMHURİYETİ" to "Harika plajları ile ünlü Karayip ülkesi 🇩🇴",
                "MISIR" to "Piramitleri ve antik Mısır kültürüyle bilinen ülke 🇪🇬",
                "GÜNEY AFRİKA" to "Safari turları ve doğal parkları ile tanınan ülke 🇿🇦",
                "FAS" to "Renkli çarşıları ve çöl manzaraları ile ünlü ülke 🇲🇦",
                "KENYA" to "Safari gezileri ve vahşi yaşamıyla bilinen Afrika ülkesi 🇰🇪",
                "NİJERYA" to "Afrika'nın en büyük ekonomisi ve zengin kültürü 🇳🇬",
                "GANA" to "Altın sahilleri ve köklü tarihiyle bilinen Batı Afrika ülkesi 🇬🇭",
                "ETİYOPYA" to "Eski medeniyetleri, kahvesi ve kültürüyle tanınan Afrika ülkesi 🇪🇹",
                "TANZANYA" to "Serengeti Milli Parkı ve Kilimanjaro Dağı ile bilinen ülke 🇹🇿"
            )
            ,

            "Teknoloji" to listOf(
                "ANDROID" to "Google tarafından geliştirilen mobil işletim sistemi 💻",
                "ARAMA MOTORU" to "Bilgi bulmak için kullanılan internet işlevi 🔍",
                "BLUETOOTH" to "Kablosuz bağlantı teknolojisi 🎧",
                "ROBOT" to "Yapay zekaya sahip makineler 🤖",
                "İNTERNET" to "Dünyayı birbirine bağlayan dijital ağ 🌐",
                "AKILLI TELEFON" to "Gelişmiş özelliklere sahip cep telefonları 📱",
                "DİZÜSTÜ BİLGİSAYAR" to "Taşınabilir bilgisayarlar 💻",
                "YAPAY ZEKA" to "Makine öğrenimi ve yapay zeka teknolojileri 🧠",
                "PROGRAMLAMA" to "Kodlama ve yazılım geliştirme 🖥️",
                "YAZILIM" to "Bilgisayarlar için geliştirilen programlar 💾",
                "VERİTABANI" to "Bilgi depolamak ve yönetmek için kullanılan sistem 🗄️",
                "SANAL GERÇEKLİK" to "Gerçeklik hissi veren dijital ortamlar 🥽",
                "SİBER GÜVENLİK" to "Bilgi güvenliğini koruma alanı 🔐",
                "BULUT BİLİŞİM" to "Veri depolama ve online servisler ☁️",
                "DONANIM" to "Bilgisayarın fiziksel bileşenleri 🛠️",
                "İŞLEMCİ" to "Bilgisayarın temel işlem birimi ⚡",
                "EKRAN KARTI" to "Görselleri işleyen donanım bileşeni 🎮",
                "MİKROÇİP" to "Elektronik cihazların beyni 🏎️",
                "KUANTUM BİLİŞİM" to "Yeni nesil hesaplama teknolojisi ⚛️",
                "BLOK ZİNCİRİ" to "Kripto paralar ve veri güvenliği teknolojisi 🔗",
                "BÜYÜK VERİ" to "Devasa miktardaki bilgiyi analiz eden sistem 📊",
                "MAKİNE ÖĞRENİMİ" to "Yapay zekanın öğrenme modeli 🤖",
                "5G" to "Yeni nesil hızlı mobil internet bağlantısı 📶",
                "NESNELERİN İNTERNETİ" to "Bağlı cihazların ağı 🔌",
                "İNSANSIZ HAVA ARACI" to "Havadan görüntüleme ve taşımacılık teknolojisi 🚁",
                "KENAR BİLİŞİM" to "Veri işlemenin kaynağa yakın yapılması ⚡",
                "YAPAY SİNİR AĞLARI" to "Derin öğrenme altyapısı 🧠",
                "DERİN ÖĞRENME" to "Karmaşık veri modellerini öğrenen algoritmalar 🤖",
                "ARTIRILMIŞ GERÇEKLİK" to "Gerçek dünya üzerine sanal ögelerin bindirilmesi 🥽",
                "GİYİLEBİLİR TEKNOLOJİ" to "Vücutla entegre çalışan teknoloji ürünleri ⌚",
                "AKILLI SAAT" to "Gelişmiş özelliklere sahip kol saati ⌚",
                "3D YAZDIRMA" to "Üç boyutlu nesneleri yazdırma teknolojisi 🖨️",
                "ELEKTRİKLİ ARAÇ" to "Elektrik ile çalışan otomobiller 🚗⚡",
                "OTONOM ARAÇ" to "Sürücüsüz hareket eden otomobil 🚘",
                "GPS" to "Küresel konumlama sistemi 📍",
                "WIFI" to "Kablosuz internet erişimi 📶",
                "ETHERNET" to "Kablolu internet bağlantısı standardı 🔌",
                "USB" to "Evrensel veri aktarımı ve şarj bağlantısı 🔌",
                "HDMI" to "Video ve ses sinyallerini dijital iletimi 📺",
                "SSD" to "Katı hal disk, hızlı veri erişimi 💾",
                "HDD" to "Manyetik disk depolama 💾",
                "RAM" to "Geçici veri depolama, bellek 🖥️",
                "ANAKART" to "Bütün donanım bileşenlerini birbirine bağlayan ana kart 🛠️",
                "İŞLETİM SİSTEMİ" to "Bilgisayarın temel yazılımı 💻",
                "UYGULAMA" to "Mobil veya masaüstü yazılım 📱",
                "API" to "Programlama arayüzleri, uygulama entegrasyonu 🔗",
                "VR BAŞLIK" to "Sanal gerçeklik gözlüğü 🥽",
                "AKILLI EV" to "Bağlantılı ev otomasyon sistemleri 🏠",
                "GÜVENLİK KAMERASI" to "Gözetleme ve güvenlik cihazı 📹",
                "DRONE İLE TESLİMAT" to "Havadan kargo taşıma sistemi 🚁",
                "KRİPTO PARA" to "Dijital para birimleri 💰",
                "FİNANS TEKNOLOJİSİ" to "İnovatif para ve ödeme çözümleri 💳",
                "DEVOPS" to "Geliştirici ve operasyon süreçlerinin entegrasyonu 🛠️",
                "SAAS" to "Hizmet olarak yazılım, abonelik modeli ☁️",
                "PAAS" to "Platform olarak hizmet, geliştirici araçları ☁️",
                "IAAS" to "Altyapı olarak hizmet, bulut çözümleri ☁️",
                "VERİ MERKEZİ" to "Büyük ölçekli veri depolama tesisleri 🏢",
                "SUNUCU" to "İşlem merkezi, veri sunucusu 🖥️",
                "GÜVENLİK DUVARI" to "Ağ güvenlik koruması 🔥",
                "PROGRAMLAMA DİLİ" to "Kodlama için kullanılan diller 🖥️",
                "PYTHON" to "Popüler, esnek programlama dili 🐍",
                "JAVA" to "Platform bağımsız, güçlü programlama dili ☕",
                "C++" to "Performans odaklı, sistem programlama dili 🖥️",
                "JAVASCRIPT" to "Web geliştirme için temel dil 🌐",
                "TYPESCRIPT" to "JavaScript'i genişleten modern dil 🌐",
                "RUBY" to "Basit ve üretken bir programlama dili 💎",
                "GO" to "Google tarafından geliştirilen, verimli dil 🚀",
                "RUST" to "Güvenli ve modern sistem programlama dili 🦀",
                "PHP" to "Web uygulamaları için yaygın kullanılan dil 🌐",
                "SWIFT" to "Apple uygulamaları için geliştirilmiş dil 🍏",
                "KOTLIN" to "Modern, Android uyumlu programlama dili 📱",
                "SQL" to "Veritabanı sorgulama dili 🗄️",
                "NOSQL" to "İlişkisel olmayan veritabanları 🗄️"


        )


    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // **sharedPreferences'i başlat**
        sharedPreferences = getSharedPreferences("GameData", MODE_PRIVATE)



        startTimer()
        // Başlangıçta rastgele kelime belirleme
        setNewWord()

        binding.btnSubmit.setOnClickListener {
            val enteredWord = binding.etWordInput.text.toString().trim().lowercase(Locale.getDefault()) // **Türkçe karakterleri doğru işlemek için**
            val correctWord = currentWord.lowercase(Locale.getDefault())

            if (enteredWord == correctWord) {
                score += 10
                saveHighScore(score)
                binding.tvScore.text = "Skor: $score"
                showSuccessAnimation()
                setNewWord() // **Yeni kelimeye geç**

                if (score % 50 == 0) {
                    levelUp()
                }
            } else {
                showFailAnimation()
                Toast.makeText(this, "Yanlış cevap! Yeni kelime geliyor...", Toast.LENGTH_SHORT).show()
                setNewWord() // **Yanlış cevapta da yeni kelime getir**
            }

            binding.etWordInput.text?.clear()
        }
        setupCategorySpinner()
        setNewWord()

    }
    private fun startTimer() {
        countDownTimer?.cancel() // **Eski zamanlayıcıyı kapat**
        countDownTimer = null // **Zamanlayıcıyı sıfırla**

        countDownTimer = object : CountDownTimer((timeLeft * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 1000).toInt()
                binding.tvTime.text = "Süre: $timeLeft"
            }

            override fun onFinish() {
                gameOver()
            }
        }.start()
    }

private fun gameOver() {
    binding.tvTime.text = "Süre Doldu!"
    showRestartDialog()
}
    private fun showRestartDialog() {
        AlertDialog.Builder(this)
            .setTitle("Oyun Bitti")
            .setMessage("Tekrar oynamak ister misin?")
            .setPositiveButton("Evet") { _, _ -> restartGame() }
            .setNegativeButton("Hayır") { _, _ ->
                val intent = Intent(this, StartActivity::class.java) // **Başlangıç ekranına git**
                startActivity(intent)
                finish() // **Aktiviteyi kapat**
            }
            .show()
    }

private fun restartGame() {
    timeLeft = 180
    score = 0
    binding.tvTime.text = "Süre: $timeLeft"
    binding.tvScore.text = "Skor: $score"
    startTimer()
}


private fun setupCategorySpinner() {
        val categories = listOf("Genel","Meyve", "Hayvan", "Ülke", "Teknoloji")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter

        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCategory = categories[position]
                setNewWord()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    private fun saveHighScore(newScore: Int) {
        val sharedPreferences = getSharedPreferences("GameData", MODE_PRIVATE)

        // **Skor listesini yükleyelim, hata önleyelim**
        val scoresList = sharedPreferences.getString("SCORE_LIST", "")
            ?.split(";")
            ?.filter { it.isNotEmpty() }
            ?.toMutableList() ?: mutableListOf()

        val currentDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val newEntry = "$newScore - $currentDate"

        scoresList.add(newEntry)

        // **Listeyi sıralayalım ve yanlış formatları önleyelim**
        scoresList.sortByDescending { it.split(" - ")[0].toIntOrNull() ?: 0 }

        // **En düşük skoru silelim (10’dan büyük olursa)**
        if (scoresList.size >= 10) {
            scoresList.removeAt(scoresList.lastIndex)
        }

        sharedPreferences.edit()
            .putString("SCORE_LIST", scoresList.joinToString(";"))
            .apply()
    }

    private val usedWords = mutableSetOf<String>() // **Kullanılmış kelimeleri takip eden liste**

    private fun setNewWord() {
        val categoryWords = if (selectedCategory == "Genel") {
            wordData.values.flatten()
        } else {
            wordData[selectedCategory] ?: return
        }

        // **Kullanılmamış kelimeleri filtrele**
        val availableWords = categoryWords.filter { it.first !in usedWords }.toMutableList()
        // **Eğer tüm kelimeler kullanılmışsa listeyi sıfırla**
        if (availableWords.isEmpty()) {
            usedWords.clear()
            availableWords.addAll(categoryWords)
        }

        val selectedEntry = availableWords.random()

        currentWord = selectedEntry.first
        currentHint = selectedEntry.second
        usedWords.add(currentWord) // **Seçilen kelimeyi kullanılanlara ekle**

        binding.tvLetters.text = shuffleWord(currentWord)
        binding.tvHint.text = "İpucu: $currentHint"
    }

    private fun shuffleWord(word: String): String {
        val chars = word.toCharArray().toMutableList()

        val vowels = listOf('a', 'e', 'ı', 'i', 'o', 'ö', 'u', 'ü')
        val consonants = chars.filterNot { it.lowercaseChar() in vowels }.toMutableList()
        val vowelGroup = chars.filter { it.lowercaseChar() in vowels }.toMutableList()

        consonants.shuffle() // **Sessiz harfleri karıştır**
        vowelGroup.shuffle() // **Sesli harfleri karıştır**

        val shuffledWord = StringBuilder()
        var vowelIndex = 0
        var consonantIndex = 0

        for (char in chars) {
            if (char.lowercaseChar() in vowels) {
                shuffledWord.append(vowelGroup[vowelIndex])
                vowelIndex++
            } else {
                shuffledWord.append(consonants[consonantIndex])
                consonantIndex++
            }
        }

        return shuffledWord.toString()
    }

    private fun getNextCategory(current: String): String {
        val categories = wordData.keys.toList()
        val currentIndex = categories.indexOf(current)
        return categories[(currentIndex + 1) % categories.size]
    }

    private fun showSuccessAnimation() {
        binding.lottieSuccess.apply {
            visibility = View.VISIBLE
            playAnimation()
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) { visibility = View.GONE }
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
        Snackbar.make(binding.root, "Doğru Kelime! 🎉", Snackbar.LENGTH_SHORT).show()
    }

    private fun showFailAnimation() {
        val shake = ObjectAnimator.ofFloat(binding.etWordInput, "translationX", -10f, 10f).apply {
            duration = 300
            repeatCount = 3
        }
        shake.start()
        Toast.makeText(this, "Yanlış kelime, tekrar dene!", Toast.LENGTH_SHORT).show()
    }

    private fun levelUp() {
        level++
        timeLeft+=10
        // **Ekranda güncellenen süreyi göster**
        binding.tvTime.text = "Süre: $timeLeft"

        countDownTimer?.cancel() // **Eski zamanlayıcıyı iptal et**
        startTimer() // **Yeni süre ile zamanlayıcı başlat**


        Snackbar.make(binding.root, "Tebrikler! Seviye: $level 🚀", Snackbar.LENGTH_LONG).show()
        binding.lottieLevelUp.apply {
            setAnimation(R.raw.level_up)
            visibility = View.VISIBLE
            playAnimation()
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) { visibility = View.GONE }
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }

        val scaleUp = ObjectAnimator.ofFloat(binding.tvScore, "scaleX", 1f, 1.5f).apply {
            duration = 400
            repeatMode = ObjectAnimator.REVERSE
        }
        scaleUp.start()
    }
}