package com.kotlin.ttknpdev.constants

object InfoSQLite {
    // here we have defined variables for our database
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "enterprise"
    const val TABLE_NAME = "employees"
    const val ID_COL = "eid"
    const val FULLNAME_COl = "fullname"
    const val AGE_COL = "age"
    const val GENDER_COL = "gender"
    const val POSITION_COL = "position"
    const val SALARY_COL = "salary"
    const val BEFORE_CREATE_TABLE = "drop table if exists $TABLE_NAME"
    // SQLite implicitly  creates a column named rowid and i change it to eid
    // ** AUTOINCREMENT is only allowed on an INTEGER PRIMARY KEY
    // syntax like sql
    const val CREATE_TABLE = "create table $TABLE_NAME (" +
            "$ID_COL integer primary key autoincrement," +
            "$FULLNAME_COl varchar(30) not null," +
            "$AGE_COL integer not null," +
            "$GENDER_COL varchar(6) not null," +
            "$POSITION_COL varchar(50) not null," +
            "$SALARY_COL decimal(10,2) not null" +
            ")"
}