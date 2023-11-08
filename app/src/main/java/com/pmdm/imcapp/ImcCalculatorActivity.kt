package com.pmdm.imcapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class ImcCalculatorActivity : AppCompatActivity() {
    private lateinit var viewMale: CardView
    private lateinit var viewFemale:CardView
    private var isMaleSelected = true
    private lateinit var rangeSlider: RangeSlider
    private lateinit var textView: TextView

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_calculator)

        initComponents()
        initListeners()
        initUI()
    }

    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)

        rangeSlider = findViewById(R.id.rsHeight)
        textView = findViewById(R.id.tvHeight)

        rangeSlider.addOnChangeListener { _, value, _ ->
            //textView.text = value.toString()
            textView.text = DecimalFormat("#.##").format(value) + " cm"
        }
    }

    private fun initListeners () {
        viewMale.setOnClickListener{
            setGenderColor()
        }
        viewFemale.setOnClickListener{
            setGenderColor()
        }
    }

    private fun setGenderColor() {
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(!isMaleSelected))
    }

    private fun getBackgroundColor(isMaleSelected: Boolean): Int {
        val isComponentSelected = isMaleSelected
        val colorReference = if(isComponentSelected) {
            R.color.bg_comp_sel
        } else {
            R.color.bg_comp
        }
        return ContextCompat.getColor(this,colorReference)
    }

    private fun initUI() {
        setGenderColor()
    }
}
