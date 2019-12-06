package day6

import java.io.File

fun main(args: Array<String>) {
    println(orbitHopComputer("input6"))
}

fun orbitHopComputer(filename: String): Int {

    val mapInput = File(filename).readLines().map { it.split(")").let { it[0] to it[1] } }

    val orbitsToItems = mapInput.map { it.second to it.first }.toMap()

    val youPath = orbitsToItems.pathFrom("YOU")
    val sanPath = orbitsToItems.pathFrom("SAN")
    val common = youPath.intersect(sanPath)
    val remYou = youPath.minus(common).size
    val remSan = sanPath.minus(common).size
    return remYou + remSan - 2
}

 fun Map<String, String>.pathFrom(item: String): List<String> {
     var next: String? = item
     val path = mutableListOf<String>()
     while (next != null) {
         path.add(next)
         next = this[next]
     }
     return path
 }
