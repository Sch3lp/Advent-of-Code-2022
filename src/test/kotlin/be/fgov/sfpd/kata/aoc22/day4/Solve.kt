package be.fgov.sfpd.kata.aoc22.day4

import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Solve {

    @Test
    fun `example input part 1`() {
        val input = readFile("day4/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(2)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day4/input.txt")
        assertThat(solve1(input)).isEqualTo(496)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day4/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(4)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day4/input.txt")
        assertThat(solve2(input)).isEqualTo(4)
    }

    private fun solve1(input: String) : Int = CampCleaning.fullyContainedPairs(input)
    private fun solve2(input: String) : Int = CampCleaning.partiallyContainedPairs(input)
}

