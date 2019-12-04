package day2

import java.io.File
import java.lang.IllegalStateException

fun main(args: Array<String>) {

    val target = 19690720

    (0..99).forEach { noun ->
        (0..99).forEach { verb ->
            val input = File("input2_1").readLines().first().split(",").map { it.toInt() }.toMutableList()
            input[1] = noun
            input[2] = verb

            var pointer = 0

            var running = true
            while (running) {
                val operation = input[pointer++]
                val a = input[pointer++]
                val b = input[pointer++]
                val out = input[pointer++]

                when (operation) {
                    1 -> input[out] = input[a] + input[b]
                    2 -> input[out] = input[a] * input[b]
                    99 -> {
                        if (input[0] == target) {
                            println(noun * 100 + verb)
                        }

                        running = false
                    }
                    else -> throw IllegalStateException()
                }
            }
        }
    }
}
