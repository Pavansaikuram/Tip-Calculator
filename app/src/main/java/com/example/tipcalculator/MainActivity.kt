package com.example.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat


private const val default=15


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekBar.progress= default
        percentage.text="$default%"
        editText.setText("1")
       //All the values of split, tip percentage is set to default

        seekBar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                percentage.text="$progress%"
                    Calculatetipandtotal()
                // Change is seekbar is identified and writing it to tip percentage textview
                }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
//we do need onstart and onstop tracking as abstract functions to track the progress
        editText2.addTextChangedListener(object: TextWatcher
        {
            override fun afterTextChanged(s: Editable?)
            {

                 if ("$s".toString().contains(".") && "$s".toString().substring("$s".indexOf(".")+1).length > 2)
                 {
                            editText2.setText("$s".substring(0, "$s".length-1))
                            editText2.setSelection(editText2.getText().length)
                     editText2.setError("Only two decimals are allowed")
                     //to limit the user to two decimals
                     //https://stackoverflow.com/questions/5357455/limit-decimal-places-in-android-edittext
                        }
                else if("$s"=="")
                     editText2.setError("Enter a value")
             Calculatetipandtotal()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })

        editText.addTextChangedListener (object : TextWatcher
        {
            override fun afterTextChanged(s: Editable?) {


                if("$s"=="0")
                {
                    editText.setText("")
                    editText.setSelection(editText.getText().length)
                    editText.setError("Minimum value is 1")
                }
                else if("$s"=="")
                {
                    editText.setError("Minimum value is 1")
                }

               Calculatetipandtotal()
                //calculating tip and total after entering the split value
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
}
        })
        // Reset button
        button.setOnClickListener({
            editText2.setText("0")
            editText.setText("1")
            seekBar.progress= default
            percentage.text="$default%"
        })

    }
      //Extracting tip and calculating total amount
    private fun Calculatetipandtotal() {
          //to remove ambiguous when number is empty
        if(editText2.text.toString().isEmpty()){
                tipvalue.text="0.0"
            totalvalue.text="0.0"
            perheadamount.text ="0.0"
            return
            }
            val billamount = editText2.text.toString().toDouble()
            val giventip = seekBar.progress
            val tip = billamount * giventip / 100
            val totalbill = billamount + tip
            tipvalue.text = "%.2f".format(tip)
            totalvalue.text = "%.2f".format(totalbill)
          //calculating the individual amount by adding split value
        if(editText.text.isNotEmpty())
        {
            val splitbill=editText.text.toString().toInt()
            val aftersplit=totalbill/splitbill
            perheadamount.text="%.2f".format(aftersplit)
        }
    }
}
