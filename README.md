
---

# **Word Puzzle Game**  tr
Bu proje, **kelime tahmin etme** mekanizmasına dayanan eğlenceli bir Android oyunu olarak tasarlandı. Oyuncular **rastgele karıştırılmış harflerden doğru kelimeyi tahmin etmeye çalışır**. Zorluk seviyesi ilerledikçe süre artırılır ve farklı kategorilerde kelimeler sunulur.  

## 🚀 **Özellikler**  
✅ **Kategori Seçimi** – Oyuncular **Meyve, Hayvan, Ülke, Teknoloji** kategorilerinden seçim yapabilir.  
✅ **Rastgele Kelimeler** – Daha önce kullanılan kelimeler tekrarlanmaz.  
✅ **Gelişmiş Harf Karışımı** – Kelimeler **tamamen rastgele değil**, mantıklı bir sıralamayla karışır.  
✅ **Seviye Sistemi** – Oyuncular puan kazandıkça seviyeleri yükselir ve süre artırılır.  
✅ **Animasyonlar ve Efektler** – **Başarı ve hata animasyonları**, oyun deneyimini daha eğlenceli hale getirir.  
✅ **Zaman Yönetimi** – Süre **seviye ilerledikçe otomatik artar** ve zamanlayıcı düzgün şekilde sıfırlanır.  
✅ **Skor Takibi** – **SharedPreferences** kullanarak skorlar kaydedilir ve sıralanır.  

## 🔧 **Kurulum**  
1️⃣ **Projeyi klonlayın:**  
```
git clone https://github.com/SELIMCNR/WorlPuzzleGame.git
```
2️⃣ **Android Studio ile açın.**  
3️⃣ **Gradle bağımlılıklarını yükleyin.**  
4️⃣ **Cihazda veya emülatörde çalıştırın.**  

## 🏗 **Kullanılan Teknolojiler**  
- **Kotlin** – Ana programlama dili  
- **Android SDK** – Mobil uygulama geliştirme  
- **SharedPreferences** – Skor takibi için veri saklama  
- **Lottie Animations** – Başarı ve hata efektleri  
- **ObjectAnimator** – UI efektleri  
- **CountDownTimer** – Zaman yönetimi  

## 🎮 **Oyun Mekanizması**  
1️⃣ Oyuncu **karışık harflerden doğru kelimeyi tahmin eder**.  
2️⃣ **Doğru cevap** → **Puan kazanır ve yeni kelime gelir.**  
3️⃣ **Yanlış cevap** → **Yeni kelime gelir ancak puan kazanmaz.**  
4️⃣ **Seviye ilerledikçe süre artırılır.**  
5️⃣ **Oyun bittiğinde tekrar başlatılabilir veya ana ekrana dönebilir.**  

## 📌 **Ek Geliştirmeler**  
💡 **Kelime listesi genişletilebilir.**  
💡 **Farklı oyun modları eklenebilir.**  
💡 **Çok oyunculu rekabetçi mod entegre edilebilir.**  
💡 **Online skor tablosu oluşturulabilir.**  

---
