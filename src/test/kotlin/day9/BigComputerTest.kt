package day9

import org.junit.Test

import org.junit.Assert.*
import BigComputer
import java.io.File

class BigComputerTest {

    @Test
    fun setPointer() {
        val cpu = BigComputer("input9_t1")
        cpu.runToEnd()
        val expect = File("input9_t1").readLines().first().split(",").toString()
        val out = ArrayList(cpu.output).toString()
        assertEquals(expect, out)

    }
    @Test
    fun bigValues() {
        val cpu = BigComputer("input9_t2")
        cpu.runToEnd()
        cpu.output.forEach { println(it) }

    }
    @Test
    fun hugeAdd() {
        val cpu = BigComputer("input9_t3")
        cpu.runToEnd()
        cpu.output.forEach { println(it) }

    }
    @Test
    fun runOld() {
        val cpu = BigComputer("input5_t5")
        cpu.input.add(15)
        cpu.runToEnd()
        cpu.output.forEach { println(it) }

    }
}