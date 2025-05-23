package edu.obya.kotlin.hospital.care

import kotlin.random.Random.Default.nextInt
import kotlin.random.Random.Default.nextLong

class MiracleMaker(private val trigger: () -> Boolean) {

    companion object {
        fun sure() = MiracleMaker { true }
        fun never() = MiracleMaker { false }
        fun coinFlip() = MiracleMaker { nextInt(from = 0, until = 2) > 0 }
        fun oneEvery(magnitude: Long) = MiracleMaker { nextLong(from = 0, until = magnitude) == 0L }
    }

    fun ask(): Boolean = trigger()
}
