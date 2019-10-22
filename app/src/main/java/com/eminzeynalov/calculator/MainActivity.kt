package com.eminzeynalov.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {

    lateinit var digitsLabel: TextView
    var lastNum: Boolean = false
    var stateError: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        digitsLabel = findViewById(R.id.digits)
    }

    fun onDigit(view: View) {
        if (stateError) {
            digitsLabel.text = (view as Button).text
            stateError = false
        } else {
            digitsLabel.append((view as Button).text)
        }
        lastNum = true
    }

    fun onOperator(view: View) {
        if (lastNum && !stateError) {
            digitsLabel.append((view as Button).text)
            lastNum = false
            lastDot = false
        }
    }

    fun onClear(view: View) {
        this.digitsLabel.text = ""
        lastNum = false
        stateError = false
        lastDot = false
    }

    fun onEqual(view: View) {
        if (lastNum && !stateError) {
            val txt = digitsLabel.text.toString()
            val expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                digitsLabel.text = result.toString()
                lastDot = true
            } catch (ex: ArithmeticException) {
                digitsLabel.text = "Error"
                stateError = true
                lastNum = false
            }
        }
    }
}