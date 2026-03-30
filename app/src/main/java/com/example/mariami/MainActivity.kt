package com.example.mariami // ← Change this to your actual package name

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get references to the 4 car card layouts
        val cardBMW = findViewById<android.widget.LinearLayout>(R.id.cardBMW)
        val cardMercedes = findViewById<android.widget.LinearLayout>(R.id.cardMercedes)
        val cardPorsche = findViewById<android.widget.LinearLayout>(R.id.cardPorsche)
        val cardFerrari = findViewById<android.widget.LinearLayout>(R.id.cardFerrari)

        // BMW M3 — click sends name, price, and drawable resource id to PaymentActivity
        cardBMW.setOnClickListener {
            openPayment(
                name = "BMW M3 (F80 generation)",
                price = 38000.0,
                imageRes = R.drawable.car1
            )
        }

        // Mercedes-Benz CLA
        cardMercedes.setOnClickListener {
            openPayment(
                name = "Mercedes-Benz CLA-Class (Second Generation)",
                price = 46400.0,
                imageRes = R.drawable.car2
            )
        }

        // Porsche 911 GT3 RS
        cardPorsche.setOnClickListener {
            openPayment(
                name = "Porsche 911 GT3 RS (991.1 Generation)",
                price = 189000.0,
                imageRes = R.drawable.car3
            )
        }

        // Ferrari 488 Spider
        cardFerrari.setOnClickListener {
            openPayment(
                name = "Ferrari 488 Spider",
                price = 260000.0,
                imageRes = R.drawable.car4
            )
        }
    }

    // Helper: builds the Intent with car data and starts PaymentActivity
    private fun openPayment(name: String, price: Double, imageRes: Int) {
        val intent = Intent(this, PaymentActivity::class.java).apply {
            putExtra("CAR_NAME", name)
            putExtra("CAR_PRICE", price)      // original price as Double
            putExtra("CAR_IMAGE", imageRes)   // drawable resource id as Int
        }
        startActivity(intent)
    }
}