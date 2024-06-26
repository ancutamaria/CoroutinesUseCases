package com.am.coroutinesusecases.playground.fundamentals

fun main(){
    println("main starts")
    routine(1, 500)
    routine(2, 3000)
    println("main ended")
}

fun routine(number: Int, delay: Long){
    println("Routine $number starts work")
    Thread.sleep(delay)
    println("Routine $number finished work")
}