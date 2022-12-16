package be.fgov.sfpd.kata.aoc22.day12

import be.fgov.sfpd.kata.aoc22.Edge
import be.fgov.sfpd.kata.aoc22.Graph
import be.fgov.sfpd.kata.aoc22.Node
import be.fgov.sfpd.kata.aoc22.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue

class GraphTest {
    @Test
    fun `input can be parsed as a Graph that only retains one-level difference edges`() {
        val input = """
            Sad
            cbc
        """.trimIndent()

        val actual = parseToGraph(input)

        val expected = listOf(
            Edge(HeightStep(Point(0, 0), 'S'), HeightStep(Point(1, 0), 'a')),
            Edge(HeightStep(Point(1, 0), 'a'), HeightStep(Point(0, 0), 'S')),
            Edge(HeightStep(Point(1, 0), 'a'), HeightStep(Point(1, -1), 'b')),
            Edge(HeightStep(Point(1, -1), 'b'), HeightStep(Point(1, 0), 'a')),
            Edge(HeightStep(Point(1, -1), 'b'), HeightStep(Point(0, -1), 'c')),
            Edge(HeightStep(Point(1, -1), 'b'), HeightStep(Point(2, -1), 'c')),
            Edge(HeightStep(Point(0, -1), 'c'), HeightStep(Point(1, -1), 'b')),
            Edge(HeightStep(Point(2, -1), 'c'), HeightStep(Point(1, -1), 'b')),
            Edge(HeightStep(Point(2, -1), 'c'), HeightStep(Point(2, 0), 'd')),
            Edge(HeightStep(Point(2, 0), 'd'), HeightStep(Point(2, -1), 'c')),
        )

        assertThat(actual.edgeList).containsExactlyInAnyOrderElementsOf(expected)
    }
}

fun parseToGraph(input: String): Graph<HeightStep> {
    val allSteps = input.lines().flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            HeightStep(Point(x, -y), c)
        }
    }.associateBy { it.point }

    val stepsWithNeighbours: Map<Point, List<HeightStep>> =
        allSteps.mapValues { (_, v) -> v.point.orthogonalNeighbours.mapNotNull { allSteps[it] } }
    val allEdges: List<Edge<HeightStep>> =
        stepsWithNeighbours.flatMap { (k, v) -> v.map { neighbour -> Edge(allSteps.getValue(k), neighbour) } }

    return Graph(allEdges.filter { (first, second) -> diff(first.height, second.height) in 0..1 })
}

typealias Height = Char

fun diff(a: Height, b: Height): Int =
    (a.asInt() - b.asInt()).absoluteValue

fun Height.asInt(): Int =
    (('a'..'z').zip(1..26)
            + ('S' to 1)
            + ('E' to 26))
        .toMap()[this] ?: error("unmappable character: $this")

data class HeightStep(val point: Point, val height: Height) : Node {
    override fun isStart() = height == 'S'
    override fun isEnd() = height == 'E'
}