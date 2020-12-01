package day12

import org.junit.Assert.*
import org.junit.Test

class Day12Test {

    @Test
    fun testMoonRotation() {

        val moons = allMoons("input12_t1")
        repeat(10) {
            moons.applyGravity()
            moons.forEach { it.velocityStep() }

        }
        assertEquals(179, moons.sumBy { it.energy })
    }

    @Test
    fun testListEquality() {

        val others = allMoons("input12_t1")
        val moons = allMoons("input12_t1")
        assertEquals(moons, others)
        println(moons)
    }


}