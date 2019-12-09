package day8

import java.io.File

fun main(args: Array<String>) {
    var input = File("input8").readLines().first()

    val layers = mutableListOf<String>()
    while (input.isNotEmpty()) {
        val layer = input.take(150)
        layers += layer

        input = input.substring(150)
    }

    layers.minBy { it.count { it == '0' } }!!.let { it.count { it == '1' } * it.count { it == '2' } }.let { println(it) }


    val out = layers.fold(layers.first()) { a, b ->
        (0 until 150).map {index ->
            if (a[index] == '2') {
                b[index]
            } else {
                a[index]
            }
        }.joinToString("")
    }

    // CJZHR

    (0..5).forEach {
        println(out.substring(it * 25))
    }

}