package be.fgov.sfpd.kata.aoc22.day5

import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Solve {

    @Test
    fun `example input part 1`() {
        val input = readFile("day5/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo("CMZ")
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day5/input.txt")
        assertThat(solve1(input))
            .isNotEqualTo("RHBNBHWD")
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day5/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(4)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day5/input.txt")
        assertThat(solve2(input)).isEqualTo(4)
    }

    private fun solve1(input: String): String =
        execute(parseToShip(input), parseToRearrangementProcedure(input).take(3)).topCrates()

    private fun solve2(input: String): String = TODO()
}



