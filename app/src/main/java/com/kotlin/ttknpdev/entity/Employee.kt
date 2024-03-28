package com.kotlin.ttknpdev.entity

/*
    Data (Keyword) class compiler automatically derives the following members like.
    .equals()/.hashCode() pair.
    .toString() of the form "User(name=John, age=42)".
    .componentN() functions corresponding to the properties in their order of declaration.
    .copy() function (see below).
    This case I am not using it
*/
// And this is seemed correct for using getter & setter
// Remember. The default getter and setter is a familiar pattern we see in Java, but in Kotlin, we don’t have to create a private backing field for the property. */
/* class Employee(
    private var i: Int,
    private var f: String,
    private var a: Int,
    private var g: String,
    private var p: String,
    private var s: Double
) { // default properties

    var eid : Int = this.i // try to understand
        // eid = i
        get() {
            return field // get eid = i
        }
        set(value) {
            field = value // set value = eid = i
        }

    var fullname: String = this.f
        get() {
            return field
        }
        set(value) {
            field = value
        }

    var age: Int = this.a
        get() {
            return field
        }
        set(value) {
            field = value
        }

    var gender: String = this.g
        get() {
            return field
        }
        set(value) {
            field = value
        }

    var position: String = this.p
        get() {
            return field
        }
        set(value) {
            field = value
        }

    var salary : Double = this.s
        get() {
            return field
        }
        set(value) {
            field = value
        }

    override fun toString(): String {
        return "Employee(eid=$eid, fullname='$fullname', age=$age, gender='$gender', position='$position', salary=$salary)"
    }

}
*/
// and this is corrected way to use
class Employee {
    constructor(eid : Int,fullname : String , age : Int, gender : String , position: String,salary: Double) {
        this.eid = eid
        this.fullname = fullname
        this.gender = gender
        this.age = age
        this.position = position
        this.salary = salary
    }
    // The default getter and setter is a familiar pattern we see in Java, but in Kotlin, we don’t have to create a private backing field for the property.
    var eid : Int
        get() = field
        set(value) {
            field = value
        }
    var fullname : String
        get() = field
        set(value) {
            field = value
        }
    var gender : String
        get() = field
        set(value) {
            field = value
        }
    var age : Int
        get() = field
        set(value) {
            field = value
        }

    var position : String
        get() = field
        set(value) {
            field = value
        }

    var salary : Double
        get() = field
        set(value) {
            field = value
        }

    override fun toString(): String {
        return "Employee(eid=$eid, fullname='$fullname', gender='$gender', age=$age, position='$position', salary=$salary)"
    }

}
