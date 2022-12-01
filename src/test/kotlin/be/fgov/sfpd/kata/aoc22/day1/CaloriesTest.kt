package be.fgov.sfpd.kata.aoc22.day1

import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CaloriesTest {

    @Test
    fun `we can count the sum per elf`() {
        val input: String = readFile("day1/exampleInput.txt")

        val calories = elfCalories(input)

        assertThat(calories.map { it.sum() }).containsExactly(6000, 4000, 11000, 24000, 10000)
    }

}

