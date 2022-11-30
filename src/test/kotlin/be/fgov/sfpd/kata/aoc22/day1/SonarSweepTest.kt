package be.fgov.sfpd.kata.aoc22.day1

import be.fgov.sfpd.kata.aoc22.day1.Measurement.INCREASED
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SonarSweepTest {

    @Test
    internal fun `given a list of two numbers check second one is larger`() {
        val report = listOf(1, 2)
        val result = measure(report)
        Assertions.assertThat(result).containsExactly(INCREASED)
    }

    private fun measure(sweep: List<Int>): List<Measurement> {
       return listOf()
    }
}

enum class Measurement {
    INCREASED,
    DECREASED
}
