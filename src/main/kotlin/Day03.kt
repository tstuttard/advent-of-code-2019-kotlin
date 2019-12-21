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
        val crossedWireCoordinates: MutableSet<Point2D> = mutableSetOf()
        wirePath1.coordinates.forEach {
            if (wirePath2.coordinates.contains(it)) {
                crossedWireCoordinates.add(it)
            }
        }

//        if (crossedWireCoordinates.isEmpty()) {
//            throw Exception("Unable to find any points where the wires cross")
//        }

        val manhattanCoordinate = crossedWireCoordinates.minBy { it.x.absoluteValue + it.y.absoluteValue }

        if (manhattanCoordinate != null) {
            val manhattanDistance = manhattanCoordinate.x.absoluteValue + manhattanCoordinate.y.absoluteValue

            return manhattanDistance.toInt()
        }
        return 0

    }

}


class WirePath(input: String) {
    var coordinates: MutableSet<Point2D> = mutableSetOf()
    private val directions: List<Pair<Char, Double>> = input.split(",").map { it[0] to it.substring(1).toDouble() }

    private var currentCoordinate: Point2D = Point2D(0.0, 0.0)


    init {
        this.directions.forEach {(direction, length) ->
            for (i in 1..length.toInt()) {
                this.currentCoordinate = getNextCoordinate(direction)
                this.coordinates.add(this.currentCoordinate)
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