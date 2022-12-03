package be.fgov.sfpd.kata.aoc22.day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RucksackTest {
    private val input = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw""".trimIndent()

    @Test
    fun `solve example input`() {
        val result = Rucksacks(input).totalPriority

        assertThat(result).isEqualTo(157)
    }

    @Test
    fun `a group's badge can be found`() {
        val groupBadges : List<ItemType> = Rucksacks(input).findGroupBadges()
        assertThat(groupBadges).containsExactly(ItemType('r'),ItemType('Z'))
    }
}