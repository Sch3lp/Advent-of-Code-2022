package be.fgov.sfpd.kata.aoc22.day4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CampCleaningTest {
    private val input = """2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8""".trimIndent()

    @Test
    fun `example input - part 1`() {
        assertThat(CampCleaning.fullyContainedPairs(input)).isEqualTo(2)
    }

    @Test
    fun `example input - part 2`() {
        assertThat(CampCleaning.partiallyContainedPairs(input)).isEqualTo(4)
    }

}

