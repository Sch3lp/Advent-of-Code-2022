package be.fgov.sfpd.kata.aoc22.day12

import be.fgov.sfpd.kata.aoc22.Debugging.debug
import be.fgov.sfpd.kata.aoc22.Debugging.withDebugging
import be.fgov.sfpd.kata.aoc22.Point
import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class HeightMapTest {
    @Test
    fun `examle input's bfs returns 31`() {
        val input = readFile("day12/exampleInput.txt")
        withDebugging {
            val actual = parseToGrid(input).bfs()
            assertThat(actual).isEqualTo(31)
        }
    }
}

fun parseToGrid(input: String) =
    HeightMap(input.lines().flatMapIndexed { y, line ->
        line.mapIndexed { x, c -> Point(x, -y) to Elevation(c) }
    }.toMap())

class HeightMap(private val backingMap: Map<Point, Elevation>) {
    private val startPoint: Point get() = backingMap.filterValues(Elevation::isStart).keys.first()
    private val endPoint: Point get() = backingMap.filterValues(Elevation::isEnd).keys.first()

    /** Breadth First Search **/
    fun bfs(from: Point = startPoint, to: Point = endPoint): Int {
        val visitedPoints = mutableSetOf<Point>()
        val nextPointsToVisit: LinkedList<Step> = LinkedList(listOf(Step(from, 0)))
        while (nextPointsToVisit.isNotEmpty()) {
            val (currentPoint, stepCounter) = nextPointsToVisit.poll().debug { "Visiting ${it.at}" }
            if (currentPoint == to) return stepCounter
            visitedPoints.add(currentPoint)

            val nextOptions = currentPoint.orthogonalNeighbours.filter { neighbour ->
                val neighbourElevation = backingMap[neighbour]
                if (neighbourElevation == null || neighbour in visitedPoints) false
                else backingMap.getValue(currentPoint).to(neighbourElevation) <= 1
            }.map { Step(it, stepCounter + 1) }
            nextPointsToVisit.addAll(nextOptions)
        }
        error("No path found")
    }

    /** Depth First Search **/
    fun dfs(from: Point = startPoint, to: Point = endPoint): Int {
        val visitedPoints = mutableSetOf(from)
        val nextPointsToVisit: LinkedList<Step> = LinkedList(listOf(Step(from, 1)))
        while (nextPointsToVisit.isNotEmpty()) {
            val (currentPoint, stepCount) = nextPointsToVisit.poll()
            if (currentPoint == to) {
                return stepCount
            }
            val nextOptions = currentPoint.orthogonalNeighbours.filter { neighbour ->
                val neighbourHeight = backingMap[neighbour]
                if (neighbourHeight == null) false
                else backingMap.getValue(currentPoint).to(neighbourHeight) <= 1
            }.filterNot { it in visitedPoints }
                .map { Step(it, stepCount + 1) }
            nextOptions.forEach(nextPointsToVisit::push)
            visitedPoints.add(currentPoint)
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