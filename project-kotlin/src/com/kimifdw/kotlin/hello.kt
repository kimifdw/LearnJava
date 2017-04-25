package com.kimifdw.kotlin

/**
 * Created by kimiyu on 2017/1/23.
 */
fun getHelloString(): String {
    return "Hello,world!"
}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        return
    }

    println(getHelloString())
}
