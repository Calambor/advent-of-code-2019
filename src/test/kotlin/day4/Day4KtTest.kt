package day4

import org.junit.Test

import org.junit.Assert.*

class Day4KtTest {

    @Test
    fun testInputYieldsCorrectResult() {
        assertFalse(isValidPass(223450) )
        assertFalse(isValidPass(123444) )
        assertFalse(isValidPass(111111) )
        assertTrue(isValidPass(112233) )
        assertTrue(isValidPass(111122) )
    }
}