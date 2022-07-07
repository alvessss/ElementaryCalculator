package com.alvessss.elementarycalculator

import android.annotation.SuppressLint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val calculator = ArithmeticCalculator(this)
    }
}

fun Double.canBeInt(): Boolean {
    if (this % 1.0 == 0.0) {
        return true
    }
    return false
}

@SuppressLint("SetTextI18n")
private class ArithmeticCalculator(context: AppCompatActivity) {
    private val display: Display = Display(context)
    private val buttons: Buttons = Buttons(context)
    private var operator: Operator = Plus()
    private var result: Double = 0.0

    init {
        val operatorsListener = View.OnClickListener {
            val displayValue: Double = display.textView.text.toString().toDouble()
            if (result == 0.0) {
                if (displayValue != 0.0) {
                    result = displayValue
                }
            }
            else if (result == displayValue) {
                // do nothing
            } else {
                result = operator.calculate(result, displayValue)
                display.textView.text = if (result.canBeInt()) result.toInt().toString() else result.toString()
            }
            operator = getOperator(it as MaterialButton)
        }

        val numbersListener = View.OnClickListener {
            val displayValue = display.textView.text.toString().toDouble()
            if (displayValue == result) {
                display.textView.text = (it as MaterialButton).text
            } else {
                display.textView.text = (if (displayValue.canBeInt()) displayValue.toInt().toString() else displayValue.toString()) + (it as MaterialButton).text
            }
        }

        val equalListener = View.OnClickListener {
            display.textView.text = if (result.canBeInt()) result.toInt().toString() else result.toString()
            result = 0.0
        }

        val cListener = View.OnClickListener {
            display.textView.text = "0"
            result = 0.0
        }

        buttons.equal.setOnClickListener(operatorsListener)
        buttons.plus.setOnClickListener(operatorsListener)
        buttons.less.setOnClickListener(operatorsListener)
        buttons.times.setOnClickListener(operatorsListener)
        buttons.divided.setOnClickListener(operatorsListener)
        buttons.c.setOnClickListener(cListener)
        buttons.dot.setOnClickListener(numbersListener)
        for (btn in buttons.numbers) {
            btn.setOnClickListener(numbersListener)
        }
    }

    private fun getOperator(button: MaterialButton): Operator {
        return when (button.text.toString()) {
            "-" -> Less()
            "x" -> Times()
            "/" -> Divided()
            else -> Plus()
        }
    }

    private class Divided: Operator() {
        override fun calculate(a: Double, b: Double): Double {
            return a / b
        }
    }

    private class Times: Operator() {
        override fun calculate(a: Double, b: Double): Double {
            return a * b
        }
    }

    private class Less: Operator() {
        override fun calculate(a: Double, b: Double): Double {
            return a - b
        }
    }

    private class Plus : Operator() {
        override fun calculate(a: Double, b: Double): Double {
            return a + b
        }
    }

    private abstract class Operator {
        abstract fun calculate(a: Double, b: Double): Double
    }

    private class Display(context: AppCompatActivity) {
        val textView:TextView = context.findViewById(R.id.calculatorResultTextView)
    }

    private class Buttons(context: AppCompatActivity) {
        val numbers:  List<MaterialButton> = initButtonsList(context)
        val plus:     MaterialButton = context.findViewById(R.id.button_plus)
        val less:     MaterialButton = context.findViewById(R.id.button_less)
        val times:    MaterialButton = context.findViewById(R.id.button_times)
        val divided:  MaterialButton = context.findViewById(R.id.button_divided)
        val c:        MaterialButton = context.findViewById(R.id.button_c)
        val dot:      MaterialButton = context.findViewById(R.id.button_dot)
        val equal:    MaterialButton = context.findViewById(R.id.button_equal)

        private fun initButtonsList(context: AppCompatActivity):List<MaterialButton> {
            val buttonsIds = listOf(
                R.id.button_0, R.id.button_1, R.id.button_2,
                R.id.button_3, R.id.button_4, R.id.button_5,
                R.id.button_6, R.id.button_7, R.id.button_8,
                R.id.button_9
            );
            val arrayList = ArrayList<MaterialButton>()
            for (btn in buttonsIds) {
                arrayList.add(context.findViewById(btn))
            }

            return arrayList.toList()
        }
    }
}