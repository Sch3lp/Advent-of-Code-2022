package be.fgov.sfpd.kata.aoc22.day2

import be.fgov.sfpd.kata.aoc22.day2.Hand.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RockPaperScissorsTest {

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

    @Test
    fun `Lines can be turned into Battles`() {
        val lines = """|A Y
                       |B X
                       |C Z""".trimMargin().lines()
        val battles: List<Battle> = lines.toBattles()
        assertThat(battles).containsExactly(
            Battle(Rock, Paper),
            Battle(Paper, Rock),
            Battle(Scissors, Scissors),
        )
    }

    @Test
    fun `Solve can solve example input`() {
        val input = """|A Y
                       |B X
                       |C Z""".trimMargin()

        val result = totalScore(input)
        assertThat(result).isEqualTo(15)
    }
}