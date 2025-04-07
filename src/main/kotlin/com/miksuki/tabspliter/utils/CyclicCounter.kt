package com.miksuki.tabspliter.utils

class CyclicCounter(
    maxSize: Int,
) {
    private val min = 0
    private val max = maxSize - 1
    private var current = 0

    fun get() = current

    fun add() {
        if (++current > max) {
            current = min
        }
    }

    fun sub() {
        if (--current < 0) {
            current = max
        }
    }
}
