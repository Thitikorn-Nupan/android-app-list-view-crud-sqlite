package com.kotlin.ttknpdev.service

// service layer
interface CrudEntity<T> {
    fun create(entity:T) : Boolean
    fun reads() : ArrayList<String>
    fun read(id : Int) : T
    fun delete(id : Int) : Boolean
    fun update( entity:T) :Boolean
    fun myScript()
}