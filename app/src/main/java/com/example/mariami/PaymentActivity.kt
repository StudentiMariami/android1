package com.example.mariami  // ← Change this to your actual package name

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class PaymentActivity : AppCompatActivity() {

    // Track whether Express shipping is currently selected
    private var isExpressSelected = false

    // Constants
    private val DISCOUNT_RATE = 0.05          // 5% discount
    private val EXPRESS_FEE = 1700.0          // Express shipping fee in dollars

    // Will hold the discounted base price
    private var discountedPrice = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // ── 1. Receive data from MainActivity ──────────────────────────────
        val carName  = intent.getStringExtra("CAR_NAME") ?: "Unknown Car"
        val carPrice = intent.getDoubleExtra("CAR_PRICE", 0.0)
        val carImage = intent.getIntExtra("CAR_IMAGE", R.drawable.car1)

        // ── 2. Apply 5% discount ───────────────────────────────────────────
        discountedPrice = carPrice * (1.0 - DISCOUNT_RATE)

        // ── 3. Find views ──────────────────────────────────────────────────
        val tvCarName     = findViewById<TextView>(R.id.tvCarName)
        val tvCarPrice    = findViewById<TextView>(R.id.tvCarPrice)
        val imgThumb      = findViewById<ImageView>(R.id.imgCarThumb)
        val tvTotal       = findViewById<TextView>(R.id.tvTotal)
        val optionStandard = findViewById<LinearLayout>(R.id.optionStandard)
        val optionExpress  = findViewById<LinearLayout>(R.id.optionExpress)
        val radioStandard  = findViewById<ImageView>(R.id.radioStandard)
        val radioExpress   = findViewById<ImageView>(R.id.radioExpress)
        val btnPay         = findViewById<Button>(R.id.btnPay)

        // ── 4. Populate the item section ───────────────────────────────────
        tvCarName.text  = carName
        tvCarPrice.text = formatPrice(discountedPrice)   // show discounted price
        imgThumb.setImageResource(carImage)

        // ── 5. Set initial total (Standard = no extra charge) ─────────────
        isExpressSelected = false
        updateTotal(tvTotal)
        updateRadioUI(radioStandard, radioExpress)

        // ── 6. Shipping option click listeners ────────────────────────────

        optionStandard.setOnClickListener {
            // Switch to Standard: remove Express fee
            isExpressSelected = false
            updateTotal(tvTotal)
            updateRadioUI(radioStandard, radioExpress)

            // Update container backgrounds
            optionStandard.setBackgroundResource(R.drawable.bg_shipping_option_selected)
            optionExpress.setBackgroundResource(R.drawable.bg_shipping_option)
        }

        optionExpress.setOnClickListener {
            // Switch to Express: add $1,700
            isExpressSelected = true
            updateTotal(tvTotal)
            updateRadioUI(radioStandard, radioExpress)

            // Update container backgrounds
            optionExpress.setBackgroundResource(R.drawable.bg_shipping_option_selected)
            optionStandard.setBackgroundResource(R.drawable.bg_shipping_option)
        }

        // ── 7. Pay button → go to DoneActivity ────────────────────────────
        btnPay.setOnClickListener {
            val intent = Intent(this, DoneActivity::class.java)
            startActivity(intent)
        }
    }

    // Recalculate and display the total price
    private fun updateTotal(tvTotal: TextView) {
        val total = if (isExpressSelected) {
            discountedPrice + EXPRESS_FEE
        } else {
            discountedPrice
        }
        tvTotal.text = formatPrice(total)
    }

    // Swap radio button icons based on current selection
    private fun updateRadioUI(radioStandard: ImageView, radioExpress: ImageView) {
        if (isExpressSelected) {
            radioStandard.setImageResource(R.drawable.ic_radio_unselected)
            radioExpress.setImageResource(R.drawable.ic_radio_selected)
        } else {
            radioStandard.setImageResource(R.drawable.ic_radio_selected)
            radioExpress.setImageResource(R.drawable.ic_radio_unselected)
        }
    }

    // Format a Double as "$260,000" style string
    private fun formatPrice(amount: Double): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        return "$${formatter.format(amount.toLong())}"
    }
}