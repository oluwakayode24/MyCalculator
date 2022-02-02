package khay.kotlinlearning.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private var tvInput : TextView? = null //creating the TextView

    //variables to handle the onDecimalPointBtn
    private var lastNumeric = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    // fun for the onClick used in the activity_main file
    fun onDigit(view: View){
        tvInput?.append((view as Button).text) //appending a value to the TextView
        lastNumeric = true
        lastDot = false
    }// end onDigit

    //function for the CLR button
    fun onClearBtn(view: View){
        tvInput?.text = ""
    } //end onClearBtn

    //function that takes care of the decimal point
    fun onDecimalPointBtn(view: View) {
        //need to make sure last clicked button was numeric and not a dot
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false // last entry is now a dot hence, this is now false
            lastDot = true
        }
    } //end onDecimalPointBtn

    //function to call when any operator is clicked
    fun onOperator(view: View){
        tvInput?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){ // it is the result of the lambda
                tvInput?.append((view as Button).text) //append whatever is inside the button
                lastNumeric = false
                lastDot = false
            }
        }
    } // end onOperator

    //function to handle the equal sign
    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = " "

            //try and catch error handling
            try{
                //to handle - in front of a number
                    if(tvValue.startsWith("-")){
                        prefix = "-"
                        tvValue =tvValue.substring(1)//will delete prefix at position 1 i.e -99 === 99
                    }

                //checking if there is - in the textview
                if (tvValue.contains("-")){
                    val splitValue = tvValue.split("-") //splitting the string allows operator to work

                    var one = splitValue[0]
                    var two = splitValue[1]

                    //checking if there is - prefix and using it in calculation
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString()) //using - operator
                }else if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+") //splitting the string allows operator to work

                    var one = splitValue[0]
                    var two = splitValue[1]

                    //checking if there is - prefix and using it in calculation
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString()) //using + operator
                }else if (tvValue.contains("/")){
                    val splitValue = tvValue.split("/") //splitting the string allows operator to work

                    var one = splitValue[0]
                    var two = splitValue[1]

                    //checking if there is - prefix and using it in calculation
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString()) //using / operator
                }else  if (tvValue.contains("*")){
                    val splitValue = tvValue.split("*") //splitting the string allows operator to work

                    var one = splitValue[0]
                    var two = splitValue[1]

                    //checking if there is - prefix and using it in calculation
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString()) //using * operator
                }

            } catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    fun removeZeroAfterDot(result: String) : String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length -2) //removes .0 at the end of the calculation result
        return value
    }

    // function to check if an operator has been added to the textview
    private fun isOperatorAdded(value : String) : Boolean {
        return if (value.startsWith("-",)) { //ignores the - in front of the numbers
              false
        } else {
            value.contains("/") || value.contains("+")
                    || value.contains("*")
                    || value.contains("-")
        }
    }// end isOperatorAdded
} //end onCreate