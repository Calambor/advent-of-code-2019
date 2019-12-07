import java.io.File
import java.lang.IllegalStateException
import java.util.*

fun main(args: Array<String>) {

    val res = tryAllPhasesB()

    println("------\n$res")
}

fun tryAllPhasesB(): Int {
    val perms = emptyList<Int>().permutations(mutableListOf<Int>(9, 8, 7, 6, 5))

    return perms.map { phases ->
        val out = thrusterFeedbackSeries("input7", phases)
        out
    }.max()!!
}

fun thrusterFeedbackSeries(filename: String, phases: List<Int>): Int {

    val computers = phases.map { phase ->
        Computer(filename).apply { input.add(phase) }
    }
    computers.first().input.add(0)

    while (computers.any { it.running }) {
        computers.forEachIndexed { index, computer ->
            computer.runABit()
            if (!computer.output.isEmpty()) {
                computers[(index + 1) % 5].input.add(computer.output.poll())
            }
        }
    }
    return computers.first().input.poll()
}

class Computer(filename: String) {
    val memory: MutableList<Int> = File(filename).readLines().first().split(",").map { it.toInt() }.toMutableList()

    var pointer = 0

    val input: Queue<Int> = ArrayDeque<Int>()
    val output: Queue<Int> = ArrayDeque<Int>()

    var running = true

    fun runABit() {
        while (running && output.isEmpty()) {
            step()
        }
    }

    fun step() {
        val operationRaw = memory[pointer++]
        val operation = operationRaw % 100
        val immediate1 = operationRaw / 100 % 10 == 1
        val immediate2 = operationRaw / 1000 % 100 == 1
        val immediate3 = operationRaw / 10000 % 1000 == 1

        when (operation) {
            1 -> {
                val a = if (immediate1) memory[pointer++] else memory[memory[pointer++]]
                val b = if (immediate2) memory[pointer++] else memory[memory[pointer++]]
                val outPtr = memory[pointer++]
                memory[outPtr] = a + b
            }
            2 -> {
                val a = if (immediate1) memory[pointer++] else memory[memory[pointer++]]
                val b = if (immediate2) memory[pointer++] else memory[memory[pointer++]]
                val outPtr = memory[pointer++]
                memory[outPtr] = a * b
            }
            3 -> { // user memory
                val outPtr = memory[pointer++]
                memory[outPtr] = input.poll()
                //userInput.removeAt(0)
            }
            4 -> { // user output
                val outVal = memory[memory[pointer++]]
                println(outVal)
                output.add(outVal)
            }
            5 -> { // Jump if true
                val a = if (immediate1) memory[pointer++] else memory[memory[pointer++]]
                val b = if (immediate2) memory[pointer++] else memory[memory[pointer++]]
                if (a != 0) {
                    pointer = b
                }
            }
            6 -> { // Jump if false
                val a = if (immediate1) memory[pointer++] else memory[memory[pointer++]]
                val b = if (immediate2) memory[pointer++] else memory[memory[pointer++]]
                if (a == 0) {
                    pointer = b
                }
            }
            7 -> { // less than
                val a = if (immediate1) memory[pointer++] else memory[memory[pointer++]]
                val b = if (immediate2) memory[pointer++] else memory[memory[pointer++]]
                if (a < b) {
                    memory[memory[pointer++]] = 1
                } else {
                    memory[memory[pointer++]] = 0
                }
            }
            8 -> { // equals
                val a = if (immediate1) memory[pointer++] else memory[memory[pointer++]]
                val b = if (immediate2) memory[pointer++] else memory[memory[pointer++]]
                if (a == b) {
                    memory[memory[pointer++]] = 1
                } else {
                    memory[memory[pointer++]] = 0
                }
            }
            99 -> {
                running = false
            }
            else -> {
                println("Unknown operation $operation")
                throw IllegalStateException()
            }
        }
    }
}
