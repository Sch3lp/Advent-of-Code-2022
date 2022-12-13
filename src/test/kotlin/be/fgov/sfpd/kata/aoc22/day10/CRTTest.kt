package be.fgov.sfpd.kata.aoc22.day10

import be.fgov.sfpd.kata.aoc22.Debugging
import be.fgov.sfpd.kata.aoc22.Debugging.disable
import be.fgov.sfpd.kata.aoc22.Debugging.enable
import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CPUSignalTest {

    @Test
    fun `bigger example`() {
        val input = readFile("day10/exampleInput.txt")
        Debugging.enable()
        val actual = CRT.signalStrength(input)
        Debugging.disable()
        assertThat(actual).isEqualTo(13140)
    }

    @Test
    fun `drawing pixels for bigger example`() {
        val input = readFile("day10/exampleInput.txt")

        val expected = """
        ⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️⚪️⚪️⚫️⚫️
        ⚪️⚪️⚪️⚫️⚫️⚫️⚪️⚪️⚪️⚫️⚫️⚫️⚪️⚪️⚪️⚫️⚫️⚫️⚪️⚪️⚪️⚫️⚫️⚫️⚪️⚪️⚪️⚫️⚫️⚫️⚪️⚪️⚪️⚫️⚫️⚫️⚪️⚪️⚪️⚫️
        ⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️
        ⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️
        ⚪️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️
        ⚪️⚪️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️⚫️⚫️⚪️⚪️⚪️⚪️⚪️⚪️⚪️⚫️⚫️⚫️⚫️⚫️
        """.trimIndent()

        enable()
        assertThat(CRT.drawImage(input)).isEqualTo(expected)
        disable()
    }
}