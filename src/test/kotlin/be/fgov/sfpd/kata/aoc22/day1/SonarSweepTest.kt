package be.fgov.sfpd.kata.aoc22.day1

import be.fgov.sfpd.kata.aoc22.day1.DepthChange.DECREASED
import be.fgov.sfpd.kata.aoc22.day1.DepthChange.INCREASED
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SonarSweepTest {
    @Test
    internal fun `given a sweep report, we measure the depth changes`() {
        val report = listOf(1, 2, 3)
        val result = measure(report)
        assertThat(result).containsExactly(INCREASED, INCREASED)
    }

    @Test
    internal fun `given a sweep report, we can count the amount of increases`() {
        val report = listOf(1, 2, 3)

        val result = measure(report).countNumberOfDepthIncreases()

        assertThat(result).isEqualTo(2)
    }
}

private fun List<DepthChange>.countNumberOfDepthIncreases(): Int = count { it == INCREASED }

private fun measure(report: List<Int>): List<DepthChange> = 
    report.zipWithNext { previous, current -> if (current > previous) INCREASED else DECREASED }

enum class DepthChange {
    INCREASED,
    DECREASED
}