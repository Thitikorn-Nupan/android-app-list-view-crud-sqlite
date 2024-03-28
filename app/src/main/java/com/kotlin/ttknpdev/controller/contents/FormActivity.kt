package com.kotlin.ttknpdev.controller.contents

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.Slider
import com.kotlin.ttknpdev.R
import com.kotlin.ttknpdev.connection.SQLiteConnect
import com.kotlin.ttknpdev.entity.Employee

// use Slider instead Seekbar was good for me
// Concept its same
class FormActivity : AppCompatActivity(), Slider.OnChangeListener, View.OnClickListener ,  DialogInterface.OnClickListener  {
    private var salary : String = ""
    private var gender : String = ""
    private lateinit var slider: Slider
    private lateinit var radioGroup: RadioGroup
    private lateinit var textViewValue: TextView
    private lateinit var editTextEmployeeFullname: EditText
    private lateinit var editTextEmployeeAge: EditText
    private lateinit var editTextEmployeePosition: EditText
    private lateinit var button: MaterialButton // MaterialButton class is beautiful more than Button class

    private var crud = SQLiteConnect(this)

    private fun initialFormActivityWidget() {
        slider = findViewById(R.id.slider)
        radioGroup = findViewById(R.id.radioGroup)
        textViewValue = findViewById(R.id.textViewValue)
        editTextEmployeeFullname = findViewById(R.id.editTextEmployeeFullname)
        editTextEmployeeAge = findViewById(R.id.editTextEmployeeAge)
        editTextEmployeePosition = findViewById(R.id.editTextEmployeePosition)
        button = findViewById(R.id.button)
    }

    private fun validateInput(
        fullname: String,
        age: String,
        position: String,
        gender: String,
        salary: String
    ): Boolean {
        // First input has no empty
        return fullname.trim().isNotEmpty() && age.trim().isNotEmpty() && position.trim().isNotEmpty() && gender.trim().isNotEmpty() && salary.trim().isNotEmpty()
    }

    private fun alertFailed() {

        val alertDialogObject = MaterialAlertDialogBuilder(this)
        alertDialogObject.setMessage("All input shouldn't be empty")
        alertDialogObject.setTitle("Warning!!")
        alertDialogObject.setCancelable(false) // can not press anywhere only Negative or Positive button
        alertDialogObject.setNegativeButton("Got it.", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                p0!!.cancel()
            }
        })
        val alertDialog = alertDialogObject.create()
        alertDialog.show()

    }

    private fun alertOptions() {

        val alertDialogObject = MaterialAlertDialogBuilder(this) // design better than  AlertDialog.Builder(this)
        alertDialogObject.setMessage("Created and What do you want to do next ?")
        alertDialogObject.setTitle("Warning!!")
        alertDialogObject.setCancelable(false) // can not press anywhere only Negative or Positive button
        // NegativeButton -2 , PositiveButton -1
        alertDialogObject.setNegativeButton("See at all.", this::onClick)
        alertDialogObject.setPositiveButton("Keep continue.", this::onClick)
        val alertDialog = alertDialogObject.create()
        alertDialog.show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_actiity_create)
        initialFormActivityWidget()
        slider.addOnChangeListener(this)
        button.setOnClickListener(this)

    }

    override fun onValueChange(p0: Slider, p1: Float, p2: Boolean) { // when moving (active) this func works
        // Log.d("ON VALUE CHANGED","${p0.value} $p1") // same result
        textViewValue.text = "$p1 ฿"
        salary = p1.toString()
    }

    override fun onClick(p0: View?) {

        val fullname = editTextEmployeeFullname.text.toString()
        val age = editTextEmployeeAge.text.toString()
        val position = editTextEmployeePosition.text.toString()

        // Log.d("ON CLICKED", "${ radioGroup.checkedRadioButtonId == R.id.radioButtonFemale }")
        if (radioGroup.checkedRadioButtonId == R.id.radioButtonFemale) {

            gender = "Female"

        } else if (radioGroup.checkedRadioButtonId == R.id.radioButtonMale) {

            gender = "Male"

        } // another case Gender was ""

        val checkInput = validateInput(fullname, age, position, gender, salary)

        if (checkInput) {

            val employee = Employee(0,fullname,age.toInt(),gender,position,salary.toDouble())

            val checkCreate = crud.create(employee)

            if (checkCreate) {

                alertOptions()

            }

        } else {

            alertFailed()

        }
    }

    override fun onClick(p0: DialogInterface?, p1: Int) {

        if (p1 == -2) {

            finish()

        } else if (p1 == -1) {

            p0?.cancel()
            // clear input
            editTextEmployeeFullname.setText("")
            editTextEmployeeAge.setText("")
            radioGroup.clearCheck()
            editTextEmployeePosition.setText("")
            slider.value = 0.0F

        }

    }
}