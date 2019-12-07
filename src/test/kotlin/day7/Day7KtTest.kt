package day7

import org.junit.Test

import org.junit.Assert.*
import permutations
import thrusterSeries

class Day7KtTest {

    @Test
    fun thrusterProgram() {
        assertEquals(43210, thrusterSeries("input7_t1", listOf(4,3,2,1,0)))
    }
    @Test
    fun thrusterProgram2() {
        assertEquals(54321, thrusterSeries("input7_t2", listOf(0, 1, 2, 3, 4)))
    }

    @Test
    fun testPermutations() {

        val expected = listOf(
            listOf(1, 2, 3),
            listOf(1, 3, 2),
            listOf(2, 1, 3),
            listOf(2, 3, 1),
            listOf(3, 1, 2),
            listOf(3, 2, 1)
        )
        val actual = listOf<Int>().permutations(listOf(1, 2, 3))
        assertEquals(expected, actual)
    }
}