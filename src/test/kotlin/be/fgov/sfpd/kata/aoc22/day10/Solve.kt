package be.fgov.sfpd.kata.aoc22.day10

import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Solve {

    @Test
    fun `example input part 1`() {
        val input = readFile("day10/exampleInput.txt")
        assertThat(solve1(input)).isEqualTo(13140)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("day10/input.txt")
        assertThat(solve1(input)).isEqualTo(11820)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("day10/exampleInput.txt")
        val expected = """
        ⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️
        ⚪️⚪️⚪️⚫️⚫️⚫️⚪️⚪️⚪️⚫️⚫️⚫️⚪️⚪️⚪️⚫️⚫️⚫️⚪️⚪️⚪️⚫️⚫️⚫️⚪️⚪️⚪️⚫️⚫️⚫️⚪️⚪️⚪️⚫️⚫️⚫️⚪️⚪️⚪️⚫️
        ⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️
        ⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️
        ⚪️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️
        ⚪️⚪️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️
        """.trimIndent()

        assertThat(solve2(input)).isEqualTo(expected)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("day10/input.txt")
        val expected = """
          ⚪️⚪️⚪️⚪️⚫️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚪️⚪️⚫️⚪️⚪️⚪️⚫️⚫️⚪️⚪️⚪️⚫️⚫️⚪️⚫️⚫️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚫️⚫️⚪️⚫️
          ⚪️⚫️⚫️⚫️⚫️⚪️⚫️⚫️⚪️⚫️⚫️⚫️⚫️⚪️⚫️⚪️⚫️⚫️⚪️⚫️⚪️⚫️⚫️⚪️⚫️⚪️⚫️⚪️⚫️⚫️⚪️⚫️⚫️⚪️⚫️⚪️⚫️⚫️⚪️⚫️
          ⚪️⚪️⚪️⚫️⚫️⚪️⚫️⚫️⚪️⚫️⚫️⚫️⚫️⚪️⚫️⚪️⚪️⚪️⚫️⚫️⚪️⚫️⚫️⚪️⚫️⚪️⚪️⚫️⚫️⚫️⚪️⚫️⚫️⚪️⚫️⚪️⚪️⚪️⚪️⚫️
          ⚪️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚪️⚫️⚪️⚫️⚫️⚪️⚫️⚪️⚪️⚪️⚫️⚫️⚪️⚫️⚪️⚫️⚫️⚪️⚪️⚪️⚪️⚫️⚪️⚫️⚫️⚪️⚫️
          ⚪️⚫️⚫️⚫️⚫️⚪️⚫️⚫️⚫️⚫️⚪️⚫️⚫️⚪️⚫️⚪️⚫️⚫️⚪️⚫️⚪️⚫️⚪️⚫️⚫️⚪️⚫️⚪️⚫️⚫️⚪️⚫️⚫️⚪️⚫️⚪️⚫️⚫️⚪️⚫️
          ⚪️⚪️⚪️⚪️⚫️⚪️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚪️⚫️⚫️⚪️⚫️⚫️⚪️⚫️⚪️⚫️⚫️⚪️⚫️⚪️⚫️⚫️⚪️⚫️⚪️⚫️⚫️⚪️⚫️
        """.trimIndent()
        //EPJBRKAH
        assertThat(solve2(input)).isEqualTo(expected)
    }

    private fun solve1(input: String): Int = CRT.signalStrength(input)

    private fun solve2(input: String): String = CRT.drawImage(input)
}