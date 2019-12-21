import com.sun.deploy.util.OrderedHashSet
import javafx.geometry.Point2D
import java.io.File
import kotlin.math.absoluteValue

class Day03 {
    private val wirePath1: WirePath
    private val wirePath2: WirePath

    constructor(wirePath1: String, wirePath2: String) {
        this.wirePath1 = WirePath(wirePath1)
        this.wirePath2 = WirePath(wirePath2)
    }

    constructor(file: File) {
        this.wirePath1 = WirePath(file.readText().lines()[0])
        this.wirePath2 = WirePath(file.readText().lines()[1])
    }

    fun calculateManhattanDistance(): Int? {
        val crossedWireCoordinates: MutableMap<String, Pair<Int, Point2D>> = getCrossedWireCoordinates()


//        todo ask about how better to handle null values here
//        if (crossedWireCoordinates.isEmpty()) {
//            throw Exception("Unable to find any points where the wires cross")
//        }

        val manhattanCoordinate = crossedWireCoordinates.minBy { (_, coordinate) ->  coordinate.second.x.absoluteValue + coordinate.second.y.absoluteValue }

        if (manhattanCoordinate != null) {
            val manhattanDistance = manhattanCoordinate.value.second.x.absoluteValue + manhattanCoordinate.value.second.y.absoluteValue

            return manhattanDistance.toInt()
        }
        return 0

    }

    fun calculateShortestPathToIntersection(): Int {
        val crossedWireCoordinates = getCrossedWireCoordinates()

        val shortestPathCoordinate = crossedWireCoordinates.minBy { (_, coordinate) -> coordinate.first }

        if (shortestPathCoordinate != null) {
            return shortestPathCoordinate.value.first
        }

        return 0

    }

    private fun getCrossedWireCoordinates(): MutableMap<String, Pair<Int, Point2D>> {
        val crossedWireCoordinates: MutableMap<String, Pair<Int, Point2D>> = mutableMapOf()
        wirePath1.coordinates.forEach {(coordinateKey, coordinate) ->
            if (wirePath2.coordinates.containsKey(coordinateKey)) {
                val otherCoordinateDistance: Int = wirePath2.coordinates[coordinateKey]?.first ?: 0
                crossedWireCoordinates[coordinateKey] = Pair(coordinate.first + otherCoordinateDistance, coordinate.second)
            }
        }
        return crossedWireCoordinates
    }

}


class WirePath(input: String) {
    var coordinates: MutableMap<String, Pair<Int, Point2D>> = mutableMapOf()
    private val directions: List<Pair<Char, Double>> = input.split(",").map { it[0] to it.substring(1).toDouble() }

    private var currentCoordinate: Point2D = Point2D(0.0, 0.0)


    init {
        var distanceFromStart = 0
        this.directions.forEach {(direction, length) ->
            for (i in 1..length.toInt()) {
                distanceFromStart++
                this.currentCoordinate = getNextCoordinate(direction)
                this.coordinates["${this.currentCoordinate.x}${this.currentCoordinate.y}"] = Pair(distanceFromStart, this.currentCoordinate)
            }
        }
    }

    private fun getNextCoordinate(direction: Char, length: Double = 1.0): Point2D {
        return when(direction) {
            'U' -> this.currentCoordinate.add(0.0, length)
            'D' -> this.currentCoordinate.add(0.0, length.unaryMinus())
            'R' -> this.currentCoordinate.add(length, 0.0)
            'L' -> this.currentCoordinate.add(length.unaryMinus(), 0.0)
            else -> throw Exception("Unable to process direction: $direction")

        }
    }

}