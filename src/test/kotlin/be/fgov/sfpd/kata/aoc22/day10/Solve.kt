package be.fgov.sfpd.kata.aoc22.day10

import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Solve {

    @Test
    fun `example input part 1`() {
        val input = readFile("day10/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(13140L)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day10/input.txt")
        assertThat(solve1(input)).isEqualTo(11820L)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day10/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(1)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day10/input.txt")
        assertThat(solve2(input)).isEqualTo(2533)
    }

    private fun solve1(input: String): Long = signalStrength(cycles(input))

    private fun solve2(input: String): Int = TODO()
}