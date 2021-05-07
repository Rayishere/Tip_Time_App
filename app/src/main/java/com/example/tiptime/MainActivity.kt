@file:Suppress("SpellCheckingInspection")

package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    /**
     * Defined at this level
     * Will be used across multiple method in MainActivity class
     * keyword: lateinit <-- a promise that your code will
     * initialized the variable before using it
     */
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize the binding object which you'll use to access Views
        //in the activity_main.xml layout
        binding = ActivityMainBinding.inflate(layoutInflater)

        //Specifies the root of the hierarchy of views in your app
        //a.k.a binding.root
        setContentView(binding.root)

        //a click listener on Calculate button
        binding.calculateButton.setOnClickListener { calculateTip() }

        /**Active when the user inputs "enter"
         * onKey() Method takes 3 input arguments: the view, code for the key was pressed,
         * and a key event.
         * onKey() --> handelKeyEvent() capture and process the arguments
         * A lambda expreession
         */
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handelKeyEvent(view, keyCode)
        }
    }

    /**
     * Calculate how much tip do the user needs to pay
     * INPUT: cost of service, percentage, round up option
     * OUTPUT: total cost in text
     */
    private fun calculateTip(){
        //obtain the text from the UI input box
        //from editable to static string
        val stringInTextField =  binding.costOfServiceEditText.text.toString()
        //check the input value is not null value
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null||cost == 0.0){
            displayTip(0.0)
            return
        }
        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId){
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
        // var => chageble
        var tip = tipPercentage * cost

        if(binding.roundUpSwitch.isChecked){
            tip = kotlin.math.ceil(tip)
        }
        //format the tip when the tip displays in different currency.
        displayTip(tip)
    }

    //display tip whenever the input is valid or not
    private fun displayTip (tip:Double){
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    //Hide the soft keyboard when users finish input value in textfiled
    private fun handelKeyEvent(view: View, keyCode: Int):Boolean{
        //when the keyCode iput parameter is equal to KeyEvent.KEYCODE_ENTE
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            //Hide the soft keyboard
            val inputMethodManager =
                getSystemService (Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            //Hiding the keyboard is handeled
            return true
        }
        return false
    }
}