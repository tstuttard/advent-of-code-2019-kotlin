import java.io.File
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.URL
import java.util.*

class Day01(pathToFile: String) {


    private val masses: List<String> = File(pathToFile).readLines()

    fun calculateFuel(mass: BigDecimal): BigDecimal {
        return mass.divide(BigDecimal(3), RoundingMode.FLOOR).minus(BigDecimal(2))
    }

    fun totalFuel(): BigDecimal {
        return masses.sumByBigDecimal { calculateFuel(BigDecimal(it)) }
    }

    fun Iterable<BigDecimal>.sumByBigDecimal(): BigDecimal {
        return this.fold(BigDecimal.ZERO) { sum: BigDecimal, element: BigDecimal -> sum + element }
    }

    private fun <T> Iterable<T>.sumByBigDecimal(transform: (T) -> BigDecimal): BigDecimal {
        return this.fold(BigDecimal.ZERO) { sum: BigDecimal, element: T -> sum + transform.invoke(element) }
    }

}
