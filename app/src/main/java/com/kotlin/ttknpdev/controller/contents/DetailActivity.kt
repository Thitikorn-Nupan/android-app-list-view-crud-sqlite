package com.kotlin.ttknpdev.controller.contents

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kotlin.ttknpdev.R
import com.kotlin.ttknpdev.connection.SQLiteConnect
import com.kotlin.ttknpdev.entity.Employee

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private var crud = SQLiteConnect(this)

    private lateinit var intentMainActivity: Intent

    private lateinit var editTextEmployeeId: EditText
    private lateinit var editTextEmployeeFullname: EditText
    private lateinit var editTextEmployeeAge: EditText
    private lateinit var editTextEmployeePosition: EditText
    private lateinit var editTextEmployeeGender: EditText
    private lateinit var editTextEmployeeSalary: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var button: MaterialButton

    private var employee: Employee? = null

    private fun initialDetailActivityWidget() {
        editTextEmployeeId = findViewById(R.id.editTextEmployeeId)
        editTextEmployeeFullname = findViewById(R.id.editTextEmployeeFullname)
        editTextEmployeeAge = findViewById(R.id.editTextEmployeeAge)
        editTextEmployeePosition = findViewById(R.id.editTextEmployeePosition)
        editTextEmployeeGender = findViewById(R.id.editTextEmployeeGender)
        editTextEmployeeSalary = findViewById(R.id.editTextEmployeeSalary)
        checkBox = findViewById(R.id.checkBox)
        button = findViewById(R.id.button)
    }

    private fun readAndSetDefaultEditText(id: Int) {
        employee = crud.read(id)
        // If not Type string used "${your data}" will be good
        editTextEmployeeId.setText("${employee!!.eid}")
        editTextEmployeeFullname.setText(employee!!.fullname)
        editTextEmployeeAge.setText("${employee!!.age}")
        editTextEmployeePosition.setText(employee!!.position)
        editTextEmployeeGender.setText(employee!!.gender)
        editTextEmployeeSalary.setText("${employee!!.salary}")
    }

    private fun enableEditText() {
        editTextEmployeeFullname.isEnabled = true
        editTextEmployeeAge.isEnabled = true
        editTextEmployeePosition.isEnabled = true
        editTextEmployeeGender.isEnabled = true
        editTextEmployeeSalary.isEnabled = true
    }

    private fun disableEditText() {
        editTextEmployeeFullname.isEnabled = false
        editTextEmployeeAge.isEnabled = false
        editTextEmployeePosition.isEnabled = false
        editTextEmployeeGender.isEnabled = false
        editTextEmployeeSalary.isEnabled = false
    }

    private fun validateInput(
        fullname: String,
        age: String,
        position: String,
        gender: String,
        salary: String
    ): Boolean {
        // First input has no empty
        if (fullname.trim().isNotEmpty() && age.trim().isNotEmpty() && position.trim().isNotEmpty() && gender.trim()
                .isNotEmpty() && salary.trim().isNotEmpty()
        ) {
            // Second input has a change
            if (fullname != employee!!.fullname || age.toInt() != employee!!.age || position != employee!!.position || gender != employee!!.gender || salary.toDouble() != employee!!.salary) {
                // Log.d("VALIDATE INPUT", "${employee!!.eid} has a change")
                return true
            }
            return false
        }
        return false
    }

    private fun alertFailed() {

        val alertDialogObject = MaterialAlertDialogBuilder(this)
        alertDialogObject.setMessage("Employee id ${employee!!.eid} hasn't changed")
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity_update)
        initialDetailActivityWidget()
        intentMainActivity = intent // get intent object from first activity
        // If you passed Type Int must use getIntExtra(...) *** getStringExtra() method is for getting the data(key) that is sent by the above method. *** use the same type you putExtra(...,...)
        val id = intentMainActivity.getStringExtra("id").toString()
        readAndSetDefaultEditText(id.toInt())
        checkBox.setOnClickListener(this)
        button.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            // need to check the id user chooses (Cause setOnClickListener() got 2)
            checkBox.id -> {
                // then check user click or not
                if (checkBox.isChecked) {
                    // Log.d("CLICKED","can edit")
                    enableEditText()
                } else {
                    // Log.d("CLICKED","can not edit")
                    disableEditText()
                }
            } // ** check box

            button.id -> {

                val fullname = editTextEmployeeFullname.text.toString()
                val age = editTextEmployeeAge.text.toString()
                val position = editTextEmployeePosition.text.toString()
                val gender = editTextEmployeeGender.text.toString()
                val salary = editTextEmployeeSalary.text.toString()
                val checkInput = validateInput(fullname, age, position, gender, salary)

                if (checkInput) {

                    Log.d("ON CLICKED", "Before update properties $employee")

                    // update my employee object class
                    employee!!.fullname = fullname
                    employee!!.age = age.toInt()
                    employee!!.gender = gender
                    employee!!.position = position
                    employee!!.salary = salary.toDouble()

                    Log.d("ON CLICKED", "After update properties $employee")
                    val checkUpdate = crud.update(employee!!)

                    if (checkUpdate) {
                        finish()
                    }

                } else {

                    alertFailed()

                }

            } // button update

        }
    }

}