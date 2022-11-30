package be.fgov.sfpd.kata.aoc22.day1

import be.fgov.sfpd.kata.aoc22.day1.DepthChange.DECREASED
import be.fgov.sfpd.kata.aoc22.day1.DepthChange.INCREASED
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SonarSweepTest {
    @Test
    fun `given a sweep report, we measure the depth changes`() {
        val report = listOf(1, 2, 3)
        val result = measure(report)
        assertThat(result).containsExactly(INCREASED, INCREASED)
    }

    @Test
    fun `given a sweep report, we can count the amount of increases`() {
        val report = listOf(1, 2, 3)

        val result = measure(report).countNumberOfDepthIncreases()

        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `given input string (actual report) can create list of depths`() {
        val report = """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263
        """.trimIndent()
        
        val actual : List<Int> = report.toDepths()
        
        assertThat(actual).containsExactly(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)
    }
}

private fun String.toDepths(): List<Int> = split("\n").map { depth -> depth.toInt() }

private fun List<DepthChange>.countNumberOfDepthIncreases(): Int = count { it == INCREASED }

private fun measure(report: List<Int>): List<DepthChange> = 
    report.zipWithNext { previous, current -> if (current > previous) INCREASED else DECREASED }

enum class DepthChange {
    INCREASED,
    DECREASED
}