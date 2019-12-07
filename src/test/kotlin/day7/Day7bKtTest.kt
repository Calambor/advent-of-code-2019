package day7

import org.junit.Test

import org.junit.Assert.*
import thrusterFeedbackSeries

class Day7bKtTest {

    @Test
    fun thrusterFeedbackSeries() {

        val out = thrusterFeedbackSeries("input7_t3", listOf(9,8,7,6,5))

        assertEquals(139629729, out)

    }
}