import java.io.File
import java.lang.IllegalStateException
import java.util.*

fun main(args: Array<String>) {

    //val res = tryAllPhasesB()

    println("------")


    val cpu = BigComputer("input9")
    cpu.input.add(2)
    cpu.runToEnd()
    cpu.output.forEach { println(it) }

}


// Answer 86025

class BigComputer(filename: String) {
    val memory: MutableList<Long> = File(filename).readLines().first().split(",").map { it.toLong() }.toMutableList().apply { addAll(MutableList(10000){0L}) }

    var pointer = 0
    var relativeOffset = 0

    val input: Queue<Long> = ArrayDeque<Long>()
    val output: Queue<Long> = ArrayDeque<Long>()

    var running = true

    fun runToEnd() {
        while (running) {
            step()
        }
    }

    fun runABit() {
        while (running && output.isEmpty()) {
            step()
        }
    }

    fun step() {
        val operationRaw = memory[pointer++]
        val operation = operationRaw % 100
        val immediate1 = operationRaw / 100 % 10 == 1L
        val relative1 = operationRaw / 100 % 10 == 2L
        val immediate2 = operationRaw / 1000 % 10 == 1L
        val relative2 = operationRaw / 1000 % 10 == 2L
        val immediate3 = operationRaw / 10000 % 10 == 1L
        val relative3 = operationRaw / 10000 % 10 == 2L


        if (immediate3) TODO()
        //if (relative3) TODO()


        when (operation) {
            1L -> {
                val a = popValue(immediate1, relative1)
                val b = popValue(immediate2, relative2)
                val outPtr = memory[pointer++]
                memory[memAddress(immediate3, relative3, outPtr)] = a + b
            }
            2L -> {
                val a = popValue(immediate1, relative1)
                val b = popValue(immediate2, relative2)
                val outPtr = memory[pointer++]
                memory[memAddress(immediate3, relative3, outPtr)] = a * b
            }
            3L -> { // user input
                //if (relative1) TODO()
                val outPtr = if (relative1) memory[pointer++] + relativeOffset else memory[pointer++]
                memory[outPtr.toInt()] = input.poll()
                //userInput.removeAt(0)

            }
            4L -> { // user output
                val outVal = popValue(immediate1, relative1)
                //println(outVal)
                output.add(outVal)
            }
            5L-> { // Jump if true
                val a = popValue(immediate1, relative1)
                val b = popValue(immediate2, relative2)
                if (a != 0L) {
                    pointer = b.toInt()
                }
            }
            6L -> { // Jump if false
                val a = popValue(immediate1, relative1)
                val b = popValue(immediate2, relative2)
                if (a == 0L) {
                    pointer = b.toInt()
                }
            }
            7L -> { // less than
                if (immediate3) TODO()
                //if (relative3) TODO()
                val a = popValue(immediate1, relative1)
                val b = popValue(immediate2, relative2)
                if (a < b) {
                    memory[memory[pointer++].toInt() + if (relative3) relativeOffset else 0] = 1
                } else {
                    memory[memory[pointer++].toInt() + if (relative3) relativeOffset else 0] = 0
                }
            }
            8L -> { // equals
                if (immediate3) TODO()
                //if (relative3) TODO()
                val a = popValue(immediate1, relative1)
                val b = popValue(immediate2, relative2)
                if (a == b) {
                    memory[memory[pointer++].toInt() + if (relative3) relativeOffset else 0] = 1
                } else {
                    memory[memory[pointer++].toInt() + if (relative3) relativeOffset else 0] = 0
                }
            }
            9L -> {
                val a = popValue(immediate1, relative1)
                relativeOffset += a.toInt()
            }
            99L -> {
                running = false
            }
            else -> {
                println("Unknown operation $operation")
                throw IllegalStateException()
            }
        }
    }

    fun memAddress(immediate: Boolean, relative: Boolean, input: Long): Int {
        return when {
            immediate -> TODO()
            relative -> input.toInt() + relativeOffset
            else -> input.toInt()
        }
    }

    fun popValue(immediate: Boolean, relative: Boolean) : Long {
        return when {
            immediate -> memory[pointer++]
            relative -> memory[memory[pointer++].toInt() + relativeOffset]
            else -> memory[memory[pointer++].toInt()]
        }
    }
}
