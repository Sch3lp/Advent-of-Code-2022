package be.fgov.sfpd.kata.aoc22.day8

import be.fgov.sfpd.kata.aoc22.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TreeHutPlannerTest {
        private val input = """
30373
25512
65332
33549
35390
        """.trimIndent()

    @Test
    fun `parsing to trees`() {
        assertThat(parse(input)).containsExactly(
            Tree(Point(0,0), 3), Tree(Point(1,0), 0), Tree(Point(2,0), 3), Tree(Point(3,0), 7), Tree(Point(4,0), 3),
            Tree(Point(0,1), 2), Tree(Point(1,1), 5), Tree(Point(2,1), 5), Tree(Point(3,1), 1), Tree(Point(4,1), 2),
            Tree(Point(0,2), 6), Tree(Point(1,2), 5), Tree(Point(2,2), 3), Tree(Point(3,2), 3), Tree(Point(4,2), 2),
            Tree(Point(0,3), 3), Tree(Point(1,3), 3), Tree(Point(2,3), 5), Tree(Point(3,3), 4), Tree(Point(4,3), 9),
            Tree(Point(0,4), 3), Tree(Point(1,4), 5), Tree(Point(2,4), 3), Tree(Point(3,4), 9), Tree(Point(4,4), 0),
        )
    }

    @Test
    fun `forest can identify visible trees`() {
        assertThat(Forest(parse(input)).isVisible(Point(0,0))).isTrue()
        assertThat(Forest(parse(input)).isVisible(Point(3,1))).isFalse()
    }
}

class Forest(private val trees: Set<Tree>) {
    private val westEdge = 0
    private val northEdge = 0
    private val eastEdge = trees.maxOf { it.at.x }
    private val southEdge = trees.maxOf { it.at.y }

    fun isVisible(at: Point): Boolean {
        return at.isAtEdge()
                || at.treesNorthOf().areLowerThan(trees.at(at).height)
                || at.treesSouthOf().areLowerThan(trees.at(at).height)
                || at.treesEastOf().areLowerThan(trees.at(at).height)
                || at.treesWestOf().areLowerThan(trees.at(at).height)
    }

    private fun List<Tree>.areLowerThan(height: Height) : Boolean
        = all { it.height < height }

    private fun Point.treesNorthOf() = trees.filter { it.at in this..Point(this.x, northEdge) }
    private fun Point.treesSouthOf() = trees.filter { it.at in this..Point(this.x, southEdge) }
    private fun Point.treesEastOf() = trees.filter { it.at in this..Point(eastEdge, this.y) }
    private fun Point.treesWestOf() = trees.filter { it.at in this..Point(westEdge, this.y) }

    private fun Set<Tree>.at(point: Point) = this.first { it.at == point }

    private fun Point.isAtEdge() =
        this in trees.map { it.at }.filter {
            it.x == westEdge || it.y == northEdge || it.x == eastEdge || it.y == southEdge }
}

fun parse(input: String) : Set<Tree> =
    input.lines().flatMapIndexed { y, line ->
        line.mapIndexed { x, height ->
            Tree(Point(x, y), "$height".toInt())
        }
    }.toSet()

data class Tree(val at: Point, val height: Height)
typealias Height = Int