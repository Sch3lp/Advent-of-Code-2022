package be.fgov.sfpd.kata.aoc22.day9

import be.fgov.sfpd.kata.aoc22.Point
import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Solve {

    @Test
    fun `example input part 1`() {
        val input = readFile("day9/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(13)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day9/input.txt")
        assertThat(solve1(input)).isEqualTo(6023)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day9/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(1)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day9/input.txt")
        assertThat(solve2(input))
            .isGreaterThan(2333)
    }

    private fun solve1(input: String): Int {
        val tailMovements = mutableSetOf(Point(0,0))
        Rope(broadcast = tailMovements::add)
            .pull(PullCommand.fromLines(input))
        return tailMovements.size
    }

    private fun solve2(input: String): Int {
        val tailMovements = mutableSetOf(Point(0,0))
        val tail = Rope(Point(0, 0), Point(0, 0), tailMovements::add, "T")
        val knot8 = Rope(Point(0, 0), Point(0, 0), tail::follow, "8")
        val knot7 = Rope(Point(0, 0), Point(0, 0), knot8::follow, "7")
        val knot6 = Rope(Point(0, 0), Point(0, 0), knot7::follow, "6")
        val knot5 = Rope(Point(0, 0), Point(0, 0), knot6::follow, "5")
        val knot4 = Rope(Point(0, 0), Point(0, 0), knot5::follow, "4")
        val knot3 = Rope(Point(0, 0), Point(0, 0), knot4::follow, "3")
        val knot2 = Rope(Point(0, 0), Point(0, 0), knot3::follow, "2")
        val knot1 = Rope(Point(0, 0), Point(0, 0), knot2::follow, "1")
        val head = Rope(Point(0, 0), Point(0, 0), knot1::follow, "H")
        head.pull(PullCommand.fromLines(input))
        return tailMovements.size
    }
}