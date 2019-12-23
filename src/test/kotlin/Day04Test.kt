import org.spekframework.spek2.Spek
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day04Test : Spek({

    val day04 by memoized { Day04() }

    test("not enough digits") {
        assertFalse { day04.isValidPassword("11111") }
    }

    test("decreasing pair of digits") {
        assertFalse { day04.isValidPassword("223450") }
    }

    test("no double digits") {
        assertFalse { day04.isValidPassword("123789") }
    }

    test("valid password digits never decrease") {
        assertTrue { day04.isValidPassword("112233") }
    }

    test("valid password 223333") {
        assertTrue { day04.isValidPassword("223333") }
    }

    test("invalid password 111111") {
        assertFalse { day04.isValidPassword("111111") }
    }

    test("valid password 111122") {
        assertTrue { day04.isValidPassword("111122") }
    }

    test("invalid repeated number part of larger group") {
        assertFalse { day04.isValidPassword("123444") }
    }


    test("password range") {
        val validPasswords = day04.findValidPasswords("158126-624574")
        assertEquals(1131, validPasswords.count())
    }
})
