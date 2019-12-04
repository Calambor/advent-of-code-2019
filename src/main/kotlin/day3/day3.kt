package day3

import java.io.File
import kotlin.math.abs
import kotlin.math.min

val occupied: MutableMap<Pair<Int, Int>, Square> = mutableMapOf()
val intersections: MutableList<Square> = mutableListOf()

fun main(args: Array<String>) {
    val input = File("input3_1").readLines()
    val cables = input.map {
        it.split(",")
    }
    plotCable(true, cables[0])
    plotCable(false, cables[1])

    val finDist = intersections.minBy { abs(it.x) + abs(it.y) }!!
    println(finDist)
    println(abs(finDist.x) + abs(finDist.y))

    val finLen = intersections.minBy { it.la + it.lb }!!

    println(finLen.la + finLen.lb)
}

data class Square(val x: Int, val y: Int) {
    var occupiedA = false
    var occupiedB = false
    var la = Int.MAX_VALUE
    var lb = Int.MAX_VALUE
}

fun Square?.isCrossed(): Boolean {
    return this != null && occupiedA && occupiedB

}

fun plotCable(isA: Boolean, cable: List<String>) {
    var x = 0
    var y = 0

    var totl = 0

    cable.forEach {
        val dir = it.first()
        var length = it.substring(1).toInt()
        (1..length).forEach {
            when (dir) {
                'R' -> x++
                'U' -> y--
                'L' -> x--
                'D' -> y++
            }
            totl++
            val currSquare: Square =
                occupied[Pair(x, y)] ?: Square(x, y).also { square -> occupied[Pair(x, y)] = square }

            if (isA) {
                currSquare.occupiedA = true
                currSquare.la = min(currSquare.la, totl)
            } else {
                currSquare.occupiedB = true
                currSquare.lb = min(currSquare.lb, totl)

                if (currSquare.isCrossed()) {
                    intersections.add(currSquare)
                }
            }
        }

    }


}