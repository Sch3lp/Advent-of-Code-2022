package be.fgov.sfpd.kata.aoc22.day8

import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Solve {

    @Test
    fun `example input part 1`() {
        val input = readFile("day8/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(21)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day8/input.txt")
        assertThat(solve1(input)).isEqualTo(1832)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day8/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(19)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day8/input.txt")
        assertThat(solve2(input)).isEqualTo(42)
    }

    private fun solve1(input: String): Int = Forest(parse(input)).visibleTrees
    private fun solve2(input: String): Int = TODO()
}