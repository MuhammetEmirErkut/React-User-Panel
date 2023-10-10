package com.muham.pettest

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var scrollView: ScrollView
    private val handler = Handler(Looper.getMainLooper())

    private val startColor = Color.parseColor("#64B5F6") // Başlangıç rengi
    private val endColor = Color.parseColor("#BBDEFB") // Bitiş rengi
    private val totalSteps = 100 // Toplam adım sayısı
    private val stepDuration = 100L // Her adım için süre (ms)

    private var currentStep = 0
    private var forwardDirection = true // İleri yönde mi gidiyoruz?

    //Fragments
    private lateinit var bottomNavigationView : BottomNavigationView
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: FragmentPageAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scrollView = findViewById(R.id.scrollView)

        //fragmnets

        bottomNavigationView = findViewById(R.id.bottomNav)
        viewPager2 = findViewById(R.id.pagerMain)
        adapter = FragmentPageAdapter(supportFragmentManager, lifecycle)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itHome -> viewPager2.currentItem = 0
                R.id.itForum -> viewPager2.currentItem = 1
                R.id.itEnsyclopedia -> viewPager2.currentItem = 2
                R.id.itAccount -> viewPager2.currentItem = 3

            }
            true
        }

        viewPager2.adapter = adapter

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> bottomNavigationView.selectedItemId = R.id.itHome
                    1 -> bottomNavigationView.selectedItemId = R.id.itForum
                    2 -> bottomNavigationView.selectedItemId = R.id.itEnsyclopedia
                    3 -> bottomNavigationView.selectedItemId = R.id.itAccount
                }
            }
        })

        // Otomatik olarak arka plan rengini değiştiren bir zamanlayıcı başlat
        startBackgroundChangeTimer()
    }

    private fun startBackgroundChangeTimer() {
        handler.postDelayed({
            // Yeni renk hesapla ve ScrollView'ın arka plan rengini değiştir
            val newColor = calculateColor(startColor, endColor, currentStep, totalSteps)
            scrollView.setBackgroundColor(newColor)

            // Bir sonraki adıma geç veya geri dön
            if (forwardDirection) {
                currentStep++
            } else {
                currentStep--
            }

            // İleriye veya geriye gitme kararı al
            if (currentStep >= totalSteps) {
                forwardDirection = false
            } else if (currentStep <= 0) {
                forwardDirection = true
            }

            // Zamanlayıcıyı tekrar başlat
            startBackgroundChangeTimer()
        }, stepDuration)
    }

    private fun calculateColor(startColor: Int, endColor: Int, step: Int, totalSteps: Int): Int {
        val startAlpha = Color.alpha(startColor)
        val startRed = Color.red(startColor)
        val startGreen = Color.green(startColor)
        val startBlue = Color.blue(startColor)

        val endAlpha = Color.alpha(endColor)
        val endRed = Color.red(endColor)
        val endGreen = Color.green(endColor)
        val endBlue = Color.blue(endColor)

        val currentAlpha = startAlpha + (endAlpha - startAlpha) * step / totalSteps
        val currentRed = startRed + (endRed - startRed) * step / totalSteps
        val currentGreen = startGreen + (endGreen - startGreen) * step / totalSteps
        val currentBlue = startBlue + (endBlue - startBlue) * step / totalSteps

        return Color.argb(currentAlpha, currentRed, currentGreen, currentBlue)
    }
}
