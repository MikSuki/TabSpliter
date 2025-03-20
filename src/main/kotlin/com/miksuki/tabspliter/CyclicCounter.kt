package com.miksuki.tabspliter

class CyclicCounter(maxSize: Int) {
    private val min = 0
    private val max = maxSize - 1
    private var current = 0

    fun get() = current

    fun add(){
        if(++current > max)
            current = min
    }
}
