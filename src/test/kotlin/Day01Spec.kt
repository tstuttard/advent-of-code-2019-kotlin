import org.spekframework.spek2.Spek
import java.math.BigDecimal
import kotlin.test.assertEquals

class Day01Spec : Spek({

    val day01 by memoized { Day01("src/test/resources/day01.txt") }

    mapOf(
        12 to 2,
        14 to 2,
        1969 to 654,
        100756 to 33583
    ).forEach { (mass, expectedFuel) ->
        test("$mass mass uses $expectedFuel fuel") {
            assertEquals(day01.calculateFuel(BigDecimal(mass)), BigDecimal(expectedFuel))
        }
    }

    test("mass to fuel total") {
        assertEquals(day01.totalFuel(), BigDecimal(3291760))
    }
})
