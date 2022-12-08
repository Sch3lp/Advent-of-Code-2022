package be.fgov.sfpd.kata.aoc22.day8

import be.fgov.sfpd.kata.aoc22.Debugging
import be.fgov.sfpd.kata.aoc22.Debugging.debug
import be.fgov.sfpd.kata.aoc22.Debugging.withDebugging
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
            Tree(Point(0, 0), 3),
            Tree(Point(1, 0), 0),
            Tree(Point(2, 0), 3),
            Tree(Point(3, 0), 7),
            Tree(Point(4, 0), 3),
            Tree(Point(0, 1), 2),
            Tree(Point(1, 1), 5),
            Tree(Point(2, 1), 5),
            Tree(Point(3, 1), 1),
            Tree(Point(4, 1), 2),
            Tree(Point(0, 2), 6),
            Tree(Point(1, 2), 5),
            Tree(Point(2, 2), 3),
            Tree(Point(3, 2), 3),
            Tree(Point(4, 2), 2),
            Tree(Point(0, 3), 3),
            Tree(Point(1, 3), 3),
            Tree(Point(2, 3), 5),
            Tree(Point(3, 3), 4),
            Tree(Point(4, 3), 9),
            Tree(Point(0, 4), 3),
            Tree(Point(1, 4), 5),
            Tree(Point(2, 4), 3),
            Tree(Point(3, 4), 9),
            Tree(Point(4, 4), 0),
        )
    }

    @Test
    fun `forest can identify visible trees`() {
        assertThat(Forest(parse(input)).isVisible(Point(0, 0))).isTrue
        assertThat(Forest(parse(input)).isVisible(Point(3, 1))).isFalse
    }

    @Test
    fun `forest can count amount of visible trees`() {
        assertThat(Forest(parse(input)).visibleTrees)
            .doesNotContainKeys(
                Point(3, 1),
                Point(2, 2),
                Point(1, 3), Point(3, 3),
            )
            .hasSize(21)
    }

    @Test
    fun `forest can discover a tree's scenic score`() {
        assertThat(Forest(parse(input)).scenicScoreFor(Point(2, 1))).isEqualTo(1 * 1 * 2 * 2)
        assertThat(Forest(parse(input)).scenicScoreFor(Point(2, 3))).isEqualTo(2 * 2 * 1 * 2)
    }

    @Test
    fun `forest can retrieve the tree with the highest scenic score`() {
        assertThat(Forest(parse(input)).highestScenicScore.debug { "highestScenicScore is $it" }).isEqualTo(8)
    }
}