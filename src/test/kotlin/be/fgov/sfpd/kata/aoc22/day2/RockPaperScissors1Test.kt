package be.fgov.sfpd.kata.aoc22.day2

import be.fgov.sfpd.kata.aoc22.day2.RockPaperScissors1.Hand
import be.fgov.sfpd.kata.aoc22.day2.RockPaperScissors1.Hand.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RockPaperScissors1Test {

    @Test
    fun `Letters are identifiable as rock, paper or scissors`() {
        val characters = listOf("A", "B", "C", "X", "Y", "Z")
        val actual: List<Hand> = characters.map { Hand.from(it) }

        assertThat(actual).containsExactly(
            Rock,
            Paper,
            Scissors,
            Rock,
            Paper,
            Scissors,
        )
    }

    @Test
    fun `Hands can be summed`() {
        assertThat(Rock + Rock).isEqualTo(1 + 3)
        assertThat(Rock + Paper).isEqualTo(2 + 6)
        assertThat(Rock + Scissors).isEqualTo(3 + 0)
        assertThat(Paper + Rock).isEqualTo(1 + 0)
        assertThat(Paper + Paper).isEqualTo(2 + 3)
        assertThat(Paper + Scissors).isEqualTo(3 + 6)
        assertThat(Scissors + Rock).isEqualTo(1 + 6)
        assertThat(Scissors + Paper).isEqualTo(2 + 0)
        assertThat(Scissors + Scissors).isEqualTo(3 + 3)
    }
}