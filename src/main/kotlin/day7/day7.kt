import java.io.File
import java.lang.IllegalStateException

fun main(args: Array<String>) {

    val res = tryAllPhases()

    println("------\n$res")
}

fun tryAllPhases(): Int {


    val perms = emptyList<Int>().permutations(mutableListOf<Int>(0, 1, 2, 3, 4))

    return perms.map { phases ->
        val out = thrusterSeries("input7", phases)
        out
    }.max()!!


}

fun List<Int>.permutations(remaining: List<Int>): List<List<Int>> {
    return if (remaining.size == 0) {
        listOf(this)// + remaining.first())
    } else {
        remaining.flatMap {
            val options = listOf(it).permutations(remaining - it)

            options.map {
                this + it
            }
        }
    }
}

fun thrusterSeries(filename: String, phases: List<Int>): Int {


    return phases.fold(0) { lastOut, phase ->
        val input = mutableListOf<Int>(phase, lastOut)

        thrusterProgram(filename, input).first()
    }
}

fun thrusterProgram(filename: String, userInputList: MutableList<Int> = mutableListOf(5)): List<Int> {

    File(filename).readLines()

    val input = File(filename).readLines().first().split(",").map { it.toInt() }.toMutableList()

    val output = mutableListOf<Int>()

    var pointer = 0

    var running = true
    while (running) {
        val operationRaw = input[pointer++]
        val operation = operationRaw % 100
        val immediate1 = operationRaw / 100 % 10 == 1
        val immediate2 = operationRaw / 1000 % 100 == 1
        val immediate3 = operationRaw / 10000 % 1000 == 1

        when (operation) {
            1 -> {
                val a = if (immediate1) input[pointer++] else input[input[pointer++]]
                val b = if (immediate2) input[pointer++] else input[input[pointer++]]
                val outPtr = input[pointer++]
                input[outPtr] = a + b
            }
            2 -> {
                val a = if (immediate1) input[pointer++] else input[input[pointer++]]
                val b = if (immediate2) input[pointer++] else input[input[pointer++]]
                val outPtr = input[pointer++]
                input[outPtr] = a * b
            }
            3 -> { // user memory
                val outPtr = input[pointer++]
                input[outPtr] = userInputList.removeAt(0)
                //userInput.removeAt(0)
            }
            4 -> { // user output
                val outVal = input[input[pointer++]]
                println(outVal)
                output.add(outVal)
            }
            5 -> { // Jump if true
                val a = if (immediate1) input[pointer++] else input[input[pointer++]]
                val b = if (immediate2) input[pointer++] else input[input[pointer++]]
                if (a != 0) {
                    pointer = b
                }
            }
            6 -> { // Jump if false
                val a = if (immediate1) input[pointer++] else input[input[pointer++]]
                val b = if (immediate2) input[pointer++] else input[input[pointer++]]
                if (a == 0) {
                    pointer = b
                }
            }
            7 -> { // less than
                val a = if (immediate1) input[pointer++] else input[input[pointer++]]
                val b = if (immediate2) input[pointer++] else input[input[pointer++]]
                if (a < b) {
                    input[input[pointer++]] = 1
                } else {
                    input[input[pointer++]] = 0
                }
            }
            8 -> { // equals
                val a = if (immediate1) input[pointer++] else input[input[pointer++]]
                val b = if (immediate2) input[pointer++] else input[input[pointer++]]
                if (a == b) {
                    input[input[pointer++]] = 1
                } else {
                    input[input[pointer++]] = 0
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
    return output
}
