package be.fgov.sfpd.kata.aoc22.day1

import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Solve {

    @Test
    fun `example input part 1`() {
        val input = readFile("day1/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(24000)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day1/input.txt")
        assertThat(solve1(input)).isEqualTo(71124)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day1/exampleInput.txt")
        assertThat(solve2(input)).isEqualTo(45000)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day1/input.txt")
        assertThat(solve2(input)).isEqualTo(204639)
    }

    private fun solve1(input: String) : Int = maxCaloriesCarried(input)
    private fun solve2(input: String) : Int = top3MaxCaloriesCarried(input)
}

