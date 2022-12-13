package be.fgov.sfpd.kata.aoc22.day10

import be.fgov.sfpd.kata.aoc22.Debugging
import be.fgov.sfpd.kata.aoc22.Debugging.debug
import be.fgov.sfpd.kata.aoc22.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CPUSignalTest {

    @Test
    fun example() {
        val input = """
        noop
        addx 3
        addx -5
""".trimIndent()

        val actual: X = cycles(input).values.last()

        assertThat(actual).isEqualTo(-1)
    }

    @Test
    fun `bigger example`() {
        val input = readFile("day10/exampleInput.txt")
        Debugging.enable()
        val actual: Long = signalStrength(cycles(input))
        Debugging.disable()
        assertThat(actual).isEqualTo(13140)
    }
}

fun signalStrength(cycles: Map<Cycle, X>): Long {
    return cycles
        .debug { "$it" }
        .mapValues { (k, v) -> v * k }
        .filterKeys { cycle -> cycle in listOf(20L, 60L, 100L, 140L, 180L, 220L) }
        .debug { "$it" }
        .values.sum()
}
