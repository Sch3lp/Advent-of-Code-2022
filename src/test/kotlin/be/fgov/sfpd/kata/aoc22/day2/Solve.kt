package be.fgov.sfpd.kata.aoc22.day2

import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Solve {

    @Test
    fun `example input part 1`() {
        val input = readFile("day2/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(15)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day2/input.txt")
        assertThat(solve1(input)).isEqualTo(42)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day2/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(42)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day2/input.txt")
        assertThat(solve2(input)).isEqualTo(42)
    }

    private fun solve1(input: String) : Int = totalScore(input)
    private fun solve2(input: String) : Int = totalScore(input)
}

