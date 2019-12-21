import org.spekframework.spek2.Spek
import java.io.File
import java.math.BigDecimal
import kotlin.test.assertEquals

class Day03Test : Spek({
    test("R8,U5,L5,D3 & U7,R6,D4,L4 = 6 manhattan distance") {
        val wirePath1 = "R8,U5,L5,D3"
        val wirePath2 = "U7,R6,D4,L4"
        assertEquals(6, Day03(wirePath1, wirePath2).calculateManhattanDistance())

    }

    test("R75,D30,R83,U83,L12,D49,R71,U7,L72 & U62,R66,U55,R34,D71,R55,D58,R83 = 159 manhattan distance and 610 steps") {
        val wirePath1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72"
        val wirePath2 = "U62,R66,U55,R34,D71,R55,D58,R83"
        val day03 = Day03(wirePath1, wirePath2)
        assertEquals(159, day03.calculateManhattanDistance())
        assertEquals(610, day03.calculateShortestPathToIntersection())
    }

    test("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51 & U98,R91,D20,R16,D67,R40,U7,R15,U6,R7 = 135 manhattan distance and 410 steps") {
        val wirePath1 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
        val wirePath2 = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
        val day03 = Day03(wirePath1, wirePath2)
        assertEquals(135, day03.calculateManhattanDistance())
        assertEquals(410, day03.calculateShortestPathToIntersection())
    }


    test("src/test/resources/day02.txt") {

        assertEquals(1674, Day03(File("src/test/resources/day03.txt")).calculateManhattanDistance())
        assertEquals(14012, Day03(File("src/test/resources/day03.txt")).calculateShortestPathToIntersection())
    }
})
