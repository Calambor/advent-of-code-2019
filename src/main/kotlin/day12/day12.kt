package day12

import java.io.File
import kotlin.math.abs
import kotlin.math.sign

// TODO solve part 2
fun main(args: Array<String>) {
    val originals = allMoons("input12")
    val moons = allMoons("input12")
    repeat(0) {
        moons.applyGravity()
        moons.forEach { it.velocityStep() }
    }

    var running = true
    var steps = 0L
    while (running) {
        steps++
        moons.applyGravity()
        moons.forEach { it.velocityStep() }
        if (steps % 10000 == 0L) {
            println(steps)
        }
        if (moons.equals(originals)) {
            running = false
        }
    }


    println(steps)

    println(231614 * 286332L * 22958L)

    var count = 0
    while (running) {
        count++
        if (231614L * count % 22958L == 0L) {
            running = false
        }
    }

    val a = 231614 * 286332L
    count = 0
    running = true
    while (running) {
        count++
        if (a * count % 22958L == 0L) {
            running = false
        }
    }
    println(count * a)

}

fun findLowestDenom() {
    val pa = 231614L
    val pb = 286332L
    val pc = 22958L

    var a = 0
    var b = 0
    var c = 0

    while (a * pa != b * pb || b * pb != c * pc) {
        if (a * pa < b * pb) {
            a++
        } else if (b * pb < c * pc) {
            b++
        } else if (a * pa < c * pc) {
            a++
        }

    }


}

// No result after 528339000000
// not 1522540119510384 (high)
// not 263534882 (low)
// not 761270059755192 (high)

// 22958 x
// 286332 y
// 231614 z

fun allMoons(input: String): List<Moon> {
    return File(input).readLines().map { Moon.fromText(it) }
}

data class Moon(
    var x: Int,
    var y: Int,
    var z: Int,
    var velX: Int = 0,
    var velY: Int = 0,
    var velZ: Int = 0
) {

    val potentialE get() = abs(x) + abs(y) + abs(z)
    val kineticE get() = abs(velX) + abs(velY) + abs(velZ)
    val energy get() = potentialE * kineticE

    companion object {
        fun fromText(text: String): Moon {
            val raws = text.split(',')
            val x = raws[0].substringAfter('=').toInt()
            val y = raws[1].substringAfter('=').toInt()
            val z = raws[2].substringAfter('=').substringBefore('>').toInt()
            return Moon(x, y, z)
        }
    }

    fun velocityStep() {
        x += velX
        y += velY
        z += velZ
    }

    fun applyMutualGravityOn(other: Moon) {
//        val xDiff = (x - other.x).sign
//        velX -= xDiff
//        other.velX += xDiff

//        val yDiff = (y - other.y).sign
//        velY -= yDiff
//        other.velY += yDiff
        val zDiff = (z - other.z).sign
        velZ -= zDiff
        other.velZ += zDiff


    }
}

fun List<Moon>.applyGravity() {
    (0 until size).forEach { first ->
        (first until size).forEach { second ->
            this[first].applyMutualGravityOn(this[second])
        }
    }
}
