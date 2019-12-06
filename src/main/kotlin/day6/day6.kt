package day6

import java.io.File

fun main(args: Array<String>) {
    println(orbitComputer("input6"))
}

fun orbitComputer(filename: String): Int {

    val mapInput = File(filename).readLines().map { it.split(")").let { it[0] to it[1] } }

    val itemToOrbits = mapInput.groupBy { it.first }

    var totalOrbits = 0
    var depth = 0
    var lastLayer = mutableListOf("COM")

    while (true) {
        depth++
        val thisLayer = mutableListOf<String>()
        lastLayer.forEach { center ->
            val orbits = itemToOrbits[center] ?: emptyList()
            thisLayer.addAll(orbits.map { it.second })
            totalOrbits += orbits.size * depth
        }
        lastLayer = thisLayer
        if (lastLayer.isEmpty()) {
            return totalOrbits
        }
    }
}
