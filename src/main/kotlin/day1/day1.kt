package day1

import java.io.File

fun main(args: Array<String>) {

    val input = File("input1_1").readLines()

    val fuelSum = input.sumBy { line ->
        fuelRequirementOfModule(line.toInt())
    }

    println(fuelSum)
}

fun fuelRequirementOfModule(mass: Int): Int {
    val dividedInput = mass / 3
    val requiredFuel = dividedInput - 2

    return if (requiredFuel <= 0) {
        0
    } else {
        val fuelForFuel = fuelRequirementOfModule(requiredFuel)
        requiredFuel + fuelForFuel
    }
}