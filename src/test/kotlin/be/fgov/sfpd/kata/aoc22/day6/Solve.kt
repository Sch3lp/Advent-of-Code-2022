package be.fgov.sfpd.kata.aoc22.day6

import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Solve {

    @Test
    fun `example input part 1`() {
        val input = readFile("day6/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(7)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day6/input.txt")
        assertThat(solve1(input)).isEqualTo(1965)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day6/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(19)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day6/input.txt")
        assertThat(solve2(input)).isEqualTo(42)
    }

    private fun solve1(input: String): Int = Handheld(CommSystem()).startingMarkerPosition(input.asSequence())

    private fun solve2(input: String): Int = TODO()
}
