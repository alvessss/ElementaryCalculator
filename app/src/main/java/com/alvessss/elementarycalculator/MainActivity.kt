package com.alvessss.elementarycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

private class ArithmeticCalculator(context: AppCompatActivity) {
    val buttonsIds = listOf(
        R.id.button_0, R.id.button_1, R.id.button_2,
        R.id.button_3, R.id.button_4, R.id.button_5,
        R.id.button_6, R.id.button_7, R.id.button_8,
        R.id.button_9
    );

    // init views
    val resultTv:TextView = context.findViewById(R.id.calculatorResultTextView)
    val numbersButtons = listOf(initButtonsList(context, buttonsIds))
    val plusButton:MaterialButton = context.findViewById(R.id.button_plus)
    val lessButton:MaterialButton = context.findViewById(R.id.button_less)
    val timesButton:MaterialButton = context.findViewById(R.id.button_times)
    val dividedButton:MaterialButton = context.findViewById(R.id.button_divided)

    private fun initButtonsList(context: AppCompatActivity, ids: List<Int>):List<MaterialButton> {
        val arrayList = ArrayList<MaterialButton>()
        for (btn in ids.indices) arrayList.add(context.findViewById(buttonsIds[btn]))

        return arrayList.toList();
    }
}