package day10

import org.junit.Test

import org.junit.Assert.*

class Day10KtTest {

    @Test
    fun allAnglesAreUnique() {

        val angles = findAllAngles()

        angles.filter { it.actualAngle == Angle(1, 0).actualAngle }.forEach {
            println("$it")
        }

        angles.map { other ->
            if (angles.count { it.actualAngle == other.actualAngle } > 1) {
                println("$other")
            }
            assertEquals(1, angles.count { it.actualAngle == other.actualAngle })
        }
    }

    @Test
    fun tinyMapHasRightAmount() {
        assertEquals(8, astroidFinder("input10_t1"))

    }
    @Test
    fun largeMapHasRightAmount() {
        assertEquals(210, astroidFinder("input10_t2"))

    }
}