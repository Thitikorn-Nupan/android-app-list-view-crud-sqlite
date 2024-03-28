package com.kotlin.ttknpdev.controller

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import com.kotlin.ttknpdev.R
import com.kotlin.ttknpdev.connection.SQLiteConnect
import com.kotlin.ttknpdev.controller.contents.DetailActivity
import com.kotlin.ttknpdev.controller.contents.FormActivity
import com.kotlin.ttknpdev.entity.Employee

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener, DialogInterface.OnClickListener {

    private val TAG = "CRUD"
    private var id: Int? = null
    private var crud = SQLiteConnect(this)

    private lateinit var listView: ListView // basic array adapter
    private lateinit var intentForChanging: Intent

    private fun initialMainActivityWidget() {
        listView = findViewById(R.id.listView)
    }

    private fun loadEmployees() {
        val employees = crud.reads()
        val arrayAdapter = ArrayAdapter(this, R.layout.item_employee, R.id.textView, employees) // **  Using my layout item
        listView.adapter = arrayAdapter
    }

    private fun alertOptions() {

        val alertDialogObject = MaterialAlertDialogBuilder(this) // design better than  AlertDialog.Builder(this)
        alertDialogObject.setMessage("What do you want to do next ?")
        alertDialogObject.setTitle("Warning!!")
        // alertDialogObject.setCancelable(false) // can not press anywhere only Negative or Positive button
        // NegativeButton -1, PositiveButton -2
        alertDialogObject.setNegativeButton("Delete it.", this::onClick)
        alertDialogObject.setPositiveButton("See more.", this::onClick)
        val alertDialog = alertDialogObject.create()
        alertDialog.show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_reads)
        initialMainActivityWidget()
        // crud.myScript()
        loadEmployees()
        listView.onItemClickListener = this // like you write listView.setOnItemClickListener(this)
    }

    override fun onClick(p0: DialogInterface?, p1: Int) {
        if (p1 == -1) {
            intentForChanging = Intent(this, DetailActivity::class.java)
            intentForChanging.putExtra("id", id.toString())
            startActivity(intentForChanging)
        } else if (p1 == -2) {
            crud.delete(id!!)
            loadEmployees() // after delete I need to load list again
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val employee =
            parent!!.getItemAtPosition(position).toString() // ID 2 , BRAND Apple , MODEL 14 PRO MAX , PRICE 26000
        val id = employee.substring(6, 8) // Assume it was 2 length
        this.id = id.trim().toInt() // delete null space and convert to int
        alertOptions()
    }

    // 2 overrides work for my menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.form_creating_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MENU", "${item.itemId == R.id.itemCreating}") // always return true because I have once option
        intentForChanging = Intent(this, FormActivity::class.java)
        startActivity(intentForChanging)
        return super.onOptionsItemSelected(item)
    }

    //  I need to restart after all query
    override fun onRestart() { // this override func works when class open mean when u do something then u back to the activity this class worked
        recreate() // To refresh an activity, you can call recreate()
        super.onRestart()
    }

}