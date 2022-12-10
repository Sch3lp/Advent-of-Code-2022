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
        assertThat(solve2(input)).isEqualTo(8)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day9/input.txt")
        assertThat(solve2(input)).isEqualTo(157320)
    }

    private fun solve1(input: String): Int {
        val tailMovements = mutableSetOf(Point(0,0))
        Rope(broadcast = tailMovements::add)
            .pull(PullCommand.fromLines(input))
        return tailMovements.size
    }
    private fun solve2(input: String): Int = TODO()
}