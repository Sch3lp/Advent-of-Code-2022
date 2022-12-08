package be.fgov.sfpd.kata.aoc22.day8

import be.fgov.sfpd.kata.aoc22.Point
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TreeHutPlannerTest {

    @Test
    fun `parsing to trees`() {
        val input = """
30373
25512
65332
33549
35390
        """.trimIndent()

        assertThat(parse(input)).containsExactly(
            Tree(Point(0,0), 3), Tree(Point(1,0), 0), Tree(Point(2,0), 3), Tree(Point(3,0), 7), Tree(Point(4,0), 3),
            Tree(Point(0,1), 2), Tree(Point(1,1), 5), Tree(Point(2,1), 5), Tree(Point(3,1), 1), Tree(Point(4,1), 2),
            Tree(Point(0,2), 6), Tree(Point(1,2), 5), Tree(Point(2,2), 3), Tree(Point(3,2), 3), Tree(Point(4,2), 2),
            Tree(Point(0,3), 3), Tree(Point(1,3), 3), Tree(Point(2,3), 5), Tree(Point(3,3), 4), Tree(Point(4,3), 9),
            Tree(Point(0,4), 3), Tree(Point(1,4), 5), Tree(Point(2,4), 3), Tree(Point(3,4), 9), Tree(Point(4,4), 0),
        )
    }
}

fun parse(input: String) : Set<Tree> =
    input.lines().flatMapIndexed { y, line ->
        line.mapIndexed { x, height ->
            Tree(Point(x, y), "$height".toInt())
        }
    }.toSet()

data class Tree(val at: Point, val height: Height) {
    companion object {
        operator fun invoke(at: Point, height: Int) = Tree(at, Height(height))
    }
}

@JvmInline value class Height(val value: Int) {
    init {
        require(value in 0..9) { "height is expected to be between 0 and 9, but it was $value"}
    }
}