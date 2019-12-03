import io.kotlintest.data.suspend.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import io.kotlintest.tables.row
import java.math.BigDecimal

class Day01Spec : FreeSpec() {
    init {
        val day01 = Day01("src/test/resources/day01.txt")
        "mass to fuel calculation" {
            forall(
                row(12, 2),
                row(14,2),
                row(1969, 654),
                row(100756, 33583)
            ) { mass: Int, expectedFuel: Int ->
                day01.calculateFuel(BigDecimal(mass)) shouldBe BigDecimal(expectedFuel)
            }
        }

        "mass to fuel total" {
            println(day01.totalFuel())
        }
    }
}
