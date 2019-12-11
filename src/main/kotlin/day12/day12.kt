package day12

import BigComputer

fun main(args: Array<String>) {
    val bot = PaintBot("input11")
    bot.run()
}

class PaintBot(program: String) {
    val mapSize = 100
    val hull: List<MutableList<Pair<Boolean, Boolean>>> = List(mapSize) { MutableList(mapSize) { Pair(false, false) } }
    var x = mapSize/2
    var y = mapSize/2

    var dir = Direction.UP
    val brain = BigComputer(program)

    fun run() {
        hull[x][y] = Pair(true, false)
        while (brain.running) {
            println("Bot at $x $y")
            brain.input.offer(hull[x][y].first.color().toLong())
            brain.runABit()
            val colorOp = brain.output.poll()
            brain.runABit()
            val moveOp = brain.output.poll()

            when (colorOp) {
                0L -> hull[x][y] = Pair(false, true)
                1L -> hull[x][y] = Pair(true, true)
            }
            when (moveOp) {
                1L -> dir = dir.rotRight()
                0L -> dir = dir.rotLeft()
            }
            when (dir) {
                Direction.UP -> y--
                Direction.RIGHT -> x++
                Direction.DOWN -> y++
                Direction.LEFT -> x--
            }

        }
        val painted = hull.sumBy { it.count { it.second } }
        println(painted)

        hull.forEach {
            it.map { if (it.first) '#' else '.' }.reversed().forEach { print(it) }
            println()
        }
    }

    private fun Boolean.color(): Int = if (this) 1 else 0
}

enum class Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    fun rotLeft(): Direction {
        val index = (this.ordinal + 4 - 1) % 4
        return Direction.values()[index]
    }

    fun rotRight(): Direction {
        val index = (this.ordinal + 1) % 4
        return Direction.values()[index]
    }
}