package be.fgov.sfpd.kata.aoc22.day2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RockPaperScissors2Test {
    @Test
    fun solve() {
        val input = """|A Y
                       |B X
                       |C Z
        """.trimMargin()

        val actual = RockPaperScissors2.totalScore(input)

        assertThat(actual).isEqualTo(12)
    }

}