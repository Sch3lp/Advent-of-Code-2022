package be.fgov.sfpd.kata.aoc22.day12

import be.fgov.sfpd.kata.aoc22.Debugging.debug
import be.fgov.sfpd.kata.aoc22.Debugging.withDebugging
import be.fgov.sfpd.kata.aoc22.Point
import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HeightMapTest {
    @Test
    fun `examle input's bfs returns 31`() {
        val input = readFile("day12/exampleInput.txt")
        withDebugging {
            val actual = parseToHeightMap(input).bfs()
            assertThat(actual).isEqualTo(31)
        }
    }
}

fun parseToHeightMap(input: String) =
    HeightMap(input.lines().flatMapIndexed { y, line ->
        line.mapIndexed { x, c -> Point(x, -y) to Elevation(c) }
    }.toMap())

class HeightMap(private val backingMap: Map<Point, Elevation>) {
    private val endPoint: Point get() = backingMap.filterValues(Elevation::isEnd).keys.first()

    /** Breadth First Search **/
    fun bfs(to: Elevation = Elevation('S')): Int {
        val visitedPoints = mutableSetOf(endPoint)
        val nextPointsToVisit: ArrayDeque<Step> = ArrayDeque<Step>().apply { add(Step(endPoint, 0)) }
        while (nextPointsToVisit.isNotEmpty()) {
            val (currentPoint, stepCounter) = nextPointsToVisit.removeFirst().debug { "Visiting ${it.at}" }
            if (backingMap.getValue(currentPoint) == to) return stepCounter

            currentPoint.orthogonalNeighbours.filter { neighbour ->
                val neighbourElevation = backingMap[neighbour]
                if (neighbourElevation == null || neighbour in visitedPoints) false
                else neighbourElevation.to(backingMap.getValue(currentPoint)) <= 1
            }.forEach {
                visitedPoints.add(it)
                nextPointsToVisit.add(Step(it, stepCounter + 1))
            }
        }
        error("No path found")
    }
}

data class Step(val at: Point, val counter: Int)

data class Elevation(private val c: Char) {
    fun isStart() = c == 'S'
    fun isEnd() = c == 'E'
    private val alphabetToInt by lazy {
        (('a'..'z').mapIndexed { index, c -> c to index }
                + ('S' to ('a' - 'a'))
                + ('E' to ('z' - 'a'))).toMap()
    }

    private fun Char.asInt(): Int =
        alphabetToInt[this] ?: error("unmappable character: $this")

    fun to(other: Elevation): Int =
        (other.c.asInt() - this.c.asInt())
}