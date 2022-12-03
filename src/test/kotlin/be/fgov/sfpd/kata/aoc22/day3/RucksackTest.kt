package be.fgov.sfpd.kata.aoc22.day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RucksackTest {

    @Test
    fun `solve example input`() {
        val input = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw""".trimIndent()

        val rucksacks = input.lines()
        val result = rucksacks.sumOf { rucksack -> rucksack.priority }

        assertThat(result).isEqualTo(157)
    }
}