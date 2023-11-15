package com.pmdm.imcapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat


class ImcCalculatorActivity : AppCompatActivity() {

    private var isMaleSelected: Boolean = true
    private var isFemaleSelected: Boolean = false
    private var weight: Int = 40
    private var age: Int = 18

    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var textEdad: TextView
    private lateinit var textPeso: TextView
    private lateinit var btnSubtractWeightAdd: FloatingActionButton
    private lateinit var btnSubtractWeightRemove: FloatingActionButton
    private lateinit var btnSubtractAgeAdd: FloatingActionButton
    private lateinit var btnSubtractAgeRemove: FloatingActionButton
    private lateinit var calcButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_calculator)
        initComponents()
        initListeners()
        initUI()
    }

    private fun setCardBackgroundColor(isComponentSelected: Boolean): Int {
        val colorReference = if (isComponentSelected) {
            R.color.bg_comp_sel //true
        } else {
            R.color.bg_comp //false
        }
        return ContextCompat.getColor(this, colorReference)
    }

    private fun setGenderColor() {
        viewMale.setCardBackgroundColor(setCardBackgroundColor(isMaleSelected)) //true
        viewFemale.setCardBackgroundColor(setCardBackgroundColor(!isMaleSelected)) //false
    }

    private fun color() {
        isMaleSelected = !isMaleSelected
        isFemaleSelected = !isFemaleSelected
    }

    private fun initUI() {
        setGenderColor()
        setWeight()
        setAge()
    }

    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        textPeso = findViewById(R.id.tvWeight2) // Corregido el ID
        textEdad = findViewById(R.id.tvEdad2)
        btnSubtractWeightAdd = findViewById(R.id.btnAddWeight)
        btnSubtractWeightRemove = findViewById(R.id.btnSubtractWeight)
        btnSubtractAgeAdd = findViewById(R.id.btnAddAge)
        btnSubtractAgeRemove = findViewById(R.id.btnSubtractAge)
        calcButton = findViewById(R.id.calcButton)
    }

    private fun initListeners() {
        viewMale.setOnClickListener {
            color()
            setGenderColor()
        }
        viewFemale.setOnClickListener {
            color()
            setGenderColor()
        }
        rsHeight.addOnChangeListener { _, value, _ ->
            tvHeight.text = DecimalFormat("#.##").format(value) + "cm"
        }
        btnSubtractWeightAdd.setOnClickListener {
            weight += 1
            setWeight()
        }

        btnSubtractAgeAdd.setOnClickListener {
            age += 1
            setAge()
        }

        btnSubtractWeightRemove.setOnClickListener {
            weight -= 1
            setWeight()
        }

        btnSubtractAgeRemove.setOnClickListener {
            age -= 1
            setAge()
        }
        calcButton.setOnClickListener {
            calculateIMC()
        }
    }

    private fun navigate2result(titulo: String, desc: String, res: Double) {
        val intent = Intent(this, ImcResultActivity::class.java)
        intent.putExtra("titulo", titulo)
        intent.putExtra("desc", desc)
        intent.putExtra("res", res)
        startActivity(intent)
    }

    private fun setWeight() {
        textPeso.text = weight.toString()
    }

    private fun setAge() {
        textEdad.text = age.toString()
    }

    private fun calculateIMC() {
        val peso: Double = weight.toDouble()
        val altura: Double = rsHeight.values[1].toDouble() / 100
        val imc: Double = peso / (altura * altura)

        var desc: String
        var titulo: String

        when {
            imc < 18.5 -> {
                titulo = "Bajo peso"
                desc = "Tu peso es demasiado bajo para tu altura."
            }

            imc < 24.9 -> {
                titulo = "Peso normal"
                desc = "Tu peso es normal para tu altura. ¡Buen trabajo!"
            }

            imc < 29.9 -> {
                titulo = "Sobrepeso"
                desc = "Tienes un poco de sobrepeso. Considera realizar más actividad física."
            }

            else -> {
                titulo = "Obesidad"
                desc =
                    "Tienes un nivel de obesidad. Consulta a un profesional de la salud para obtener asesoramiento."
            }
        }

        navigate2result(titulo, desc, imc)
    }
}
