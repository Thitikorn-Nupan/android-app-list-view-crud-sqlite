package com.kotlin.ttknpdev.connection

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.kotlin.ttknpdev.constants.InfoSQLite
import com.kotlin.ttknpdev.entity.Employee
import com.kotlin.ttknpdev.service.CrudEntity

// SQLiteOpenHelper(...) class helps you to build database. and you can CRUD it also.
class SQLiteConnect(context: Context) : CrudEntity<Employee>, SQLiteOpenHelper(context, InfoSQLite.DATABASE_NAME, null, InfoSQLite.DATABASE_VERSION) {

    private val TAG = "SQLite"
    private lateinit var sqLiteDatabase: SQLiteDatabase

    override fun myScript() {
        // sqLiteDatabase = writableDatabase
        // sqLiteDatabase.execSQL(InfoSQLite.BEFORE_CREATE_TABLE)
        // sqLiteDatabase.execSQL(InfoSQLite.CREATE_TABLE)
        // sqLiteDatabase.execSQL("delete from employees where  eid = 12 ")
        // sqLiteDatabase.execSQL("delete from employees where  eid = 13 ")
        // sqLiteDatabase.execSQL("delete from employees where  eid = 14 ")
        // sqLiteDatabase.close()
    }

    // CrudEntity
    override fun reads(): ArrayList<String> {
        sqLiteDatabase = writableDatabase

        val employees = ArrayList<String>()
        // working seems select * from <Table name>
        val cursor = sqLiteDatabase.query(
            InfoSQLite.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        // move to first position if cursor it's not null (?.)
        cursor?.moveToFirst()
        //
        while (!cursor.isAfterLast) {
            employees.add(
                "EID : ${cursor.getInt(0)} , " +
                        "FULLNAME : ${cursor.getString(1)} , " +
                        "AGE : ${cursor.getInt(2)} ,\n" +
                        "GENDER : ${cursor.getString(3)} ,\n" +
                        "POSITION : ${cursor.getString(4)}"
            )
            cursor.moveToNext() // ** don't forget
        }

        sqLiteDatabase.close() // close all way

        return employees

    }

    override fun read(id: Int): Employee {

        sqLiteDatabase = writableDatabase

        val cursor = sqLiteDatabase.query(
            InfoSQLite.TABLE_NAME,
            null,
            "${InfoSQLite.ID_COL} = ?",
            arrayOf("$id"),
            null,
            null,
            null
        ) // working seems select * from where ? id = ?

        cursor?.moveToFirst()

        sqLiteDatabase.close() // close all way

        return Employee(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getInt(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getDouble(5)
        )
    }

    override fun delete(id: Int): Boolean {
        sqLiteDatabase = writableDatabase

        val row = sqLiteDatabase.delete(InfoSQLite.TABLE_NAME,"${InfoSQLite.ID_COL} = ?", arrayOf("$id"))

        sqLiteDatabase.close()

        if (row != 0) {
            Log.d(TAG, "Deleted")
            return true
        }

        return false
    }

    override fun update(entity: Employee): Boolean {

        sqLiteDatabase = writableDatabase

        val values = ContentValues()
        values.put(InfoSQLite.ID_COL, entity.eid)
        values.put(InfoSQLite.FULLNAME_COl, entity.fullname)
        values.put(InfoSQLite.AGE_COL, entity.age)
        values.put(InfoSQLite.GENDER_COL, entity.gender)
        values.put(InfoSQLite.POSITION_COL, entity.position)
        values.put(InfoSQLite.SALARY_COL, entity.salary)

        Log.d(TAG, "$values")

        val row = sqLiteDatabase.update(InfoSQLite.TABLE_NAME, values, "${InfoSQLite.ID_COL} = ?", arrayOf("${entity.eid}"))

        // Log.d(TAG, "$row")

        sqLiteDatabase.close()

        if (row != 0) {
            Log.d(TAG, "Updated")
            return true
        }

        return false

    }

    override fun create(entity: Employee): Boolean {
        // *** (First called writableDatabase func for taking to access database) Before performing any database operations like insert, update, delete records in a table, first open the database connection by calling getWritableDatabase() method
        sqLiteDatabase = writableDatabase

        val values = ContentValues()
        values.put(InfoSQLite.FULLNAME_COl, entity.fullname)
        values.put(InfoSQLite.AGE_COL, entity.age)
        values.put(InfoSQLite.GENDER_COL, entity.gender)
        values.put(InfoSQLite.POSITION_COL, entity.position)
        values.put(InfoSQLite.SALARY_COL, entity.salary)
        // query insert and passed values

        val row = sqLiteDatabase.insert(InfoSQLite.TABLE_NAME, null, values)
        // close a database connection
        sqLiteDatabase.close()

        if (row != 0L) {
            Log.d(TAG, "Created")
            return true
        }
        return false
    }

    // SQLiteOpenHelper
    /*
    When It’s called.
    there is no database and the app needs one. It passes us a SQLiteDatabase object,
    pointing to a newly-created database, that we can populate with tables and initial data.
    */
    override fun onCreate(p0: SQLiteDatabase?) {
        val SCRIPT_CREATE_TABLE = InfoSQLite.CREATE_TABLE
        Log.d(TAG, "Command you executed $SCRIPT_CREATE_TABLE")
        p0?.execSQL(SCRIPT_CREATE_TABLE)  // (calling sqlite) execSQL() method for executing our command sql
    }

    /*
    When It’s called. ** the schema version we need does not match the schema version of the database,
    It passes us a SQLiteDatabase object and the old and new version numbers.
    */
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val SCRIPT_BEFORE_CREATE_TABLE = InfoSQLite.BEFORE_CREATE_TABLE
        p0?.execSQL(SCRIPT_BEFORE_CREATE_TABLE)
        Log.d(TAG, "Upgrade database from $p1 to $p2 versions")
        onCreate(p0) // ** relation onUpgrade(...) and onCreate(...)
    }


}