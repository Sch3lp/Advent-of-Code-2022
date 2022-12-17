package be.fgov.sfpd.kata.aoc22.day12

import be.fgov.sfpd.kata.aoc22.Debugging.withDebugging
import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Solve {

    @Test
    fun `example input part 1`() {
        val input = readFile("day12/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(31)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day12/input.txt")
        assertThat(solve1(input)).isEqualTo(6023)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day12/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(1)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day12/input.txt")
        assertThat(solve2(input))
            .isGreaterThan(2333)
            .isEqualTo(2533)
    }

    private fun solve1(input: String): Int = withDebugging { parseToGrid(input).bfs() }

    private fun solve2(input: String): Int = TODO()
}