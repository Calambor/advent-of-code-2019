package day10

import java.io.File
import kotlin.math.atan2

//34 width

val allAngles = day10.findAllAngles()

fun main(args: Array<String>) {
    println(astroidFinder("input10"))

}

fun astroidFinder(filename: String): Int {

    val map = File(filename).readLines().map {
        it.toCharArray().asList()
    }
    val height = map.size

    var maxSeen = 0

    (0 until height).forEach { x ->
        (0 until height).forEach { y ->
            if (map[x][y] == '#') {


                var seenAstroids = 0
                allAngles.forEach { angle ->
                    try {
                        if ((1..15).any { mult ->
                                (map[x + angle.xStep * mult][y + angle.yStep * mult] == '#')
                            }) {
                            seenAstroids++
                        }

                    } catch (e: IndexOutOfBoundsException) {

                    }
                }
                println("$seenAstroids seen from $x, $y")
                if (seenAstroids > maxSeen) {
                    maxSeen = seenAstroids
                }
            }
        }
    }
    return maxSeen


}

fun findAllAngles(): List<Angle> {
    var anglesQuadrant1 = mutableListOf<Angle>()
    (1..34).forEach { x ->
        (0..34).forEach { y ->
            val newAngle = Angle(x, y)

            if (anglesQuadrant1.none { it.actualAngle == newAngle.actualAngle }) {
                anglesQuadrant1.add(newAngle)
            }

            //anglesQuadrant1.add(newAngle)
            if ((2..18).any { divider ->
                    x % divider == 0 && y % divider == 0 && (x / divider == 1 || y / divider == 1)

                }) {

            } else {

            }
        }
    }
    anglesQuadrant1 = anglesQuadrant1.map { it.lowestMultiple }.toMutableList()

    val finalAngles =
        anglesQuadrant1 + anglesQuadrant1.map { it.rotated } + anglesQuadrant1.map { it.rotated.rotated } + anglesQuadrant1.map { it.rotated.rotated.rotated }

    return finalAngles
}

data class Angle(val xStep: Int, val yStep: Int) {


    val actualAngle = atan2(yStep.toFloat(), xStep.toFloat())

    val rotated: Angle
        get() = Angle(-yStep, xStep)

    operator fun times(mul: Int): Angle {
        return Angle(xStep * mul, yStep * mul)
    }

    val lowestMultiple: Angle
        get() {
            val div: Int? = ((18 downTo 2).firstOrNull { divider ->
                xStep % divider == 0 && yStep % divider == 0
            })
            return if (div != null) {
                Angle(xStep / div, yStep / div)
            } else {
                this
            }
        }

}

